package com.jy.vo.system.reconciliation.games;

/**
 * Created by Administrator on 2017/4/20.
 */
public class DiamondVo {
    private String date;
    private Integer qcDiamond;//期初结余充值钻石数
    private Double  qcMoney;//期初结余充值金额
    private  Integer addDiamond;//本期充值钻石数
    private Double  addMoney;//本期充值金额
    private Double  averageDiamond;//钻石货币单价（加权平均）
    private Integer  consumeDiamond;//本期充值钻石消耗数
    private Double  addDiamondToMoney;//本期充值钻石结转价值
    private Integer  qmDiamond;//本期结余充值钻石数量
    private Double  qmMoney;//期末结余充值金额
    private Double  qmAverageDiamond;//期末钻石货币单价（加权平均）

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getQcDiamond() {
        return qcDiamond;
    }

    public void setQcDiamond(Integer qcDiamond) {
        this.qcDiamond = qcDiamond;
    }

    public Double getQcMoney() {
        return qcMoney;
    }

    public void setQcMoney(Double qcMoney) {
        this.qcMoney = qcMoney;
    }

    public Integer getAddDiamond() {
        return addDiamond;
    }

    public void setAddDiamond(Integer addDiamond) {
        this.addDiamond = addDiamond;
    }

    public Double getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(Double addMoney) {
        this.addMoney = addMoney;
    }

    public Double getAverageDiamond() {
        return averageDiamond;
    }

    public void setAverageDiamond(Double averageDiamond) {
        this.averageDiamond = averageDiamond;
    }

    public Integer getConsumeDiamond() {
        return consumeDiamond;
    }

    public void setConsumeDiamond(Integer consumeDiamond) {
        this.consumeDiamond = consumeDiamond;
    }

    public Double getAddDiamondToMoney() {
        return addDiamondToMoney;
    }

    public void setAddDiamondToMoney(Double addDiamondToMoney) {
        this.addDiamondToMoney = addDiamondToMoney;
    }

    public Integer getQmDiamond() {
        return qmDiamond;
    }

    public void setQmDiamond(Integer qmDiamond) {
        this.qmDiamond = qmDiamond;
    }

    public Double getQmMoney() {
        return qmMoney;
    }

    public void setQmMoney(Double qmMoney) {
        this.qmMoney = qmMoney;
    }

    public Double getQmAverageDiamond() {
        return qmAverageDiamond;
    }

    public void setQmAverageDiamond(Double qmAverageDiamond) {
        this.qmAverageDiamond = qmAverageDiamond;
    }
}
