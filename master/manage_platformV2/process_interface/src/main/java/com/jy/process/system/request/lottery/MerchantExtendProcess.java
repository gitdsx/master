package com.jy.process.system.request.lottery;

import com.jy.from.system.request.MerchantExtendForm;
import com.jy.from.system.request.RequestParamInfoForm;

import java.util.List;

/**
 * Created by Matthew on 2017/5/22.
 */
public interface MerchantExtendProcess {

    void synchronizeMerEData(RequestParamInfoForm infoForm) throws Exception;

    public Integer transforPojoSave(List<MerchantExtendForm> listAll);


}
