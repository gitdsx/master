package com.jy.process.impl.system.request.lottery;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jy.common.utils.DateUtils;
import com.jy.entity.system.finance.reconciliation.jobTask.JobTaskStatistics;
import com.jy.from.system.request.MerchantExtendForm;
import com.jy.from.system.request.RequestPageInfoForm;
import com.jy.from.system.request.RequestParamInfoForm;
import com.jy.process.impl.system.request.base.ApiRequestProcessImpl;
import com.jy.process.system.request.lottery.MerchantExtendProcess;
import com.jy.service.system.channels.MerchantExtendService;
import com.jy.service.system.finance.statistics.jobTask.JobTaskStatisticsService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Matthew on 2017/5/22.
 */
@Service("MerchantExtendProcess")
public class MerchantExtendProcessImpl extends ApiRequestProcessImpl<MerchantExtendForm> implements MerchantExtendProcess {


    @Autowired
    protected MerchantExtendService merchantExtendService;

    @Autowired
    private JobTaskStatisticsService jobTaskStatisticsService;

    @Value("${merExtend.url}")
    private  String path;

    @Override
    public void synchronizeMerEData(RequestParamInfoForm infoForm) throws Exception {
        String JsonArray = super.sendRequestTranJson(infoForm, this.path);
        RequestPageInfoForm<T> requestPageInfoForm = JSONObject.parseObject(JsonArray, RequestPageInfoForm.class);

        List<MerchantExtendForm> list = JSONArray.parseArray(requestPageInfoForm.getList().toString(), MerchantExtendForm.class);
        if (list.size() < 0) {
            super.LOGGER.info("商户扩展接口未返回任何数据！");
            return;
        }
        Integer count =this.transforPojoSave(list);//分批保存

        JobTaskStatistics jobTaskStatistics = new JobTaskStatistics();
        jobTaskStatistics.setJobName("商户扩展接口");
        jobTaskStatistics.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        jobTaskStatistics.setDescription("调用接口成功");
        jobTaskStatistics.setStorageNumber(count);
        jobTaskStatistics.setInterfaceNumber(list.size());
        jobTaskStatisticsService.insertJobTaskStatistics(jobTaskStatistics);

    }

    public Integer transforPojoSave(List<MerchantExtendForm> listAll) {
        return this.merchantExtendService.transforPojoSave(listAll);
    }

}