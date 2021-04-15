package com.bjtu.websystem.model.datasetModels.toFileModels;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bjtu.websystem.model.datasetModels.Cross;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/4/12 23:44
 */
@TableName("cross_tbl")
public class CivilPoint extends Cross {

	public CivilPoint(Integer id, String name, double longitude, double latitude, Integer type, Integer carSum, Integer carStartTime, Integer carStartInterval, Integer passengerSum, Integer carPassengerSum, Integer truckSum, Integer motorSum, Integer capacity) {
		super(id, name, longitude, latitude, type, carSum, carStartTime, carStartInterval, passengerSum, carPassengerSum, truckSum, motorSum, capacity);
	}

	@Override
	public String[] toStrings() {

		String[] values = new String[5];

		values[0] = super.getId() + "";
		values[1] = super.getName();
		values[2] = super.getCarSum() + "";
		values[3] = super.getTruckSum() + "";
		values[4] = super.getMotorSum() + "";

		return values;

	}
}
