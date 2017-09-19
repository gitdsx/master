package com.jy.repository.system.finance.reconciliation.lottery;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.system.finance.reconciliation.lottery.TicketInfoDetail;
import com.jy.repository.base.JYBatis;


/** 
 * @文件名:TicketInfoDetailDao.java 
 * @功能描述：
 * @创建日期:2017年3月10日下午3:08:22 
 * @创建人：lijunke
 * @Copyright（c） 2017,all rights reserved by days 
 */
@JYBatis
public interface TicketInfoDetailDao {
	/**
	 *
	 * @方法功能描述： 用于统计总条数和总金额
	 * @return
	 * TicketInfoDetailPO
	 * @author lijunke
	 * @创建时间： 2017年3月10日上午9:45:03
	 * @param date
	 */
	TicketInfoDetail queryTotal(@Param("date")String date);


	/**
	 *
	 * @方法功能描述： 查询所有数据
	 * @return
	 * List<TicketInfoDetailPO>
	 * @author lijunke
	 * @创建时间： 2017年3月10日上午10:10:01
	 * @param date
	 */
	List<TicketInfoDetail> findAll(@Param("date")String date);

	/**
	 *
	 * @方法功能描述： 根据批次号返回一个对象
	 * @param batchNo
	 * @return
	 * TicketInfoDetailPO
	 * @author Dingj
	 * @创建时间： 2017年3月10日下午12:43:01
	 * @param date
	 */
	TicketInfoDetail queryByNo(@Param("ticketNo")String ticketNo, @Param("date")String date);

	/**
	 *
	 * @方法功能描述： 根据日期分组，获取每天总条数，总金额
	 * @return
	 * List<TicketInfoDetail>
	 * @author lijunke
	 * @创建时间： 2017年3月13日上午11:35:40
	 * @param date
	 */

	List<TicketInfoDetail> queryByDate(@Param("startTime")Date startTime, @Param("endTime")Date endTime);
}
