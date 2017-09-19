package com.jy.repository.system.finance.reconciliation.lottery;

import com.jy.entity.system.channels.OrderDetail;
import com.jy.entity.system.channels.TicketInfoDetailPO;
import com.jy.entity.system.finance.CpOrderInfo;
import com.jy.entity.system.finance.SalesSum;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import java.util.List;

/**
 *
 */
@JYBatis
public interface SalesSumDao extends BaseDao<SalesSum>{
	/**
	 * 查询投注系统销售总报表数据
	 * @param date		查询总报表日期数据
     * @return
     */
	public SalesSum findMarket(@Param("date") String date);

	/**
	 * 整合投注系统销售总报表,总金额明细
	 * @param ticketTime
	 * @param ticketDate
     * @return
     */
	public List<TicketInfoDetailPO> findTicketInfoDetail(@Param("ticketTime") String ticketTime,@Param("ticketDate") String ticketDate);

	/**
	 * 统计总金额
	 */
	public SalesSum findSumMoney(@Param("ticketTime") String ticketTime,@Param("ticketDate") String ticketDate);

	/**
	 * 查询购彩明细
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CpOrderInfo> findTicketInfoList(Map map) throws Exception;
	public List<OrderDetail> findTicketInfoDetailList(Map map) throws Exception;
	public List<OrderDetail> findTicketInfoDetailListMerchant(Map map) throws Exception;
	public Double sumSalesTicketInfo(Map map) throws Exception;
	public Integer sumFindTicketInfoList(Map map) throws Exception;
	public void insertOrderList(List<OrderDetail> list) throws Exception;
	public void deleteDetailListIfExist(Map map) throws Exception;
}
