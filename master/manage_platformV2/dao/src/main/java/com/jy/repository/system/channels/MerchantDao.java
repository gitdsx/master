package com.jy.repository.system.channels;


import java.util.List;
import java.util.Map;

import com.jy.mybatis.Page;
import org.apache.ibatis.annotations.Param;


import com.jy.entity.system.channels.Merchant;
import com.jy.repository.base.BaseDao;
import com.jy.repository.base.JYBatis;

/**
 * 渠道
 */
@JYBatis
public interface MerchantDao extends BaseDao<Merchant> {


  public  List<Merchant> findValidMerchantList();

  public  List<Merchant> findMerchantListPre();
  public  List<Merchant> findMerchantByPreId(Map map);
  public  List<Merchant> findMerchantByPreIdEj(Map map);
  public  List<Merchant> findMerchantByPreIdorg(Map map);
  public  List<Merchant> findChildMerchantId(Map map);

  public List<Merchant> findByParentMerchantId(Integer parentMerchantId);

  	/**
	 * 查询自己的渠道ID
	 * 
	 * @param userId
	 * @return
	 */
  public Merchant findId(@Param("userId") String userId,@Param("mobile") String mobile );

  public List<Merchant> findChildCustomerByMId(Merchant merchant);

  public List<Merchant> findChildMerchantByMId(Merchant merchant);

	/**
	 * 获得对象列表
	 * 
	 * @param o 集合对象
	 * @return List
	 */
  public List<Merchant> findByPageId(Map o);
  
  public List<Merchant> findLabelMerchant(Map o);
  
  	/**
	 * 统计个人和企业数
	 * 
	 * @param merchant
	 * @return
	 */
  public Merchant findPersonAndEnterprise(Merchant merchant);
  public Merchant countPersonAndEnterprise(Map map);

  public List<Merchant> findByPage(@Param("param") Merchant merchant);

  	/**
	 * 查询商户名数量
	 * 
	 * @param mName 商户名
	 * @return
	 */
  public Integer findmName(@Param("mName") String mName);

  public Integer findmMobile(@Param("mMobile") String mMobile,@Param("mId") String mId);
  	/**
	 * 新增商户
	 * 
	 * @param merchant
	 * @return
	 */
  public Integer createMerchant(Merchant merchant);

  	/**
	 * 查询自己渠道的次级渠道
	 * 
	 * @param merchant
	 * @return
	 */
  public List<Merchant> findChannel(Merchant merchant);

  	/**
	 * 查询自己渠道的次级渠道数
	 * 
	 * @param merchant
	 * @return
	 */
  public Integer findCount(Merchant merchant);


  List<Merchant> findByPageAndParam(@Param("param") Merchant o, Page<Merchant> page);

  	/**
	 * 修改商户
	 * 
	 * @param merchant
	 * @return
	 */
  public Integer updateMerchant(Merchant merchant);
  public Integer updateMLicense(Merchant merchant);
  public Merchant findMerchant(String accountId);
  public Merchant findMerchantPO(String accountId);
  public String findChildMerchant(@Param("mId") Integer mId);
  public String findChildCustomer(@Param("mId") Integer mId);
  public String findRoleName(@Param("mId") Integer mId);
  public Merchant findMerchantById(Integer merchantId);

  public List<Merchant> findMerchantByIdAndName(@Param("merchantId") Integer merchantId, @Param("merchantName") String merchantName);

	public List<Merchant> findBaseCommission(Merchant merchantId);
	
  	/**
	 * 通过渠道ID批量查询渠道
	 * 
	 * @param id
	 * @return
	 */
  public List<Merchant> findMerchantId(List id);

  	/**
	 * 批量删除
	 * 
	 * @param id
	 * @return
	 */
  public Integer deleteMerchant(List id);

  public Integer deleteMerchantByMId(@Param("mId") String mId);


  
  
  //查询所有数据
  public List<Merchant> queryMerchantAll();

  //根据时期和pid查询 渠道的期初客户数
  public List<Merchant> queryMerchantByIdAndDate(@Param("createdate") String createdate);

  //根据创建时间查找对应的商户
  public Merchant queryMerchantByDate(@Param("createdate") String createdate);
  
  //根据时期和pid查询 渠道的新增客户数
  public List<Merchant> queryMerchantByIdAndDatenow(@Param("createdate") String createdate);

  public List<Merchant> findDataAuthorityMerchant(Map o);

  public List<Merchant> findDataAuthoritySalesManager(Map o);

  public List<Merchant> findParentMerchant(Map o);

  public List<Merchant> findOwn(Map o);

  public List<Merchant> findUserLevel(Map o);

//  /**
	// * 查询归属自己的商户
	// * @param userId 平台用户ID
//   * @return
//   */
//  public List<MerchantVO> subordinateMerchant(@Param("userId") String userId);




  	/**
	 * 新增
	 *
	 * @param salesChannel
	 */
//    public void save(SalesChannel salesChannel) {
//        this.createObject(salesChannel);
//    }

    /**
	 * 修改
	 *
	 * @param salesChannel
	 */
//    public void update(SalesChannel salesChannel) {
//        this.updateObject(salesChannel);
//    }


    /**
	 * 删除
	 *
	 * @param salesChannel
	 */
//    public void delete(SalesChannel salesChannel) {
//        getHibernateTemplate().delete(salesChannel);
//    }

    /**
	 * 根据抢包记录的ID 查找记录
	 *
	 * @param id
	 * @return
	 */
//    public SalesChannel findById(Integer id) {
//        return (SalesChannel) findObjectById(id);
//    }


    /**
	 * 根据实体类的属性 查找记录
	 *
	 * @param propertyName 实体类的属性
	 * @param value 实体类属性对应的值
	 * @return
	 */
//    public List findByProperty(String propertyName, Object value) {
//        return getByProperty(propertyName, value);
//    }

    /**
	 * 新增或者修改
	 *
	 * @param salesChannel
	 */
//    public void saveOrUpdate(SalesChannel salesChannel) {
//        getHibernateTemplate().saveOrUpdate(salesChannel);
//    }

    /**
	 * 根据手机号码进行查找有效渠道
	 *
	 * @param phone
	 */
//    public SalesChannel findSalesChannelByPhone(String phone) {
//        String hql = " from SalesChannel where channelPhone=? and valid= 1 ";
//        List<Object> args = new ArrayList<Object>();
//        args.add(phone);
//        return (SalesChannel) getFirstObject(hql, args);
//    }

    /**
	 * admin 后台初始化渠道管理首页数据加载
	 *
	 * @param userId
	 * @param type
	 * @param channelName
	 * @param phone
	 * @param valid
	 * @param status
	 * @return
	 * @throws Exception
	 */
//    public List<SalesChannel> findChannelInfo(Integer userId, int type, String channelName, String phone, int valid, int status) throws Exception {
//        List<Object> args = new ArrayList<Object>();
//        StringBuffer hql = new StringBuffer(" from SalesChannel where 1=1 ");
	// if (userId != 0) {//不是系统管理用户
//            hql.append(" and sellerId=? ");
//            args.add(userId);
//        }
//        if (type != -1) {
//            hql.append("and type= ? ");
//            args.add(type);
//        }
//        if (channelName != null && !channelName.equals("")) {
//            hql.append("and channelName like ? ");
//            args.add("%" + channelName.trim() + "%");
//        }
//        if (phone != null && !phone.equals("")) {
//            hql.append("and channelPhone = ? ");
//            args.add(phone.trim());
//        }
//        if (valid != -1) {
//            hql.append("and valid = ? ");
//            args.add(valid);
//        }
//        if (status != -1) {
//            hql.append("and status  = ? ");
//            args.add(status);
//        }
//        return getList(hql.toString(), args);
//    }

    /**
	 * admin 后台初始化渠道管理首页数据加载
	 *
	 * @param userId
	 * @param type
	 * @param channelName
	 * @param phone
	 * @param valid
	 * @param status
	 * @return
	 * @throws Exception
	 */
//    public List<SalesChannel> findChannelInfo(Integer userId, int type, String channelName, String phone, String valid, int status) throws Exception {
//        List<Object> args = new ArrayList<Object>();
//        StringBuffer hql = new StringBuffer(" from SalesChannel where 1=1 ");
//        hql.append(" and sellerId=? ");
//        args.add(userId);
//        if (type != -1) {
//            hql.append("and type= ? ");
//            args.add(type);
//        }
//        if (!(channelName + "").equals("")) {
//            hql.append("and channelName like ? ");
//            args.add("%" + channelName + "%");
//        }
//        if (!(phone + "").equals("")) {
//            hql.append("and channelPhone = ? ");
//            args.add(phone);
//        }
//        if (!valid.equals("-1")) {
//            hql.append("and valid = ? ");
//            args.add(valid);
//        }
//        if (status != -1) {
//            hql.append("and status  = ? ");
//            args.add(status);
//        }
//        return getList(hql.toString(), args);
//    }

    /**
	 * 删除渠道
	 *
	 * @param channelId
	 */
//    public void deletSalesChannel(Integer channelId) throws Exception {
//        StringBuffer hql = new StringBuffer(" update SalesChannel set valid=0 where id = ? ");
//        List<Object> args = new ArrayList<Object>();
//        args.add(channelId);
//        this.executeUpdate(hql.toString(), args);
//    }
//
//
//    /**
	// * 渠道审核查询
//     *
//     * @param type
//     * @param status
//     * @param name
//     * @param phone
//     * @param flg
//     * @return
//     */
//    public List<SalesChannel> findSalesChannels_audit(int type, int status, String name, String phone, int flg) {
//        List<Object> args = new ArrayList<Object>();
//        StringBuffer hql = new StringBuffer(" from SalesChannel where valid=1 ");
//        if (type != -1) {
//            hql.append("and type= ? ");
//            args.add(type);
//        }
//        if (status != -1) {
//            hql.append("and status  = ? ");
//            args.add(status);
//        } else {
//            hql.append(flg == 1 ? "and ( status=0 or status=1 or status=2 ) " : "and ( status=1 or status=3 or status=4 ) ");
//        }
//        if (name != null && !name.equals("")) {
//            hql.append("and channelName like ? ");
//            args.add("%" + name.trim() + "%");
//        }
//        if (phone != null && !phone.equals("")) {
//            hql.append("and channelPhone = ? ");
//            args.add(phone.trim());
//        }
//
//        return getList(hql.toString(), args);
//    }
//
//    /**
	// * 查询所有的销售渠道
//     */
//    public List<SalesChannel> findAllChannelInfo() {
//        StringBuffer hql = new StringBuffer(" from SalesChannel where valid=1 ");
//        List<Object> args = new ArrayList<Object>();
//        return getList(hql.toString(), args);
//    }
}
