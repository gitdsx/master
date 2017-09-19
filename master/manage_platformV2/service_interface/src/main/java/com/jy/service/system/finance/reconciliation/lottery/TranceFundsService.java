package com.jy.service.system.finance.reconciliation.lottery;

import com.jy.entity.system.finance.reconciliation.lottery.TranceFunds;
import com.jy.service.base.BaseService;

/**
 * Created by Administrator on 2017/4/21.
 */
public interface TranceFundsService extends BaseService<TranceFunds> {
    void generateReport( String month);

    void execTask(String thisDate);
}
