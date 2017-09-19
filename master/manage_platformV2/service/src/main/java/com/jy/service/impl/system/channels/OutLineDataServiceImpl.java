package com.jy.service.impl.system.channels;

import com.jy.entity.system.channels.Merchant;
import com.jy.entity.system.channels.Prepayment;
import com.jy.entity.system.channels.PrepaymentExtend;
import com.jy.from.system.request.OutLineDataForm;
import com.jy.repository.system.channels.OutLineDataDao;
import com.jy.service.system.channels.OutLineDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ZQY on 2017/5/17.
 */
@Service
public class OutLineDataServiceImpl implements OutLineDataService {
    @Autowired
    private OutLineDataDao outLineDataDao;

    @Override
    public void addOutLineData(OutLineDataForm outLineDataForm) throws Exception {
        outLineDataDao.addOutLineData(outLineDataForm);
    }

    @Override
    public void addDeposit(Merchant merchant) throws Exception {
        outLineDataDao.addDeposit(merchant);
    }

    @Override
    public void delDeposit(Map map) throws Exception {
        outLineDataDao.delDeposit(map);
    }

    @Override
    public void updateDeposit(Merchant merchant) throws Exception {
        outLineDataDao.updateDeposit(merchant);
    }

    @Override
    public List findOutLineData(Map map) throws Exception {
        return outLineDataDao.findOutLineData(map);
    }
    @Override
    public List findDeposit(Map map) throws Exception {
        return outLineDataDao.findDeposit(map);
    }
    @Override
    public List findOutLineData(Map map, int a) throws Exception {
        return   outLineDataDao.findOutLineDataTotal(map);
    }
    @Override
    public List findDeposit(Map map, int a) throws Exception {
        return   outLineDataDao.findDepositTotal(map);
    }
    @Override
    public void updatePreDepositBalance(Map map) throws Exception {
        outLineDataDao.updatePreDepositBalance(map);
    }

    @Override
    public void updatePreDepositBalanceEj(Map map) throws Exception {
        outLineDataDao.updatePreDepositBalanceEj(map);
    }

    @Override
    public Prepayment findPrePaymentInfo(Map map) throws Exception {
        return outLineDataDao.findPrePaymentInfo(map);
    }
    @Override
    public PrepaymentExtend findPrePaymentExtend(Map map) throws Exception {
        return outLineDataDao.findPrePaymentExtend(map);
    }
    @Override
    public List<Merchant> findMerchant() throws Exception {
        return outLineDataDao.findMerchant();
    }
}
