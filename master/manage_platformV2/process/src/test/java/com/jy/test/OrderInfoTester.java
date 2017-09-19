package com.jy.test;

import com.jy.from.system.request.RequestParamInfoForm;
import com.jy.process.system.request.lottery.OrderInfoRequestProcess;
import com.jy.process.system.request.lottery.TraceRequestProcess;
import com.jy.process.system.request.lottery.VoucherInfoRequestProcess;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by matthew on 2017/4/26.
 */
public class OrderInfoTester extends BaseJunit4Test {

    @Autowired
    public OrderInfoRequestProcess orderInfo;
    @Autowired
    public TraceRequestProcess trancProcess;

    @Autowired
    public VoucherInfoRequestProcess voucherInfo;



    @Test
    public void order() {
        RequestParamInfoForm req = new RequestParamInfoForm();
//        Integer a = 20170401;
//        for(int i=0;i<30;i++){
//            req.setPage("true");
//            req.setQueryAll("false");
//            req.setCurrentPage(1);
//            req.setQueryTime(a.toString());
//            req.setPageSize(5000);
//            try {
//                orderInfo.synchronizeOrderData(req);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            a++;
//        }
            req.setPage("true");
            req.setQueryAll("true");
            req.setCurrentPage(1);
            req.setQueryTime("20170421");
            req.setPageSize(1);
            try {
                //orderInfo.synchronizeOrderData(req,1);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    @Test
    public void trace() {
        RequestParamInfoForm req = new RequestParamInfoForm();

        req.setPage("true");
        req.setQueryAll("false");
        req.setCurrentPage(1);
        req.setQueryTime("20170524");
        req.setPageSize(3000);
        try {
            for (int i=1;i<5;i++){
                req.setType(i);
                trancProcess.synchronizeTraceData(req.getQueryTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void voucher() {
        RequestParamInfoForm req = new RequestParamInfoForm();

//        Integer a = 20170401;
//        for(int i=0;i<30;i++){
//            req.setPage("true");
//            req.setQueryAll("false");
//            req.setCurrentPage(1);
//            req.setQueryTime(a.toString());
//            req.setPageSize(2000);
//            try {
//                voucherInfo.synchronizeVoucherData(req);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            a++;
//        }
        req.setPage("true");
        req.setQueryAll("true");
        req.setCurrentPage(1);
        req.setQueryTime("20170418");
        req.setPageSize(3000);
        try {
           // voucherInfo.synchronizeVoucherData(req,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
