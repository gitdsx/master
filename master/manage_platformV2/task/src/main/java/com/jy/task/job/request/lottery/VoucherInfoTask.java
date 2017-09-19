package com.jy.task.job.request.lottery;


import com.jy.common.utils.DateUtils;
import com.jy.common.utils.SpringWebContextUtil;
import com.jy.process.system.request.lottery.VoucherInfoRequestProcess;
import com.jy.task.job.request.base.BaseTask;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * 用户兑换券数据入库
 */
public class VoucherInfoTask extends BaseTask {
    
    public void saveData() {
		log.info(DateUtils.getDate("yyyy-MM-dd HH:mm:ss") + " ——兑换券任务开始运行");
        ApplicationContext ac = SpringWebContextUtil.getApplicationContext();
        VoucherInfoRequestProcess process = (VoucherInfoRequestProcess) ac.getBean("voucherInfoRequestProcess");
        log.info(DateUtils.getDate("yyyy-MM-dd HH:mm:ss") + " 同步兑换券数据开始运行");
        try {
            String queryTime = super.getParam()[2];

            if (!"default".equals(queryTime)) {
                if (queryTime.contains("-")) {
                    String[] byDate = queryTime.split("-");
                    List<String> strings = DateUtils.findDatesFormat(byDate[0], byDate[1]);
                    for (String string : strings) {
                        process.synchronizeVoucherData(string);
                    }
                } else {
                    process.synchronizeVoucherData(queryTime);
                }
            } else {
                queryTime = DateUtils.getDate("yyyyMMdd");
                process.synchronizeVoucherData(queryTime);
            }
        } catch (Exception e) {
            log.error("同步兑换券信息JOB失败！", e);
            e.printStackTrace();
        }
        log.info(DateUtils.getDate("yyyy-MM-dd HH:mm:ss") + " 同步兑换券信息结束");
    }
}
