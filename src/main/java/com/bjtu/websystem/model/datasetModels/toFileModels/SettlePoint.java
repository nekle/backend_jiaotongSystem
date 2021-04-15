package com.bjtu.websystem.model.datasetModels.toFileModels;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bjtu.websystem.model.datasetModels.Cross;

/**
 * @author Nekkl
 * @version 1.0
 * @description: 居民点
 * @date 2021/4/12 23:58
 */
@TableName("cross_tbl")
public class SettlePoint extends Cross {

	public SettlePoint(Integer id, String name, double longitude, double latitude, Integer type, Integer vehicleSum, Integer vehicleStartTime, Integer vehicleStartInterval, Integer passengerSum, Integer carSum, Integer truckSum, Integer motorSum, Integer capacity) {
		super(id, name, longitude, latitude, type, vehicleSum, vehicleStartTime, vehicleStartInterval, passengerSum, carSum, truckSum, motorSum, capacity);
	}

	@Override
	public String[] toStrings(){

		String[] values = new String[3];
		values[0] = super.getId() + "";
		values[1] = super.getName();
		values[2] = super.getCapacity() + "";

		return values;

	}
 }
