package com.jy.service.impl.system.finance.reconciliation.lottery;

import java.io.*;
import java.util.*;

import com.jy.common.CpSystem.CpConst;
import com.jy.common.utils.excel.ExcelReport;


import com.jy.entity.system.channels.vo.MerchantExtend;
import com.jy.repository.system.finance.reconciliation.lottery.PlatformFundsDao;
import com.jy.repository.system.finance.reconciliation.lottery.SalesDifferencesDetailDao;
import com.jy.service.impl.help.ExcelUtil;
import com.jy.vo.system.reconciliation.lottery.FundsActivityReportVo;
import com.jy.vo.system.reconciliation.lottery.PlatFormFundsRunningVo;
import com.jy.vo.system.reconciliation.lottery.PlatFormFundsVo;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jy.common.CpSystem.DifferenceConst;
import com.jy.mybatis.Page;
import com.jy.common.utils.CalculationUtils;
import com.jy.common.utils.DateUtils;
import com.jy.entity.system.finance.reconciliation.lottery.SalesDifferencesDetail;
import com.jy.entity.system.finance.vo.FundsDiveVo;
import com.jy.entity.system.finance.reconciliation.lottery.CashDifference;
import com.jy.entity.system.finance.reconciliation.lottery.PlatformFunds;
import com.jy.entity.system.finance.reconciliation.lottery.PlatformFundsRunning;

import com.jy.service.impl.base.BaseServiceImp;
import com.jy.service.system.finance.reconciliation.lottery.PlatformFundsService;

@Service("PlatformFundsService")
public class PlatformFundsServiceImpl extends BaseServiceImp<PlatformFunds> implements PlatformFundsService {

	@Autowired
	private PlatformFundsDao platformFundsDao;

	@Autowired
	private SalesDifferencesDetailDao salesDifferencesDetailDao;

	@Value("${download.platFormFunds.filename}")
	private String platFormFundsFileName;

	@Value("${download.platFormFundsRunning.filename}")
	private String platFormFundsRunningFileName;

	@Value("${download.platFormFundsDiff.filename}")
	private String platFormFundsDiffFileName;

	@Value("${download.platFormFundsRunningDiff.filename}")
	private String platFormFundsRunningDiffFileName;
	@Value("${download.platFormFundsCMCC.filename}")
	private String platFormFundsCMCCFileName;
	@Value("${download.platFormFundsOUJIAN.filename}")
	private String platFormFundsOUJIANFileName;
	@Autowired
	private ExcelUtil excelutil;

	/**
	 * 根据对账统计的日期段查询平台用户资金汇总对账数据
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @param type
	 * @param pageVo
     * @return
     */
	@Override
	public Page<PlatFormFundsVo> findFundsListByPage(String startDate, String endDate, Integer status,Integer type, Page<PlatFormFundsVo> pageVo) {
		Page<PlatformFunds> page=new Page<>();
		page.setPageNum(pageVo.getPageNum());
		page.setPageSize(pageVo.getPageSize());
		List<PlatformFunds> fundsList=platformFundsDao.findFundsListByPage(startDate, endDate, status,type,page);
		if(fundsList==null){
			return new Page<PlatFormFundsVo>();
		}else{
			pageVo.setPageSize(page.getPageSize());
			pageVo.setTotalPage(page.getTotalPage());
			pageVo.setTotalRecord(page.getTotalRecord());
		List<PlatFormFundsVo> voList=new ArrayList<>();
		for(PlatformFunds fund:fundsList){
			PlatFormFundsVo vo=new PlatFormFundsVo();
			BeanUtils.copyProperties(fund,vo);
			voList.add(vo);
		}
		pageVo.setResults(voList);
		return pageVo;
		}
	}

	/**
	 * 根据对账统计的日期段查询平台用户交易流水汇总对账数据
	 *
	 * @param pageVo
	 * @return
	 */
	@Override
	public Page<PlatFormFundsRunningVo> findFundsRunningListByPage(String startDate, String endDate, Integer status, Page<PlatFormFundsRunningVo> pageVo) {
		Page<PlatformFundsRunning> page=new Page<>();
		page.setPageNum(pageVo.getPageNum());
		page.setPageSize(pageVo.getPageSize());
		List<PlatformFundsRunning> list=platformFundsDao.findFundsRunningListByPage(startDate, endDate, status,page);
		if(list==null){
			return new Page<PlatFormFundsRunningVo>();
		}else{
		List<PlatFormFundsRunningVo> voList=new ArrayList<>();
		pageVo.setPageSize(page.getPageSize());
		pageVo.setTotalPage(page.getTotalPage());
		pageVo.setTotalRecord(page.getTotalRecord());
		for(PlatformFundsRunning fund:list){
			PlatFormFundsRunningVo vo=new PlatFormFundsRunningVo();
			BeanUtils.copyProperties(fund,vo);
			voList.add(vo);
		}
		pageVo.setResults(voList);
		return pageVo;
		}
	}

	/**
	 * 存储平台资金每日总报表
	 *
	 * @param date
	 */
	@Override
	public void execPlatFormFundsTask( String date) {
		//字段1:正常(非活动) 301:移动活动,601：得仕捕鱼，501：得仕棋牌401：欧建商户
		// 1.存储昨天T-1日的平台资金报表（正常用户，非活动）
		_saveFundsReport(date, CpConst.ISCOMMON);
		// 1.存储昨天T-1日的平台资金报表（活动）
		_execActivityReport(date);
	}

	private void _execActivityReport(String date) {
		List<MerchantExtend> merchantList=platformFundsDao.findValidMerchantList();
		if(merchantList!=null&&merchantList.size()!=0&&!merchantList.isEmpty()){
			for(MerchantExtend m:merchantList){
				_saveFundsReport(date,Integer.parseInt(m.getSchemeType()));
			}
		}
	}






	/**
	 * 存储昨天T-1日的平台资金报表
	 * @param date
	 */
	private void _saveFundsReport( String date,int commonOrMerchantNo) {
		Date dzDate = DateUtils.parseDate(date);
//		date=DateUtils.getPreDateByDay(dzDate,"1");
		String endTime =DateUtils.getDateEndString(date);
		PlatformFunds funds = _installFundsReport(dzDate,date,endTime,commonOrMerchantNo);
		_checkAndSaveFunds(date, funds);
		_dealWithFundsDiff(date,commonOrMerchantNo);
	}

	/**
	 * 找出平台资金差异（非活动商户）
	 * @param date
     */
	private void _dealWithFundsDiff(String date,int commonOrMerchantNo) {
		Date dzDate = DateUtils.parseDate(date);
		String nextDay = DateUtils.getPreDateByDay(dzDate, "1");
		String endTime =DateUtils.getDateEndString(date);
		List<PlatformFunds> list=platformFundsDao.findCommonFundsDiff(date,endTime,nextDay);
		if(list.isEmpty()||list.size()==0||list==null){
			return;
		}
		List<SalesDifferencesDetail> diffList=_turnToDIff( date,list);
		_saveDiveDifference(diffList);
	}

	private List<SalesDifferencesDetail> _turnToDIff(String date,List<PlatformFunds> list) {
		List<SalesDifferencesDetail> diffList=new ArrayList<>();
		for(PlatformFunds v:list){
			SalesDifferencesDetail diff=new SalesDifferencesDetail();
			diff.setdType(v.getType());
			diff.setType(v.getType());
			diff.setdDate(date);
			diff.setdNumber(v.getId());
			diff.setdDifferenceMoney(v.getTotalDive().toString());
			diff.setdCondition(0);
			diffList.add(diff);
			diff.setdCreator("0");
			diff.setdDifferenceType(DifferenceConst.DIFFERENCE_TYPE_3+"");
		}
		return diffList;
	}

	private PlatformFunds _installFundsReport(Date dzDate, String beginTime, String endTime,int commonOrMerchantNo) {
		PlatformFunds funds = new PlatformFunds();
		funds.setDzDate(dzDate);
		Double qcye = _getQcye(beginTime, endTime,commonOrMerchantNo);
		String day = DateUtils.getPreDateByDay(dzDate, "1");
		funds.setQcye(qcye);
		Double recharge = _getTotalRecharge(beginTime, endTime,commonOrMerchantNo);
		funds.setRecharge(recharge);
		Double internalDeposit=_getInternalDeposit(beginTime, endTime,commonOrMerchantNo);
		funds.setInternalDeposit(internalDeposit);
		Double totalRecharge=Double.valueOf(CalculationUtils.add(recharge,internalDeposit));
		funds.setTotalRecharge(totalRecharge);
		funds.setCreateTime(DateUtils.getAfterDayDate("0"));
		//1=普通订单（非追期）；2：追期订单
		Double buyCommon=_getBuyTranceOrCommon(commonOrMerchantNo,CpConst.COMMONORDER,beginTime, endTime,day);
		Double buyTrance=_getBuyTranceOrCommon(commonOrMerchantNo,CpConst.TRANCEORDER,beginTime, endTime,day);
		funds.setTotalBuyTrance(buyTrance);
		funds.setTotalBuyCommon(buyCommon);
//		Double totalBuyEntrust = _getTotalBuyEntrust(beginTime, endTime);
		Double totalBuyEntrust = Double.valueOf(CalculationUtils.add(buyTrance, buyCommon));
		funds.setTotalBuyEntrust(totalBuyEntrust);
		Double totalBuyPick = _getTotalBuyPick(beginTime, endTime,commonOrMerchantNo,day);
		funds.setTotalBuyPick(totalBuyPick);
		Double totalBuy = Double.valueOf(CalculationUtils.add(totalBuyEntrust, totalBuyPick));
		funds.setTotalBuy(totalBuy);
		Double prizeAmt =0.0;
		Double totalWithdraw =0.0;
		Double withdraw =0.0;
		Double internalExtraction =0.0;
		Double totalRefundWithDraw=0.0;
		Double totalRefundTicketFail=0.0;
		Double commission=0.0;
		Double coupon=0.0;
			prizeAmt = _getTotalPrizeAmt(commonOrMerchantNo,beginTime, endTime);
			totalRefundWithDraw=_getRefundDrawOrTicket(commonOrMerchantNo,CpConst.REFUNDWITHDRAW,beginTime, endTime);
			totalRefundTicketFail=_getRefundDrawOrTicket(commonOrMerchantNo,CpConst.REFUNDTICKETFAIL,beginTime, endTime);
		withdraw =_getTotalWithdraw(commonOrMerchantNo,beginTime, endTime);
		internalExtraction =_getInternalExtraction(commonOrMerchantNo,beginTime, endTime);
		totalWithdraw = Double.valueOf(CalculationUtils.add(withdraw,internalExtraction));
		if(commonOrMerchantNo==1||commonOrMerchantNo==801){
			commission=_getMoneyByTypeOrChannel(CpConst.COMMISSION,beginTime, endTime);
		}if(commonOrMerchantNo==1||commonOrMerchantNo==701){
			coupon=_getMoneyByTypeOrChannel(CpConst.COUPON,beginTime, endTime);
		}
		funds.setCoupon(coupon);
		funds.setCommission(commission);
		funds.setTotalPrizeAmt(prizeAmt);
		funds.setWithdraw(withdraw);
		funds.setInternalExtraction(internalExtraction);
		funds.setTotalWithdraw(totalWithdraw);
		funds.setTotalRefundWithDraw(totalRefundWithDraw);
		funds.setTotalRefundTicketFail(totalRefundTicketFail);
//		Double totalRefund = _getTotalRefund(beginTime, endTime);
		Double totalRefund =Double.valueOf(CalculationUtils.add(totalRefundWithDraw, totalRefundTicketFail));
		funds.setTotalRefund(totalRefund);
		// T-1期末余额=T期初余额
		String dayBeginTime=DateUtils.getDateStartString(day);
		String dayEndTime =DateUtils.getDateEndString(day);
		Double qmye = _getQcye(dayBeginTime, dayEndTime,commonOrMerchantNo);
		funds.setQmye(qmye);
		Double totalDive =0.0;
		if(commonOrMerchantNo==CpConst.ISCOMMON||commonOrMerchantNo==401){
			 totalDive =_getTotalDive(funds);
		}else if(commonOrMerchantNo==301||commonOrMerchantNo==501||commonOrMerchantNo==601){
			totalDive =_get356TotalDive(funds, beginTime,  endTime, day);
		}else if(commonOrMerchantNo==701||commonOrMerchantNo==801){
			totalDive =_getCCDive(funds);
		}
		funds.setTotalDive(totalDive);
		// 处理总金额
		funds.setDealAmt(0.0);
		// 累计总差异额
		funds.setTotalSumDive(0.0);
		// 处理后总差异额
		funds.setDiveAfterDeal(0.0);
		if (totalDive.compareTo(0.0) == 0) {
			// 差异额为0，则此数据无需处理
			funds.setDealStatus(3);
		} else {
			funds.setDealStatus(0);
		}

		funds.setIsValid(1);
		funds.setType(commonOrMerchantNo);
		return funds;
	}

	private Double _getCCDive(PlatformFunds funds) {
		Double a=Double.valueOf(CalculationUtils.add(funds.getQcye(),funds.getTotalRecharge()));
		Double b=Double.valueOf(CalculationUtils.add(a,funds.getInternalDeposit()));
		Double d=Double.valueOf(CalculationUtils.sub(b,funds.getTotalWithdraw()));
		Double d1=Double.valueOf(CalculationUtils.sub(d,funds.getInternalExtraction()));
		Double e=Double.valueOf(CalculationUtils.sub(d1,funds.getCommission()));
		Double f=Double.valueOf(CalculationUtils.sub(e,funds.getCoupon()));
		Double calQmye=Double.valueOf(CalculationUtils.add(f,funds.getTotalRefund()));
		//数据库期末-计算后的期末
		Double totalDive=Double.valueOf(CalculationUtils.sub(funds.getQmye(),calQmye));
		return totalDive;
	}

	/**
	 * 内部取出
	 * @param commonOrMerchantNo
	 * @param beginTime
	 * @param endTime
     * @return
     */
	private Double _getInternalExtraction(int commonOrMerchantNo, String beginTime, String endTime) {
		Double withdraw = platformFundsDao.findInternalExtraction(commonOrMerchantNo, beginTime, endTime);
		return CalculationUtils.fomatMoney(withdraw);
	}

	/**
	 * 内部存入
	 * @param beginTime
	 * @param endTime
	 * @param commonOrMerchantNo
     * @return
     */
	private Double _getInternalDeposit(String beginTime, String endTime, int commonOrMerchantNo) {
		Double recharge = platformFundsDao.findInternalDeposit(commonOrMerchantNo, beginTime, endTime);
		return CalculationUtils.fomatMoney(recharge);
	}


	private Double _get356TotalDive(PlatformFunds funds,String beginTime, String endTime,String day) {
		Double a=Double.valueOf(CalculationUtils.add(funds.getQcye(),funds.getTotalRecharge()));
		Double buyMoney= CalculationUtils.fomatMoney(platformFundsDao.find356TotalBuy(beginTime, endTime, day));
		Double calQmye=Double.valueOf(CalculationUtils.sub(a,buyMoney));
		//数据库期末-计算后的期末
		Double totalDive=Double.valueOf(CalculationUtils.sub(funds.getQmye(),calQmye));
		return totalDive;
	}

	/**
	 * 5：出票失败退款 6：提现失败退款
	 * @param i
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	private Double _getRefundDrawOrTicket(Integer commonOrMerchantNo,int i, String beginTime, String endTime) {
		Double money = platformFundsDao.findRefundDrawOrTicket(commonOrMerchantNo,i, beginTime, endTime);
		return CalculationUtils.fomatMoney(money);
	}

	private Double _getMoneyByTypeOrChannel(Integer type, String beginTime, String endTime) {
		Double money = platformFundsDao.getMoneyByTypeOrChannel(type, beginTime, endTime);
		return CalculationUtils.fomatMoney(money);
	}
	/**
	 * 委托购彩金额
	 *  @param isCommon 1:正常(非活动) 2:移动活动,3：得仕捕鱼，4：得仕棋牌5：欧建商户
	 *  @param isTrance 1=普通订单（非追期）；2：追期订单
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	private Double _getBuyTranceOrCommon(int isCommon,int isTrance, String beginTime, String endTime,String day) {
		Double money = platformFundsDao.findBuyTranceOrCommon(isCommon,isTrance, beginTime, endTime,day);
		return CalculationUtils.fomatMoney(money);
	}

	private Double _getTotalDive(PlatformFunds funds) {
		Double a=Double.valueOf(CalculationUtils.add(funds.getQcye(),funds.getTotalRecharge()));
		Double a1=Double.valueOf(CalculationUtils.add(a,funds.getInternalDeposit()));
		Double b=Double.valueOf(CalculationUtils.sub(a1,funds.getTotalBuy()));
		Double c=Double.valueOf(CalculationUtils.add(b,funds.getTotalPrizeAmt()));
		Double d=Double.valueOf(CalculationUtils.sub(c,funds.getTotalWithdraw()));
		Double d1=Double.valueOf(CalculationUtils.sub(d,funds.getInternalExtraction()));
		Double e=Double.valueOf(CalculationUtils.add(d1,funds.getCommission()));
		Double f=Double.valueOf(CalculationUtils.add(e,funds.getCoupon()));
		Double calQmye=Double.valueOf(CalculationUtils.add(f,funds.getTotalRefund()));
		//数据库期末-计算后的期末
		Double totalDive=Double.valueOf(CalculationUtils.sub(funds.getQmye(),calQmye));
		return totalDive;
	}

	/**
	 * @param date
	 * @param funds
	 */
	private void _checkAndSaveFunds(String date, PlatformFunds funds) {
		List<PlatformFunds> isExit = platformFundsDao.findFundsListByDate(date, -1,funds.getType());
		if (isExit != null && !isExit.isEmpty() && isExit.size() != 0) {
				platformFundsDao.deleteList(isExit);
				platformFundsDao.insert(funds);
		} else {
			platformFundsDao.insert(funds);
		}
	}


	/**
	 * 退款总额
	 *
	 * @param
	 * @return
	 */
//	private Double _getTotalRefund(String beginTime, String endTime) {
//		Double refund = platformFundsDao.findMoneyByTypeAndDate(4, beginTime, endTime);
//		return CalculationUtils.fomatMoney(refund);
//	}
//	private Double _getActivityRecharge(String beginTime, String endTime) {
//		Double recharge = platformFundsDao.getActivityRecharge(beginTime, endTime);
//		return CalculationUtils.fomatMoney(recharge);
//	}
	/**
	 * 提现总额
	 *
	 * @param
	 * @return
	 */
	private Double _getTotalWithdraw(int commonOrMerchantNo,String beginTime, String endTime) {
		Double withdraw = platformFundsDao.findWithDrawMoney(commonOrMerchantNo, beginTime, endTime);
		return CalculationUtils.fomatMoney(withdraw);
	}

	/**
	 * 派奖总额
	 *
	 * @param
	 * @return
	 */
	private Double _getTotalPrizeAmt(Integer commonOrMerchantNo,String beginTime, String endTime) {
		Double totalPrizeAmt = platformFundsDao.findPrizeByDate(commonOrMerchantNo,beginTime, endTime);
		return CalculationUtils.fomatMoney(totalPrizeAmt);
	}

	/**
	 * 充值总额
	 *
	 * @param beginTime,endTime
	 * @return
	 */
	private Double _getTotalRecharge(String beginTime, String endTime,int commonOrMerchantNo) {
		Double recharge = platformFundsDao.findRechargeMoney(commonOrMerchantNo, beginTime, endTime);
		return CalculationUtils.fomatMoney(recharge);
	}

	/***
	 * 购彩金额_自取方式{购买方式( 1:委托出票；2:自己取票))}
	 *
	 * @param
	 * @return
	 */
	private Double _getTotalBuyPick(String beginTime, String endTime, int commonOrMerchantNo,String day) {
		Double totalBuyPick = platformFundsDao.findTotalBuyPick(beginTime, endTime,commonOrMerchantNo, day);
		return CalculationUtils.fomatMoney(totalBuyPick);
	}

//	private Double _getActivityTotalBuyEntrust(String beginTime, String endTime, int activityType) {
//		Double buy = platformFundsDao.findActivityTotalBuyEntrust(beginTime, endTime,activityType);
//		return CalculationUtils.fomatMoney(buy);
//	}

	/**
	 * 购彩金额——委托方式(购买方式( 1:委托出票；2:自己取票))
	 *
	 * @param
	 * @return
	 */
//	private Double _getTotalBuyEntrust(String beginTime, String endTime) {
//		Double totalBuyEntrust = platformFundsDao.findTotalBuyEntrust(beginTime, endTime);
//		return CalculationUtils.fomatMoney(totalBuyEntrust);
//	}

	/**
	 * 获取某日期初余额
	 *
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	private Double _getQcye(String beginTime, String endTime,int commonOrMerchantNo) {
		Double qcye = platformFundsDao.findQcyeByDate(beginTime, endTime,commonOrMerchantNo);
		return CalculationUtils.fomatMoney(qcye);
	}

//	private Double _getActivityQcye(String beginTime, String endTime) {
//		Double qcye = platformFundsDao.findActivityQcyeByDate(beginTime, endTime);
//		return CalculationUtils.fomatMoney(qcye);
//	}


	// ///---------------------------------投注系统与第三方流水对账------------------------------------------------------

	/**
	 * 存储平台资金流水与第三方流水对账数据
	 *
	 * @param date
	 */
	@Override
	public void execThirdpayTask( String date) {
		PlatformFundsRunning run = new PlatformFundsRunning();
		Date dzDate = DateUtils.parseDate(date);
		run.setDzDate(dzDate);
		String beginTime =DateUtils.getDateStartString(date);
		String endTime = DateUtils.getDateEndString(date);
		//内部存入金额
		Double insideRecharge=_getCpTotalAmtbyTypeAndDate(7, beginTime, endTime);
		run.setInsideRecharge(insideRecharge);
		// cp系统交易渠道(1:微信 2:得仕通 3:银联 4:余额）
		Double totalUnionAmt = _getCpTotalAmtbyTypeAndDate(CpConst.RECHARGECHANNEL_UNIONPAY, beginTime, endTime);
		run.setTotalUnionAmt(totalUnionAmt);
		Double totalDaysAmt = _getCpTotalAmtbyTypeAndDate(CpConst.RECHARGECHANNEL_DAYSPAY, beginTime, endTime);
		run.setTotalDaysAmt(totalDaysAmt);
		Double totalWechatAmt = _getCpTotalAmtbyTypeAndDate(CpConst.RECHARGECHANNEL_WECHAT, beginTime, endTime);
		run.setTotalWechatAmt(totalWechatAmt);
		Double totalAmt = _getTotalAmt(run, 1);
		run.setTotalAmt(totalAmt);
		// 第三方支付渠道 1=微信， 2:得仕通 3:银联
		Double totalRunUnionAmt = _getThirdMoneyByTypeAndDate(CpConst.RECHARGECHANNEL_UNIONPAY, beginTime, endTime);
		run.setTotalRunUnionAmt(totalRunUnionAmt);
		Double totalRunDaysAmt = _getThirdMoneyByTypeAndDate(CpConst.RECHARGECHANNEL_DAYSPAY, beginTime, endTime);
		run.setTotalRunDaysAmt(totalRunDaysAmt);
		Double totalRunWechatAmt = _getThirdMoneyByTypeAndDate(CpConst.RECHARGECHANNEL_WECHAT, beginTime, endTime);
		run.setTotalRunWechatAmt(totalRunWechatAmt);
		Double totalRunAmt = _getTotalAmt(run, 2);
		run.setTotalRunAmt(totalRunAmt);
		// 差异额=投注系统总额-第三方总额
		Double dive = Double.valueOf(CalculationUtils.sub(totalAmt, totalRunAmt));
		run.setTotalDive(dive);
		// 比对总金额，总条数，不一致就开始对账
		Integer cpTotalCount = _getTotalCount(1, beginTime, endTime);
		Integer thirdTotalCount = _getTotalCount(2, beginTime, endTime);
		Integer diveCount = cpTotalCount - thirdTotalCount;
		run.setDiveAfterDeal(0.0);
		if (dive.compareTo(0.0) == 0 && diveCount == 0) {
			run.setDealStatus(3);
			run.setDealAmt(0.0);
			run.setTotalSumDive(0.0);
		} else {
			// 开始对账
			_setValueForDz(run, beginTime, endTime, diveCount, dive, dzDate);

		}
		run.setCreateTime(DateUtils.getAfterDayDate("0"));
		run.setIsValid(1);
		_checkAndSaveRunning(date, run);

	}



	/**
	 * 保存第三方对账
	 *
	 * @param run
	 */
	private void _checkAndSaveRunning(String date, PlatformFundsRunning run) {
		List<PlatformFundsRunning> isExit = platformFundsDao.findFundsRunningListByDate(date, -1);
		if (isExit != null && !isExit.isEmpty() && isExit.size() != 0) {
				platformFundsDao.deleteRunList(isExit);
				platformFundsDao.insertRun(run);
		} else {
			platformFundsDao.insertRun(run);
		}
	}

	private void _setValueForDz(PlatformFundsRunning run, String beginTime, String endTime, Integer diveCount, Double dive, Date dzDate) {
		Double dealSumMoney = _dealWithDiveAndCount(beginTime, endTime);
		if (dealSumMoney.compareTo(dive) == 0) {
			run.setDealStatus(2);
		} else {
			run.setDealStatus(0);
		}

		Double diveAfterDeal = Double.valueOf(CalculationUtils.sub(run.getTotalDive(), dealSumMoney));
		// 处理后总差异额=总差异额-处理总金额
		run.setDiveAfterDeal(diveAfterDeal);
		// 处理总金额
		run.setDealAmt(Double.valueOf(CalculationUtils.add(0, dealSumMoney)));
		// 累计总差异额=往日累计差异额
		Double totalSumDive = _getTotalSumDive(dzDate, dealSumMoney);
		run.setTotalSumDive(totalSumDive);
	}

	/**
	 * 累计总差异额
	 *
	 * @param dzDate
	 * @param dealSumMoney
	 * @return
	 */
	private Double _getTotalSumDive(Date dzDate, Double dealSumMoney) {
		String date = DateUtils.getPreDateByDay(dzDate, "-1");
		PlatformFundsRunning o = new PlatformFundsRunning();
		o.setDzDate(DateUtils.parseDate(date));
		List<PlatformFundsRunning> runList = platformFundsDao.findRunning(o);
		if (runList.size() != 0 && !runList.isEmpty() && runList != null) {
			PlatformFundsRunning run = runList.get(0);
			Double dive = Double.valueOf(CalculationUtils.add(run.getTotalSumDive(), dealSumMoney));
			return dive;
		}
		return 0.0;
	}

	/**
	 * 获取流水总条数
	 *
	 * @param i 类型1：彩票系统 2.第三方
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	private Integer _getTotalCount(int i, String beginTime, String endTime) {
		Integer count = 0;
		// (1:微信 2:得仕通 3:银联 4:余额）
		if (i == 1) {
			Integer wechantCount = _getCpTotalCountByTypeAndDate(CpConst.RECHARGECHANNEL_WECHAT, beginTime, endTime);
			Integer daysCount = _getCpTotalCountByTypeAndDate(CpConst.RECHARGECHANNEL_DAYSPAY, beginTime, endTime);
			Integer unionCount = _getCpTotalCountByTypeAndDate(CpConst.RECHARGECHANNEL_UNIONPAY, beginTime, endTime);
			count = wechantCount + daysCount + unionCount;
		} else if (i == 2) {
			Integer thirdWeChantCount = _getThirdCountByTypeAndDate(CpConst.RECHARGECHANNEL_WECHAT, beginTime, endTime);
			Integer thirddaysCount = _getThirdCountByTypeAndDate(CpConst.RECHARGECHANNEL_DAYSPAY, beginTime, endTime);
			Integer thirdUnionCount = _getThirdCountByTypeAndDate(CpConst.RECHARGECHANNEL_UNIONPAY, beginTime, endTime);
			count = thirddaysCount + thirdUnionCount + thirdWeChantCount;
		}
		return count;
	}

	/**
	 * 第三方流水数据总条数
	 *
	 * @param i 第三方支付渠道 1=微信，2=得仕通,3银联，
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	private Integer _getThirdCountByTypeAndDate(int i, String beginTime, String endTime) {
		Integer count = platformFundsDao.findThirdCountByTypeAndDate(i, beginTime, endTime);
		if (count == null) { return 0; }
		return count;
	}

	/**
	 * 获取彩票系统流水总条数
	 *
	 * @param i cp系统交易渠道(1:微信 2:得仕通 3:银联 4:余额）
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	private Integer _getCpTotalCountByTypeAndDate(int i, String beginTime, String endTime) {
		Integer count = platformFundsDao.findCpTotalCountByTypeAndDate(i, beginTime, endTime);
		if (count == null) { return 0; }
		return count;
	}

	/**
	 * 开始对账，对出差异额（//依次比对各渠道的金额、条数）
	 *
	 * @param beginTime
	 * @param endTime
	 */
	private Double _dealWithDiveAndCount(String beginTime, String endTime) {
		List<SalesDifferencesDetail> list = _dealWithDive( beginTime,  endTime);
		if (list.size() != 0 && !list.isEmpty() && list != null) {
			_saveDiveDifference(list);
		}
		Double dealMoney = _getTotalDealMoney( beginTime,  endTime);
		return dealMoney;
	}

	private Double _getTotalDealMoney(String beginTime, String endTime) {
		Double money = platformFundsDao.findTotalDealMoney( beginTime,  endTime);
		return CalculationUtils.fomatMoney(money);
	}

	/**
	 * 开始对账
	 *
	 * @param
	 */
	private List<SalesDifferencesDetail> _dealWithDive(String beginTime, String endTime) {
		List<FundsDiveVo> voList = platformFundsDao.findDiveVoListBetweenCpAndThird( beginTime,  endTime);
		Double money = 0.0;
		List<SalesDifferencesDetail> list = new ArrayList<>();
		if (!voList.isEmpty() && voList.size() != 0 && voList != null) {
			for (FundsDiveVo vo : voList) {
				money = Double.valueOf(CalculationUtils.add(money, vo.getPayMoney()));
				SalesDifferencesDetail detail = new SalesDifferencesDetail();
				_judgeTypeAndSetValue(vo, detail, beginTime, list);

			}
		}
		return list;
	}

	/**
	 * 设置差异对象差异类型等值
	 *
	 * @param vo
	 * @param detail
	 * @param date
	 * @param list
	 */
	private void _judgeTypeAndSetValue(FundsDiveVo vo, SalesDifferencesDetail detail, String date, List<SalesDifferencesDetail> list) {
		_setParam(date, detail, list, vo);
		if (DifferenceConst.DIFFERENCE_TYPE_1.toString().equals(vo.getType())) {
			_setCpOkDetail(vo, detail);
		} else if (DifferenceConst.DIFFERENCE_TYPE_2.toString().equals(vo.getType())) {
			_setThirdOkDetail(vo, detail);
		} else if (DifferenceConst.DIFFERENCE_TYPE_3.toString().equals(vo.getType())) {
			_setMoneyDiveDetail(vo, detail);
		} else if (DifferenceConst.DIFFERENCE_TYPE_4.toString().equals(vo.getType())) {
			_setDateDiveDetail(vo, detail);
		}
	}

	private void _setParam(String date, SalesDifferencesDetail detail, List<SalesDifferencesDetail> list, FundsDiveVo vo) {
		detail.setdDifferenceMoney(vo.getPayMoney().toString());
		detail.setdDifferenceType(vo.getType());
		String payFlowNo = vo.getPayNo();
		detail.setdNumber(payFlowNo);
		detail.setdType(DifferenceConst.TYPE_2);
		detail.setdDate(date);
		detail.setdCreateTime(DateUtils.getAfterDayDate("0"));
		detail.setdCreator("0");
		detail.setdResult(DifferenceConst.DIFFERENCE_RESULT_NO + "");
		detail.setdCondition(DifferenceConst.DIFFERENCE_RESULT_NO);
		detail.setType(0);
		list.add(detail);
	}

	private void _setDateDiveDetail(FundsDiveVo vo, SalesDifferencesDetail detail) {
		String third = _getChannelName(vo.getPayWay());
		String payFlowNo = vo.getPayNo();
		String msg = DifferenceConst.DIFFERENCE_REASON_DATE.replace("thirdDate", vo.getPayTime()).replace("payFlowNo", payFlowNo).replace("third", third);
		detail.setdCause(msg);
	}

	private void _setMoneyDiveDetail(FundsDiveVo vo, SalesDifferencesDetail detail) {
		String third = _getChannelName(vo.getPayWay());
		String msg = DifferenceConst.DIFFERENCE_REASON_MONEY.replace("thirdMoney", vo.getPayMoney().toString()).replace("payFlowNo", vo.getPayNo()).replace("third", third);
		detail.setdCause(msg);
	}

	private void _setThirdOkDetail(FundsDiveVo vo, SalesDifferencesDetail detail) {
		String third = _getChannelName(vo.getPayWay());
		String msg = DifferenceConst.DIFFERENCE_REASON_STATUS.replace("payFlowNo", vo.getPayNo()).replace("third", third);
		detail.setdCause(msg);

	}

	private void _setCpOkDetail(FundsDiveVo vo, SalesDifferencesDetail detail) {
		String us = _getChannelName(vo.getPayWay());
		String msg = DifferenceConst.DIFFERENCE_REASON_STATUS_US.replace("payFlowNo", vo.getPayNo()).replace("us", us);
		detail.setdCause(msg);


	}

	private String _getChannelName(Integer cpWay) {
		String us = "微信";
		if (cpWay == CpConst.RECHARGECHANNEL_DAYSPAY) {
			us = "得仕通";
		} else if (cpWay == CpConst.RECHARGECHANNEL_UNIONPAY) {
			us = "银联";
		}
		return us;
	}

	/**
	 * 批量保存差异到差异表
	 *
	 * @param list
	 */
	private void _saveDiveDifference(List<SalesDifferencesDetail> list) {

		List<SalesDifferencesDetail> isExit = salesDifferencesDetailDao.find(list.get(0));
		if (isExit != null && !isExit.isEmpty() && isExit.size() != 0) {
				salesDifferencesDetailDao.deleteDifference(list.get(0));
				salesDifferencesDetailDao.insertDifference(list);
		} else {
			salesDifferencesDetailDao.insertDifference(list);
		}

	}

	/**
	 * 第三方流水数据（beginTime,endTime）
	 *
	 * @param i,第三方支付渠道 1=微信，3=银联，2=得仕通
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	private Double _getThirdMoneyByTypeAndDate(int i, String beginTime, String endTime) {
		Double money = platformFundsDao.findThirdMoneyByType(i, beginTime, endTime);
		return CalculationUtils.fomatMoney(money);
	}

	/**
	 * 彩票平台交易总金额
	 *
	 * @param run
	 * @return
	 */
	private Double _getTotalAmt(PlatformFundsRunning run, Integer type) {
		Double a = 0.0;
		Double all = 0.0;
		if (type == 1) {
			a = Double.valueOf(CalculationUtils.add(run.getTotalDaysAmt(), run.getTotalWechatAmt()));
			all = Double.valueOf(CalculationUtils.add(a, run.getTotalUnionAmt()));
		} else {
			a = Double.valueOf(CalculationUtils.add(run.getTotalRunDaysAmt(), run.getTotalRunWechatAmt()));
			all = Double.valueOf(CalculationUtils.add(a, run.getTotalRunUnionAmt()));
		}
		return all;
	}

	/**
	 * 获得交易金额
	 *
	 * @param i 交易渠道(1:微信 2:得仕通 3:银联 4:余额）
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	private Double _getCpTotalAmtbyTypeAndDate(int i, String beginTime, String endTime) {
		Double money = platformFundsDao.findCpMoneyByType(i, beginTime, endTime);
		return CalculationUtils.fomatMoney(money);
	}


	/**
	 * 保存平台资金差异
	 *
	 * @param form
	 */
	@Override
	public void saveDiveDifference(SalesDifferencesDetail form) {
		List<SalesDifferencesDetail> list = new ArrayList<>();
		list.add(form);
		salesDifferencesDetailDao.insertDifference(list);
	}

	/**
	 * 查询某日差异记录
	 *
	 * @param form
	 * @param page
	 * @return
	 */
	@Override
	public Page<SalesDifferencesDetail> findSalesDifferencesDetailByPage(SalesDifferencesDetail form, Page<SalesDifferencesDetail> page) {
		page.setResults(salesDifferencesDetailDao.findDetailByDateAndType(form, page));
		return page;
	}

	/**
	 * 获得某天第三方对账记录
	 *
	 * @param o
	 * @return
	 */
	@Override
	public List<PlatformFundsRunning> findRunning(PlatformFundsRunning o) {
		return platformFundsDao.findRunning(o);
	}

	/**
	 * 修改某天第三方对账表记录
	 *
	 * @param funds
	 */
	@Override
	public void updateRunning(PlatformFundsRunning funds) {
		platformFundsDao.updateRunning(funds);
	}

	@Override
	public Page<CashDifference> findCashDifferenceListByPage(String beginTime, String endTime, Page<CashDifference> page) {

		return null;
	}

	////------------------------------------报表生成------------------------------------------------/
	/**
	 * 生成平台资金对账报表
	 * @param month
	 */
	@Override
	public  void GenerateFundsReport(String month){
//		_generateFundsReport(month,1);

//		generateDiffReport(month,DifferenceConst.TYPE_1,1);
		//生成活动报表
		_generateReport(month);



	}

	private void _generateReport(String month) {
		//同一excel展示在不同sheet里
//		List<MerchantActivity> merchantList=platformFundsDao.findValidMerchantList();
//		if(merchantList!=null&&merchantList.size()!=0&&!merchantList.isEmpty()){
//			for(MerchantActivity m:merchantList){
//				_generateFundsReport(month,Integer.parseInt(m.getMerchantNo()));
//			}
//		}
		ExcelReport ep = _installExcelObj(month);
		try {
			excelutil.GenerateExcel(ep);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ExcelReport _installExcelObj(String month) {
		List<ExcelReport> excelList=new ArrayList<>();
		ExcelReport ep1 = _install4ExcelObj(month);
		ExcelReport ep2 = _install356ExcelObj(month);
		ExcelReport ep3 = _installFundsExcelObj(month,1);
		ExcelReport ep4=_installDiffExcelObj(month,DifferenceConst.TYPE_1,1);
		excelList.add(ep1);
		excelList.add(ep2);
		excelList.add(ep3);
		excelList.add(ep4);
		ExcelReport ep =new ExcelReport();
		StringBuffer sbf = new StringBuffer();
		String fileName = platFormFundsFileName;
		ep.setFileName(sbf.append(fileName).append(month).toString());
		ep.setSheet(true);
		ep.setList(excelList);
		return ep;
	}

//	private void _generate4Excel(List<ExcelReport> excelList,String month) {
//		ExcelReport ep = _install4ExcelObj(month);
//		try {
//			excelutil.GenerateExcel(ep);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}



//	private void _generate356Excel(String month) {
//		ExcelReport ep = _install356ExcelObj(month);
//		try {
//			excelutil.GenerateExcel(ep);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	private ExcelReport _install356ExcelObj(String month) {
		ExcelReport ep = new ExcelReport();
		StringBuffer sbf = new StringBuffer();
		String fileName = platFormFundsCMCCFileName;
		ep.setTitle(fileName);
		String headers[] = new String[] {"日期","期初余额","充值金额","购彩金额（委托）,中国移动/得仕捕鱼/得仕棋牌","期末余额","总差异额"};
		String attrs[] = new String[] {
				"dzDate", "qcye", "totalRecharge",  "totalBuyCMCC","totalBuyFISH","totalBuyCARD","qmye","totalDive" };

		JSONArray data=_install356Data(month);
		ep.setHeader(headers);
		ep.setAttrs(attrs);
		ep.setData(data);
		ep.setHeaderType(1);
		return ep;
	}

	private ExcelReport _install4ExcelObj(String month) {
		ExcelReport ep = new ExcelReport();
		StringBuffer sbf = new StringBuffer();
		String fileName = platFormFundsOUJIANFileName;
		ep.setTitle(fileName);
		String headers[] = new String[] {"日期","期初余额","充值金额","购彩金额（委托）欧建活动","期末余额","总差异额"};
		String attrs[] = new String[] {
				"dzDate", "qcye", "totalRecharge","totalBuyOUJIAN","qmye","totalDive" };

		JSONArray data=_install4Data(month);
		ep.setHeader(headers);
		ep.setAttrs(attrs);
		ep.setData(data);
		return ep;
	}



	private JSONArray _install356Data(String month) {
		List<FundsActivityReportVo> list=new ArrayList<>();
		List<PlatformFunds> list2 = platformFundsDao.findMonth(month,301);
		List<PlatformFunds> list3 = platformFundsDao.findMonth(month,601);
		List<PlatformFunds> list4 = platformFundsDao.findMonth(month,501);

		for(int i=0;i<list2.size();i++){
			FundsActivityReportVo vo=new FundsActivityReportVo();
			vo.setDzDate(list2.get(i).getDzDate());
			vo.setQcye(list2.get(i).getQcye());
			vo.setTotalRecharge(list2.get(i).getTotalRecharge());
			vo.setTotalBuyCMCC(list2.get(i).getTotalBuyEntrust());
			vo.setTotalBuyFISH(list3.get(i).getTotalBuyEntrust());
			vo.setTotalBuyCARD(list4.get(i).getTotalBuyEntrust());
			vo.setQmye(list2.get(i).getQmye());
			vo.setTotalDive(list2.get(i).getTotalDive());
			list.add(vo);
		}
		JSONArray data= JSONArray.fromObject(list);
		return data;
	}
	private JSONArray _install4Data(String month) {
		List<FundsActivityReportVo> listVo=new ArrayList<>();
		List<PlatformFunds> list = platformFundsDao.findMonth(month,401);
		for(int i=0;i<list.size();i++){
			FundsActivityReportVo vo=new FundsActivityReportVo();
			vo.setDzDate(list.get(i).getDzDate());
			vo.setQcye(list.get(i).getQcye());
			vo.setTotalBuyOUJIAN(list.get(i).getTotalBuyEntrust());
			vo.setQmye(list.get(i).getQmye());
			vo.setTotalDive(list.get(i).getTotalDive());
			listVo.add(vo);
		}
		JSONArray data= JSONArray.fromObject(listVo);
		return data;
	}

//	private Double _findActicityDive(PlatformFunds vo,String beginTime, String endTime) {
//		Double a=Double.valueOf(CalculationUtils.add(vo.getQcye(),vo.getTotalRecharge()));
//		Double buyMoney= CalculationUtils.fomatMoney(platformFundsDao.findActivityTotalBuy(beginTime, endTime));
//		Double calQmye=Double.valueOf(CalculationUtils.sub(a,buyMoney));
//		//数据库期末-计算后的期末
//		Double totalDive=Double.valueOf(CalculationUtils.sub(vo.getQmye(),calQmye));
//		return totalDive;
//	}


//	private void _generateFundsReport( String month,Integer commonOrMerchantNo) {
//		ExcelReport ep = _installFundsExcelObj(month,commonOrMerchantNo);
//		try {
//			excelutil.GenerateExcel(ep);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}

	private ExcelReport _installFundsExcelObj(String month,int commonOrMerchantNo) {
		ExcelReport ep = new ExcelReport();
		StringBuffer sbf = new StringBuffer();
		String fileName = platFormFundsFileName;
		ep.setTitle(fileName);
		ep.setFileName(sbf.append(fileName).append(month).toString());
		String headers[] = new String[] {"日期","期初余额","充值金额,普通充值/内部存入/合计","方式一（自取）","方式二（委托）,购彩/追期","购彩金额（小计）","派奖金额","提现金额,普通提现/内部取出/合计","退款金额,出票/提现","佣金","优惠卷","期末余额","总差异额"};
		String attrs[] = new String[] {
				"dzDate", "qcye", "recharge", "internalDeposit", "totalRecharge", "totalBuyPick", "totalBuyCommon","totalBuyTrance", "totalBuy", "totalPrizeAmt", "withdraw", "internalExtraction", "totalWithdraw","totalRefundTicketFail", "totalRefundWithDraw","commission","coupon","qmye","totalDive" };
		JSONArray data=_installFundsExcelData(month,commonOrMerchantNo);
		ep.setHeader(headers);
		ep.setAttrs(attrs);
		ep.setData(data);
		ep.setHeaderType(1);
		return ep;
	}

	private JSONArray _installFundsExcelData(String month,int commonOrMerchantNo) {
		List<PlatformFunds> list = platformFundsDao.findMonth(month,commonOrMerchantNo);
		if(list==null){
			list=new ArrayList<>();
		}
		JSONArray data= JSONArray.fromObject(list);
		return data;
	}


	@Override
	public Page<FundsActivityReportVo> findActivityFundsListByPage(String beginTime, String endTime, Integer dealResultStatus, Integer fundsType, Page<FundsActivityReportVo> pageVo) {
		Page<PlatformFunds> page=new Page<>();
		page.setPageNum(pageVo.getPageNum());
		page.setPageSize(pageVo.getPageSize());
		if(fundsType==301||fundsType==501||fundsType==601){
			pageVo=_installReport(beginTime, endTime, dealResultStatus,fundsType,page,pageVo);
			return pageVo;
		}else {
		List<PlatformFunds> fundsList=platformFundsDao.findFundsListByPage(beginTime, endTime, dealResultStatus,fundsType,page);
		if(fundsList==null){
			return new Page<FundsActivityReportVo>();
		}else{
			pageVo.setPageSize(page.getPageSize());
			pageVo.setTotalPage(page.getTotalPage());
			pageVo.setTotalRecord(page.getTotalRecord());
			List<FundsActivityReportVo> voList=new ArrayList<>();
			for(int i=0;i<fundsList.size();i++){
				FundsActivityReportVo vo=new FundsActivityReportVo();
				vo.setDzDate(fundsList.get(i).getDzDate());
				vo.setQcye(fundsList.get(i).getQcye());
				vo.setTotalRecharge(fundsList.get(i).getTotalRecharge());
				vo.setTotalBuyOUJIAN(fundsList.get(i).getTotalBuyEntrust());
				vo.setQmye(fundsList.get(i).getQmye());
				vo.setTotalDive(fundsList.get(i).getTotalDive());
				voList.add(vo);
			}
			pageVo.setResults(voList);
			return pageVo;
		}
		}
	}

	private Page<FundsActivityReportVo>  _installReport(String beginTime, String endTime, Integer dealResultStatus, Integer fundsType, Page<PlatformFunds> page,Page<FundsActivityReportVo> pageVo) {
		List<PlatformFunds> fundsList=platformFundsDao.findFundsListByPage(beginTime, endTime, dealResultStatus,301,page);
		List<PlatformFunds> fundsList3=platformFundsDao.findFundsListByPage(beginTime, endTime, dealResultStatus,601,page);
		List<PlatformFunds> fundsList4=platformFundsDao.findFundsListByPage(beginTime, endTime, dealResultStatus,501,page);
		if(fundsList==null){
			return new Page<FundsActivityReportVo>();
		}else{
			pageVo.setPageSize(page.getPageSize());
			pageVo.setTotalPage(page.getTotalPage());
			pageVo.setTotalRecord(page.getTotalRecord());
			List<FundsActivityReportVo> voList=new ArrayList<>();
			for(int i=0;i<fundsList.size();i++){
				FundsActivityReportVo vo=new FundsActivityReportVo();
				vo.setDzDate(fundsList.get(i).getDzDate());
				vo.setQcye(fundsList.get(i).getQcye());
				vo.setTotalRecharge(fundsList.get(i).getTotalRecharge());
				vo.setTotalBuyCMCC(fundsList.get(i).getTotalBuyEntrust());
				vo.setTotalBuyFISH(fundsList3.get(i).getTotalBuyEntrust());
				vo.setTotalBuyCARD(fundsList4.get(i).getTotalBuyEntrust());
				vo.setQmye(fundsList.get(i).getQmye());
				vo.setTotalDive(fundsList.get(i).getTotalDive());
				voList.add(vo);
			}
			pageVo.setResults(voList);
			return pageVo;
		}
	}

	/**
	 * 生成第三方对账报表
	 * @param auto
	 * @param month
	 */
	@Override
	public  void generateRunningReport(String auto, String month){
		_generateRunningReport(month);
//		generateDiffReport(month, DifferenceConst.TYPE_2, 0);

	}



	private void _generateRunningReport( String month) {
		ExcelReport ep=_installEp(month);
		try {
			excelutil.GenerateExcel(ep);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	private ExcelReport _installEp(String month) {
		List<ExcelReport> list=new ArrayList<>();
		ExcelReport ep=new ExcelReport();
		ep.setSheet(true);
		StringBuffer sbf = new StringBuffer();
		String fileName = platFormFundsRunningFileName;
		ep.setFileName(sbf.append(fileName).append(month).toString());
		ExcelReport ep1=_installRunningExcelObj(month);
		ExcelReport ep2=_installDiffExcelObj(month, DifferenceConst.TYPE_2, 0);
		list.add(ep1);
		list.add(ep2);
		ep.setList(list);
		return  ep;
	}

	private ExcelReport _installRunningExcelObj(String month) {
		ExcelReport ep = new ExcelReport();
		StringBuffer sbf = new StringBuffer();
		String fileName = platFormFundsRunningFileName;
		ep.setTitle(fileName);
//		ep.setFileName(sbf.append(fileName).append(month).toString());
		String headers[] = new String[] {"日期","内部存入","投注系统,银联/得仕通/微信/小计","第三方流水,银联/得仕通/微信/ 小计","差异额"};
		String attrs[] = new String[] {
				"dzDate","insideRecharge", "totalUnionAmt", "totalDaysAmt", "totalWechatAmt", "totalAmt", "totalRunUnionAmt", "totalRunDaysAmt", "totalRunWechatAmt", "totalRunAmt","totalDive" };
		JSONArray data=_installFundsRunningExcelData(month);
		ep.setHeader(headers);
		ep.setAttrs(attrs);
		ep.setData(data);
		ep.setHeaderType(1);
		return ep;
	}

	private JSONArray _installFundsRunningExcelData(String month) {
		List<PlatformFundsRunning> list = platformFundsDao.findMonthRunning(month);
		if(list==null){
			list=new ArrayList<>();
		}
		JSONArray data= JSONArray.fromObject(list);
		return data;
	}

	public void   generateDiffReport(String month, Integer type, Integer fundsType) {
		ExcelReport ep=_installDiffExcelObj(month,type,fundsType);
		try {
			excelutil.GenerateExcel(ep);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ExcelReport _installDiffExcelObj(String month, Integer type, Integer fundsType) {
		ExcelReport ep = new ExcelReport();
		StringBuffer sbf = new StringBuffer();
		String fileName="";
		if(type ==1){
			fileName=platFormFundsDiffFileName;
		}else if(type ==2){
			fileName=platFormFundsRunningDiffFileName;
		}
		ep.setTitle(fileName);
		ep.setFileName(sbf.append(fileName).append(month).toString());
		String headers[] = new String[] {"日期","编号","差异金额","差异类型(1:彩票系统有 第三方无 2:彩票系统无 第三方有 3:金额差异 4:时间差异)","具体原因","建议处理意见","处理结果","处理状态","处理人","处理时间","类型(0:第三方流水 1:平台资金正常（非活动） 2：平台资金(移动活动)"};
		String attrs[] = new String[] {
				"dDate", "dNumber", "dDifferenceMoney", "dDifferenceType", "dCause", "dOpinion", "dResult", "dCondition","dCreator","dCreateTime","type" };
		JSONArray data=_installDiffExcelData(month,fundsType,type);
		ep.setHeader(headers);
		ep.setAttrs(attrs);
		ep.setData(data);
		return ep;
	}

	private JSONArray _installDiffExcelData(String month, Integer fundsType, Integer type) {
		SalesDifferencesDetail m = new SalesDifferencesDetail();
		m.setdType(type);
		m.setdDate(month);
		m.setType(fundsType);
		List<SalesDifferencesDetail> list = salesDifferencesDetailDao.findMonth(m);
		if(list==null){
			list=new ArrayList<>();
		}
		JSONArray data= JSONArray.fromObject(list);
		return data;
	}


}
