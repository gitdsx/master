package com.jy.entity.system.channels;

/**
 * ÿ�ն�����ϸ
 * Created by ZQY on 2017/4/11.
 */
public class OrderDetail {

    private Integer id;
    private Integer userId;
    private String phone;
    private String userName;
    private Integer orderNum;
    private Double money;
    private String buyTime;
    private Integer preMerchantId;
    private String ticketTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public Integer getPreMerchantId() {
        return preMerchantId;
    }

    public void setPreMerchantId(Integer preMerchantId) {
        this.preMerchantId = preMerchantId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }
}
