package com.jy.service.system.channels;

import com.jy.entity.system.channels.Payable;
import com.jy.mybatis.Page;
import com.jy.service.base.BaseService;

import java.util.Map;

/**
 * 渠道销量
 */
public interface PayableService extends BaseService<Payable> {

    public Page<Payable> findByPage(Map map, Page page) throws Exception;

    /**
     * 统计
     *
     */
    public Integer counts(Map map);


    /**
     * 查找
     *
     */
    public Payable findById(String id);

    /**
     * 新增
     *
     * @param salesVolume
     */
//    public void save(SalesVolume salesVolume) {
//        this.createObject(salesVolume);
//    }

    /**
     * 修改
     *
     */
//    public void update(SalesVolume salesVolume) {
//        this.updateObject(salesVolume);
//    }


    /**
     * 修改
     *
     * @param
     */
//    public void update(SalesVolume salesVolume) {
//        this.updateObject(salesVolume);
//    }

//    @Override
//    public void deleteBatch(List<SalesVolume> os) {
//
//    }
//
//    @Override
//    public List<SalesVolume> find(SalesVolume o) {
//        return null;
//    }

//    @Override
//    public List<SalesVolume> findByPage(@Param("param") SalesVolume o, Page<SalesVolume> page) {
//        return null;
//    }
//
//    @Override
//    public int count(SalesVolume o) {
//        return 0;
//    }
//
//    @Override
//    public void insert(SalesVolume o) {
//
//    }

    /**
     * 删除
     *
     * @param salesVolume
     */
//    public void delete(SalesVolume salesVolume) {
//        getHibernateTemplate().delete(salesVolume);
//    }

    /**
     * 根据抢包记录的ID 查找记录
     *
     * @param id
     * @return
     */
//    public SalesVolume findById(Integer id) {
//        return (SalesVolume) findObjectById(id);
//    }


    /**
     * 根据实体类的属性 查找记录
     *
     * @param propertyName 实体类的属性
     * @param value        实体类属性对应的值
     * @return
     */
//    public List findByProperty(String propertyName, Object value) {
//        return getByProperty(propertyName, value);
//    }

    /**
     * 新增或者修改记录
     *
     * @param salesVolume
     */
//    public void saveOrUpdate(SalesVolume salesVolume) {
//        getHibernateTemplate().saveOrUpdate(salesVolume);
//    }

//    public List<SalesVolume> getListBySellId(SalesChannel salesChannel, int firstRow, int fetchSize) {
//        StringBuffer sb = new StringBuffer("from SalesVolume where 1=1");
//        sb.append(" and sellerId=?");
//        List<Object> parameters = new ArrayList<>();
//        parameters.add(salesChannel.getSellerId());
//        sb.append(" order by createTime desc");
//        return getListForPage(sb.toString(), parameters, firstRow, fetchSize);
//    }

    /**
     * 根据渠道表的ID查询该渠道的销量对象
     *
     * @param channelId
     * @param type
     * @return
     */
//    public List<SalesVolume> findSalesVolumeByChannelId(Integer channelId, int type) {
//        String hql = " from SalesVolume where type=? and channelId=? ";
//        List<Object> params = new ArrayList<>();
//        params.add(type);
//        params.add(channelId);
//        return (List<SalesVolume>) getList(hql, params);
//    }

    /**
     * 查询某销售下所有渠道的销量对象
     *
     * @param sellerId
     * @return
     */
//    public List<SalesVolume> findSalesVolumeBySellerId(Integer sellerId) {
//        StringBuffer hql = new StringBuffer(" from SalesVolume ");
//        List<Object> params = new ArrayList<>();
//        if (sellerId != 0) {
//            hql.append(" where sellerId= ? ");
//            params.add(sellerId);
//        }
//        return (List<SalesVolume>) getList(hql.toString(), params);
//    }

    /**
     * 根据渠道手机号码查询该渠道的销量对象
     *
     * @param phone
     * @return
     */
//    public SalesVolume findSalesVolumeByPhone(String phone) {
//        StringBuffer hql = new StringBuffer(" from SalesVolume ");
//        List<Object> params = new ArrayList<>();
//        if (phone != null && !phone.equals("")) {
//            hql.append(" where phone= ? ");
//            params.add(phone);
//        }
//        List<SalesVolume> salesVolumes = getList(hql.toString(), params);
//        if (salesVolumes == null || salesVolumes.isEmpty() || salesVolumes.size() == 0) {
//            return new SalesVolume();
//        }
//        return salesVolumes.get(0);
//    }
}
