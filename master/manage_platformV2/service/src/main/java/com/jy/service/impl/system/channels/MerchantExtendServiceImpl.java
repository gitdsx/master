package com.jy.service.impl.system.channels;


import com.jy.common.utils.DateUtils;
import com.jy.entity.system.channels.vo.MerchantExtend;
import com.jy.from.system.request.MerchantExtendForm;
import com.jy.repository.system.channels.MerchantExtendDao;
import com.jy.service.impl.base.BaseServiceImp;
import com.jy.service.system.channels.MerchantExtendService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Matthew on 2017/5/22.
 */
@Service("merchantExtendService")
public class MerchantExtendServiceImpl extends BaseServiceImp<MerchantExtend> implements MerchantExtendService {

    @Autowired
    private MerchantExtendDao merchantExtendDao;

    @Override
    public Integer transforPojoSave(List<MerchantExtendForm> listAll) {
        List<MerchantExtend> MerchantExtends = new ArrayList<>();
        for (MerchantExtendForm MerchantExtendForm : listAll) {
            MerchantExtend MerchantExtend = new MerchantExtend();
            BeanUtils.copyProperties(MerchantExtendForm, MerchantExtend);
            MerchantExtend.setCreateTime(new Date());
            MerchantExtend.setVaildDate(DateUtils.stringToDate(MerchantExtendForm.getVaildDateStr(),"yyyyMMddhhmmss"));
            MerchantExtends.add(MerchantExtend);
        }
        this.merchantExtendDao.clean();
        return this.merchantExtendDao.save(MerchantExtends);
    }
}
