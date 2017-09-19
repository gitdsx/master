package com.jy.controller.system.channels;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.ChannelsUtils;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.channels.Merchant;
import com.jy.entity.system.channels.OutLineDataInfo;
import com.jy.entity.system.channels.PrepaymentExtend;
import com.jy.entity.system.finance.CpOrderInfo;
import com.jy.entity.system.finance.UserVo;
import com.jy.entity.system.finance.statistics.SalesCommissionReport;
import com.jy.from.system.request.OutLineDataForm;
import com.jy.mybatis.Page;
import com.jy.process.system.channels.OutLineDataProcess;
import com.jy.process.system.channels.SalesVolumeProcess;
import com.jy.service.system.account.AccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Matthew on 2017/1/19 0005.
 * 商户销量展示信息
 */
@Controller
@RequestMapping("/channels/onlinedata/")
public class OutLineDataController extends BaseController implements
        HandlerExceptionResolver {

    @Autowired
   private OutLineDataProcess outLineDataProcess;

    Logger logger = Logger.getLogger(OutLineDataController.class);
    @RequestMapping("findOutLineDataInit")
    public String findOutLineDataInit(Model model){
        if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
            model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
            return "/system/channels/outLineData";
        }
        return Const.NO_AUTHORIZED_URL;
    }

    @RequestMapping(value="checkBalance", method= RequestMethod.POST)
    @ResponseBody
    public AjaxRes checkBalance(OutLineDataForm outLineDataForm, HttpServletResponse response){
        AjaxRes ar = getAjaxRes();
        try {
                String id[] = outLineDataForm.getMerchantName().split(";");
                outLineDataForm.setMerchantName(id[1]);
                outLineDataForm.setMerchantId(Integer.parseInt(id[0]));
            PrepaymentExtend prepaymentExtend =  outLineDataProcess.checkBalance(outLineDataForm);
                if(prepaymentExtend != null){
                    if(prepaymentExtend.getBalance()<outLineDataForm.getSaleMoney()){
                        ar.setSucceedMsg("fail");
                    }else{
                        ar.setSucceedMsg("success");
                    }
                }else{
                    ar.setSucceedMsg("notExist");
                }
        } catch (Exception e) {
            e.printStackTrace();ar.setSucceedMsg("error");
        }
        return ar;
    }
    @RequestMapping(value="findOutLineData", method= RequestMethod.POST)
    @ResponseBody
    public AjaxRes findByPage(Page<OutLineDataForm> page, OutLineDataForm o) {
        AjaxRes ar = getAjaxRes();
        Page<OutLineDataInfo> reports=new Page<OutLineDataInfo>();
        Map map = new HashMap();
        map.put("pageNum",(page.getPageNum()-1)*page.getPageSize());
        map.put("pageSize",page.getPageSize());
        map.put("endTime",o.getEndTime());
        map.put("machineNum",o.getMachineNum());
        List<OutLineDataInfo> listTotal = new ArrayList<OutLineDataInfo>();
        List<OutLineDataInfo> list = new ArrayList<OutLineDataInfo>();
        try {
            listTotal = outLineDataProcess.findOutLineData(map,1);
             list = outLineDataProcess.findOutLineData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int total=listTotal.size();
        int totalPage = total/page.getPageSize();
        if(total % page.getPageSize()>0){
            totalPage++;
        }
        reports.setTotalPage(totalPage);
        reports.setPageNum(page.getPageNum());
        reports.setPageSize(page.getPageSize());
        reports.setTotalRecord(total);
        reports.setResults(list);
        Map<String, Object> p=new HashMap<String, Object>();
        p.put("permitBtn", getPermitBtn("3"));
        p.put("list", reports);
        ar.setSucceed(p);
        return ar;
    }
    @RequestMapping("addOutLineDataInit")
    public String addOutLineDataInit(Model model){
//        if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
            model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
            return "/system/channels/addOutLineDataInit";
//        }
//        return Const.NO_AUTHORIZED_URL;
    }

    @RequestMapping(value = "addOutLineData", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes add(OutLineDataForm outLineDataForm, HttpServletResponse response){
        AjaxRes ar = getAjaxRes();
        try {
            if ((doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))) {
                String id[] = outLineDataForm.getMerchantName().split(";");
                outLineDataForm.setMerchantName(id[1]);
                outLineDataForm.setMerchantId(Integer.parseInt(id[0]));

                outLineDataProcess.addOutLineData(outLineDataForm);
                ar.setSucceedMsg("success");
            }
        } catch (Exception e) {
            e.printStackTrace();ar.setSucceedMsg("fail");
        }
        return ar;
    }
    @RequestMapping(value = "findMerchant", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes findMerchant(){
        AjaxRes ar = getAjaxRes();
        Page<Merchant> reports=new Page<Merchant>();

        List<Merchant> list = new ArrayList<Merchant>();
        try {
            list = outLineDataProcess.findMerchant();
        } catch (Exception e) {
            e.printStackTrace();
        }
        reports.setResults(list);
        Map<String, Object> p=new HashMap<String, Object>();
        p.put("list", reports);
        p.put("size", list.size());
        ar.setSucceed(p);
        return ar;
    }
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        return null;
    }

}
