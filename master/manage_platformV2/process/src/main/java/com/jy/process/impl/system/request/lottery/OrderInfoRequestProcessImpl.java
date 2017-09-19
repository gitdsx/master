package com.jy.process.impl.system.request.lottery;


import com.jy.entity.system.finance.reconciliation.jobTask.JobTaskStatistics;
import com.jy.process.impl.system.request.base.ApiRequestTxtProcessImpl;
import com.jy.process.system.request.lottery.OrderInfoRequestProcess;
import com.jy.service.system.channels.CpOrderInfoService;
import com.jy.service.system.finance.statistics.jobTask.JobTaskStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Matthew on 2017/4/26.
 */
@Service("orderInfoRequestProcess")
public class OrderInfoRequestProcessImpl extends ApiRequestTxtProcessImpl implements OrderInfoRequestProcess {
    @Value("${userOrder.info}")
    private String ftpFileName;//下载文件的名称
    @Value("${new_order.info}")
    private String newOrderName;
    @Autowired
    private CpOrderInfoService cpOrderInfoService;
    @Autowired
    private JobTaskStatisticsService jobTaskStatisticsService;

    private static final Logger LOG = LoggerFactory.getLogger(OrderInfoRequestProcessImpl.class);

    @Override
    public String byJobName() {
        return "用户购彩接口";
    }

    @Override
    public void saveJobLog(JobTaskStatistics jobTaskStatistics) {
        try {
            this.jobTaskStatisticsService.insertJobTaskStatistics(jobTaskStatistics);
        } catch (Exception e) {
            LOG.info("用户购彩接口保存LOG数据失败！");
            e.printStackTrace();
        }
    }

    @Override
    public int saveFTPData(String filePath, String date) {
        int count = 0;
        try {
            count = this.cpOrderInfoService.orderSave(filePath, date);
            LOG.info("入库行数：{}", count);
        } catch (Exception e) {
            LOG.info("用户购彩接口保存数据失败！");
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void synchronizeOrderData(String date) throws Exception {
        super.ftpCommonsFunction(newOrderName, date);
    }
}
