package com.jy.from.system.request;

/**
 * Created by ZQY on 2017/5/17.
 */
public class OutLineDataForm {
    private Integer id; //
    private Integer merchantId;//商户id
    private String endTime;
    private String machineNum;//机器号
    private Double saleMoney;
    private Double winningMoney;
    private Double offsetVoucherMoney;
    private Double returnVoucherMoney;
    private Double oldPreDeposit;
    private Double nowPreDeposit;
    private String merchantName;
    private String merchantPhone;
    private String merchantLevel;

    public String getMerchantLevel() {
        return merchantLevel;
    }

    public void setMerchantLevel(String merchantLevel) {
        this.merchantLevel = merchantLevel;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMachineNum() {
        return machineNum;
    }

    public void setMachineNum(String machineNum) {
        this.machineNum = machineNum;
    }

    public Double getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(Double saleMoney) {
        this.saleMoney = saleMoney;
    }

    public Double getWinningMoney() {
        return winningMoney;
    }

    public void setWinningMoney(Double winningMoney) {
        this.winningMoney = winningMoney;
    }

    public Double getOffsetVoucherMoney() {
        return offsetVoucherMoney;
    }

    public void setOffsetVoucherMoney(Double offsetVoucherMoney) {
        this.offsetVoucherMoney = offsetVoucherMoney;
    }

    public Double getReturnVoucherMoney() {
        return returnVoucherMoney;
    }

    public void setReturnVoucherMoney(Double returnVoucherMoney) {
        this.returnVoucherMoney = returnVoucherMoney;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Double getOldPreDeposit() {
        return oldPreDeposit;
    }

    public void setOldPreDeposit(Double oldPreDeposit) {
        this.oldPreDeposit = oldPreDeposit;
    }

    public Double getNowPreDeposit() {
        return nowPreDeposit;
    }

    public void setNowPreDeposit(Double nowPreDeposit) {
        this.nowPreDeposit = nowPreDeposit;
    }
}
