package com.jy.controller.system.channels;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jy.common.utils.HttpClientUtil;
import com.jy.common.utils.HttpUtil;
import com.jy.entity.system.channels.Prepayment;
import com.jy.repository.system.channels.PrepaymentDao;
import com.jy.service.system.channels.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;

import com.jy.common.utils.JsonUtil;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.controller.controllerUtils.controllerUtil;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.channels.Merchant;
import com.jy.entity.system.dict.SysDict;
import com.jy.mybatis.Page;
import com.jy.service.system.account.AccountService;
import com.jy.service.system.channels.AuditService;
import com.jy.service.system.dict.SysDictService;

/**
 * Created by Administrator on 2016/12/29. 审核(二次)
 */
@Controller
@RequestMapping("/channels/secondaryAudit/")
public class SecondaryAuditController extends BaseController<Object> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${secondaryAuditUrl}")
	private String path;
    @Autowired
    private AuditService service;
    @Autowired
    private PrepaymentDao prepaymentDao;
    @Autowired
    private MerchantService merchantService;


	/**
	 * 审核页面
	 *
	 * @param model
	 * @return
	 */
    @RequestMapping("secondaryAuditIndex")
    public String salesSumDifferences(Model model){
        if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
            model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
            return "/system/channels/secondaryAudit";
        }
        return Const.NO_AUTHORIZED_URL;
    }

    /**
	 * 审核数据查询(二审)
	 *
	 * @param page
	 * @param o
	 * @return
	 */
    @RequestMapping(value="findByPage", method= RequestMethod.POST)
    @ResponseBody
    public AjaxRes findByPage(Page<Object> page, Merchant o){
        String userId= AccountShiroUtil.getCurrentUser().getAccountId();
        o.setUserId(userId);
        o.setmStatus(1);
        AjaxRes ar=getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/channels/secondaryAudit/secondaryAuditIndex"))){
            try {
                Page<Object> byPage=service.findByPage(o, page);
                Map<String, Object> p=new HashMap<String, Object>();
                p.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
                p.put("list",byPage);
                ar.setSucceed(p);
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    /**
	 * 审核(二审)
	 *
	 * @param
	 * @param o
	 * @return
	 */
    @RequestMapping(value="secondaryAuditThrough", method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes auditThrough(Merchant o){
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept("3"))) {
            try
            {
                logger.info("彩票请求接口开始：===========");
                Merchant merchant = this.service.findMerchantId(o);
                StringBuilder sb = new StringBuilder(10);
                String url = this.path+"?mobile="+merchant.getmMobile()+"&mName="+ URLEncoder.encode(merchant.getmName(), "utf-8");
                logger.info("彩票请求地址为：==========="+url);
                String s = null;
                try {
                    s = HttpUtil.get(url,false);
                    logger.info("彩票接口请求响应数据为：==========================="+s);
                } catch (Exception e) {
                    ar.setSucceedMsg("彩票接口异常!");
                    logger.error("接口异常信息============================"+e.getMessage().toString());
                    return ar;
                }
                if (s != null) {
                    Map map = JsonUtil.jsonToObject(s);
                    if (map.get("flag").equals("1") || map.get("flag").equals("2")|| map.get("flag").equals("4")) {
                        merchant.setmCpUserId(map.get("userId").toString());
                        merchant.setmBarcode(map.get("picBUrl").toString());
                    }else if(map.get("flag").equals("-1")){
                        ar.setSucceedMsg("参数异常!");
                        return ar;
                    }else if(map.get("flag").equals("3")){
                        ar.setSucceedMsg("该用户已经关联销售，不可创建!");
                        return ar;
                    }else{
                        ar.setSucceedMsg("彩票系统接口异常!");
                        return ar;
                    }
                } else {
                    ar.setSucceedMsg("彩票帐号创建失败,彩票接口异常!");
                    return ar;
                }

                merchant.setmStatus(2);
                Integer up=service.updateUserAccount(merchant);
                if(up==1){
                    ar.setSucceedMsg("审核成功");
                    //为一级渠道预备预存款信息
                    if(merchant.getmLevel()==2){
                        Prepayment pre = new Prepayment();
                        pre.setMerchantId(merchant.getmId());
                        pre.setBalance(0.00);
                        pre.setPayMoney(0.00);
                        pre.setWarningMoney(0.00);
                        if("1".equals(merchant.getLine())){
                            pre.setDataType(1);
                            prepaymentDao.insert(pre);
                        }else if("2".equals(merchant.getLine())){
                            pre.setDataType(2);
                            prepaymentDao.insert(pre);
                        }else{
                            pre.setDataType(1);
                            prepaymentDao.insert(pre);
                            pre.setDataType(2);
                            prepaymentDao.insert(pre);
                        }
                    }
                }else {
                    ar.setSucceedMsg("审核失败");
                    return ar;
                }
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg("审核失败");
            }
        }
        return ar;
    }
}
