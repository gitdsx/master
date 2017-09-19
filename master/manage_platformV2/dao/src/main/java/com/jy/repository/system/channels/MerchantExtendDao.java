package com.jy.repository.system.channels;

import com.jy.entity.system.channels.vo.MerchantExtend;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;
import java.util.List;


@JYBatis
public interface MerchantExtendDao extends BaseDao<MerchantExtend> {

	/**
	 * 保存所有记录
	 * @param MerchantExtends
     */
	Integer save(List<MerchantExtend> MerchantExtends);

	void clean();
}
