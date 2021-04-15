package com.bjtu.websystem.model.datasetModels.toFileModels;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bjtu.websystem.model.datasetModels.Cross;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/4/12 23:23
 */
@TableName("cross_tbl")
public class BusPoint extends Cross {

	public BusPoint(Integer id, String name, double longitude, double latitude, Integer type, Integer car_sum, Integer car_start_time, Integer car_start_interval, Integer passenger_sum, Integer car_passenger_sum, Integer truck_sum, Integer motor_sum, Integer capacity) {
		super(id, name, longitude, latitude, type, car_sum, car_start_time, car_start_interval, passenger_sum, car_passenger_sum, truck_sum, motor_sum, capacity);
	}

	@Override
	public String[] toStrings() {

		String[] values = new String[5];

		values[0] = super.getId() + "";
		values[1] = super.getName();
		values[2] = super.getVehicleSum() + "";
		values[3] = super.getVehicleStartTime() + "";
		values[4] = super.getVehicleStartInterval() + "";

		return values;

	}
}
