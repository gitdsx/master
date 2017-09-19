package com.jy.entity.system.channels;

import java.util.Date;
import java.util.List;

/**
 * 渠道商户表 Created by Administrator on 2017/1/6.
 */
public class Merchant {
	
	private Integer bcId;
	
	private Integer bcIdLine;
	
	private String bcType;

    private String isValid;

    private Integer mId;
	
	private String mCpUserId; // 彩票系统账户ID
	
	private String mName; // 商户名称
	
	private String mParentMerchant; // 上级渠道ID
	
	private Integer mType; // 商户类型1=个人，2=企业
	
	private Integer mLevel; // 商户等级1=1级代理，2=2级代理；3=3级代理，以此类推
	
	private Integer mStatus; // 0=未审核;1=一审;2=有效;3=拒绝,4,待定
	
	private String mBarcode; // 商户二维码
	
	private String mMobile; // 商户手机号
	
	private String mAddress; // 商户地址
	
	private String mContactUser; // 联系人
	
	private String mContactMobile; // 联系人手机号
	
	private String mLicense; // 三证
	
	private String mIdCard; // 身份证
	
	private String mIntroduce; // 商户简介
	
	private String mAccountId; // 平台用户绑定ID
	
	private String mInfo2; // 预留商户信息
	
	private String mInfo3; // 预留商户信息
	
	private String mCreateTime; // 营销时间
	
	private String userId; // 平台用户ID绑定ID 勿删
	
	private Integer bcFlag; // 佣金配置
	// 预收款设置
    private Integer preFlag;
	
	private Integer judge; // 判断是否查询一级渠道
	
	private Integer depId; // 是否是自身部门ID
    private List<String> idList;
	
	private Integer findCount; // 统计次级渠道数
	
	private Integer person; // 统计个人数
	
	private Integer enterprise; // 统计企业数
	
	private String findDepId; // 部门ID
	
	private Integer developCustomerNumber; // 发展的客户T+1天的数量
	
	private Integer developCustomerToday; // 发展的客户T+2天的数量
    
	private String mCommionType;
	private String commission;
	private String recMoney;
	private String purMoney;
	private String balMoney;
    private String childCustomerNum;
    private String childMerchantNum;
    private String mParentName;
    private String line;
    private Date buyTime;
    private Double deposit;//押金
    private String depositTime;
    private String roleId;
    private String orgId;
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(String depositTime) {
        this.depositTime = depositTime;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public String getChildCustomerNum() {
        return childCustomerNum;
    }

    public void setChildCustomerNum(String childCustomerNum) {
        this.childCustomerNum = childCustomerNum;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getmParentName() {
        return mParentName;
    }

    public void setmParentName(String mParentName) {
        this.mParentName = mParentName;
    }

    public String getChildMerchantNum() {
        return childMerchantNum;
    }

    public void setChildMerchantNum(String childMerchantNum) {
        this.childMerchantNum = childMerchantNum;
    }

    public Integer getBcIdLine() {
		return bcIdLine;
	}

	public void setBcIdLine(Integer bcIdLine) {
		this.bcIdLine = bcIdLine;
	}

	public String getBcType() {
		return bcType;
	}

	public void setBcType(String bcType) {
		this.bcType = bcType;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getRecMoney() {
		return recMoney;
	}

	public void setRecMoney(String recMoney) {
		this.recMoney = recMoney;
	}

	public String getPurMoney() {
		return purMoney;
	}

	public void setPurMoney(String purMoney) {
		this.purMoney = purMoney;
	}

	public String getBalMoney() {
		return balMoney;
	}

	public void setBalMoney(String balMoney) {
		this.balMoney = balMoney;
	}

	// 查询参数
    private String beginTime;//
    private String endTime;//


    
	public String getmCommionType() {
		return mCommionType;
	}
	
	public void setmCommionType(String mCommionType) {
		this.mCommionType = mCommionType;
	}
	
	public Integer getBcId() {
		return bcId;
	}
	
	public void setBcId(Integer bcId) {
		this.bcId = bcId;
	}
	
	public Integer getDevelopCustomerNumber() {
    	return developCustomerNumber;
    }

	
    public void setDevelopCustomerNumber(Integer developCustomerNumber) {
    	this.developCustomerNumber = developCustomerNumber;
    }

	
    public Integer getDevelopCustomerToday() {
    	return developCustomerToday;
    }

	
    public void setDevelopCustomerToday(Integer developCustomerToday) {
    	this.developCustomerToday = developCustomerToday;
    }

	
    public String getBeginTime() {
    	return beginTime;
    }

	
    public void setBeginTime(String beginTime) {
    	this.beginTime = beginTime;
    }

	
    public String getEndTime() {
    	return endTime;
    }

	
    public void setEndTime(String endTime) {
    	this.endTime = endTime;
    }

	public String getFindDepId() {
        return findDepId;
    }

    public void setFindDepId(String findDepId) {
        this.findDepId = findDepId;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public Integer getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Integer enterprise) {
        this.enterprise = enterprise;
    }

    public Integer getFindCount() {
        return findCount;
    }

    public void setFindCount(Integer findCount) {
        this.findCount = findCount;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public Integer getJudge() {
        return judge;
    }

    public void setJudge(Integer judge) {
        this.judge = judge;
    }

    public Integer getBcFlag() {
        return bcFlag;
    }

    public void setBcFlag(Integer bcFlag) {
        this.bcFlag = bcFlag;
    }

    public Integer getPreFlag() {
        return preFlag;
    }

    public void setPreFlag(Integer preFlag) {
        this.preFlag = preFlag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getmCpUserId() {
        return mCpUserId;
    }

    public void setmCpUserId(String mCpUserId) {
        this.mCpUserId = mCpUserId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getMParentMerchant() {
        return mParentMerchant;
    }

    public void setMParentMerchant(String mParentMerchant) {
        this.mParentMerchant = mParentMerchant;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public Integer getmLevel() {
        return mLevel;
    }

    public void setmLevel(Integer mLevel) {
        this.mLevel = mLevel;
    }

    public Integer getmStatus() {
        return mStatus;
    }

    public void setmStatus(Integer mStatus) {
        this.mStatus = mStatus;
    }

    public String getmBarcode() {
        return mBarcode;
    }

    public void setmBarcode(String mBarcode) {
        this.mBarcode = mBarcode;
    }

    public String getmMobile() {
        return mMobile;
    }

    public void setmMobile(String mMobile) {
        this.mMobile = mMobile;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmContactUser() {
        return mContactUser;
    }

    public void setmContactUser(String mContactUser) {
        this.mContactUser = mContactUser;
    }

    public String getmContactMobile() {
        return mContactMobile;
    }

    public void setmContactMobile(String mContactMobile) {
        this.mContactMobile = mContactMobile;
    }

    public String getmLicense() {
        return mLicense;
    }

    public void setmLicense(String mLicense) {
        this.mLicense = mLicense;
    }

    public String getmIdCard() {
        return mIdCard;
    }

    public void setmIdCard(String mIdCard) {
        this.mIdCard = mIdCard;
    }

    public String getmIntroduce() {
        return mIntroduce;
    }

    public void setmIntroduce(String mIntroduce) {
        this.mIntroduce = mIntroduce;
    }

    public String getmAccountId() {
        return mAccountId;
    }

    public void setmAccountId(String mAccountId) {
        this.mAccountId = mAccountId;
    }

    public String getmInfo2() {
        return mInfo2;
    }

    public void setmInfo2(String mInfo2) {
        this.mInfo2 = mInfo2;
    }

    public String getmInfo3() {
        return mInfo3;
    }

    public void setmInfo3(String mInfo3) {
        this.mInfo3 = mInfo3;
    }

    public String getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(String mCreateTime) {
        this.mCreateTime = mCreateTime;
    }
}
