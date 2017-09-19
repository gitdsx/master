package com.jy.service.system.channels;

import com.jy.entity.system.channels.CpVoucherInfo;
import com.jy.service.base.BaseService;
import com.jy.from.system.request.VoucherInfoForm;

import java.util.List;

/**
 * Created by Matthew on 2017/4/26.
 */
public interface CpVoucherInfoService extends BaseService<CpVoucherInfo> {
    int voucherSave(List<VoucherInfoForm> listAll);
    void voucherClean(String date);
    int save(String filePath, String currentDate);

}
