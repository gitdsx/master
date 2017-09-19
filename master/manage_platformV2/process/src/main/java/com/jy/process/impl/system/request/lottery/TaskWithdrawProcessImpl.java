package com.jy.process.impl.system.request.lottery;


import com.jy.entity.system.finance.reconciliation.jobTask.JobTaskStatistics;
import com.jy.process.impl.system.request.base.ApiRequestTxtProcessImpl;
import com.jy.process.system.request.lottery.TaskWithdrawProcess;
import com.jy.service.system.finance.reconciliation.lottery.TaskWithdrawService;
import com.jy.service.system.finance.statistics.jobTask.JobTaskStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by ZQY on 2017/4/27.
 */
@Service("taskWithdrawProcess")
public class TaskWithdrawProcessImpl extends ApiRequestTxtProcessImpl implements TaskWithdrawProcess {

    @Value("${user.withdraw}")
    private String ftpFileName;//下载文件的名称
    @Autowired
    private TaskWithdrawService taskWithdrawService;
    @Autowired
    private JobTaskStatisticsService jobTaskStatisticsService;

    private static final Logger LOG = LoggerFactory.getLogger(TaskWithdrawProcessImpl.class);

    @Override
    public String byJobName() {
        return "用户提现接口";
    }

    @Override
    public void saveJobLog(JobTaskStatistics jobTaskStatistics) {
        try {
            this.jobTaskStatisticsService.insertJobTaskStatistics(jobTaskStatistics);
        } catch (Exception e) {
            LOG.info("用户提现接口保存LOG数据失败！");
            e.printStackTrace();
        }
    }


    @Override
    public int saveFTPData(String filePath, String date) {
        int count = 0;
        try {
            count = this.taskWithdrawService.saveTaskWithdrawInfoList(filePath, date);
            LOG.info("入库行数：{}", count);
        } catch (Exception e) {
            LOG.info("用户提现接口保存数据失败！");
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void saveTaskWithdrawInfoList(String date) throws Exception {
        super.ftpCommonsFunction(ftpFileName, date);
    }

}
