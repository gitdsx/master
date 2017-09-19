package com.jy.entity.system.finance;

import java.math.BigDecimal;


/** 
 * @文件名:LotteryWaySaleReport.java 
 * @功能描述：
 * @创建日期:2017年3月9日上午11:35:45 
 * @创建人：lijunke
 * @Copyright（c） 2017,all rights reserved by days 
 */
public class LotteryWaySaleReport {

    private Integer id;
    private String date;
    private Double 			lottery10023;			//排列3
    private Double 			lottery10024;			//排列5
    private Double 			lottery100234;			//排列3/5
    private Double 			lottery10026;			//超级大乐透
    private Double 			lottery10030;			//七星彩
    private Double 			lottery10039;			//胜负彩
    private Double 			lottery10040;			//任选九
    private Double 			lottery10041;			//6场半全场
    private Double 			lottery10042;			//四场进球彩
    private Double 			lottery10058;			//竞彩篮球
    private Double 			lottery10059;			//竞彩足球
    private Double 			lottery10066;			//11选5
    private Double 			lotteryAll;				//总计


    private String beginTime;
    private String endTime;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getLottery10039() {
        return lottery10039;
    }

    public void setLottery10039(Double lottery10039) {
        this.lottery10039 = lottery10039;
    }

    public Double getLottery10040() {
        return lottery10040;
    }

    public void setLottery10040(Double lottery10040) {
        this.lottery10040 = lottery10040;
    }

    public Double getLottery100234() {
        return lottery100234;
    }

    public void setLottery100234(Double lottery100234) {
        this.lottery100234 = lottery100234;
    }

    public Double getLottery10066() {
        return lottery10066;
    }

    public void setLottery10066(Double lottery10066) {
        this.lottery10066 = lottery10066;
    }

    public Double getLottery10026() {
        return lottery10026;
    }

    public void setLottery10026(Double lottery10026) {
        this.lottery10026 = lottery10026;
    }

    public Double getLottery10058() {
        return lottery10058;
    }

    public void setLottery10058(Double lottery10058) {
        this.lottery10058 = lottery10058;
    }

    public Double getLottery10059() {
        return lottery10059;
    }

    public void setLottery10059(Double lottery10059) {
        this.lottery10059 = lottery10059;
    }

    public Double getLottery10023() {
        return lottery10023;
    }

    public void setLottery10023(Double lottery10023) {
        this.lottery10023 = lottery10023;
    }

    public Double getLottery10024() {
        return lottery10024;
    }

    public void setLottery10024(Double lottery10024) {
        this.lottery10024 = lottery10024;
    }

    public Double getLottery10030() {
        return lottery10030;
    }

    public void setLottery10030(Double lottery10030) {
        this.lottery10030 = lottery10030;
    }

    public Double getLotteryAll() {
        return lotteryAll;
    }

    public void setLotteryAll(Double lotteryAll) {
        this.lotteryAll = lotteryAll;
    }

    public Double getLottery10042() {
        return lottery10042;
    }

    public void setLottery10042(Double lottery10042) {
        this.lottery10042 = lottery10042;
    }

    public Double getLottery10041() {
        return lottery10041;
    }

    public void setLottery10041(Double lottery10041) {
        this.lottery10041 = lottery10041;
    }
}
