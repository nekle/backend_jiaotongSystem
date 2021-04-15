package com.bjtu.websystem.model.datasetModels;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nekkl
 * @version 1.0
 * @description: cross 类，表示一个cross节点
 * @date 2021/4/12 23:19
 */
@Data
@TableName("cross_tbl")
@AllArgsConstructor
public class Cross extends ToStrings {
	private Integer id;
	private String name;
	private double longitude;
	private double latitude;
	private Integer type;
	private Integer vehicleSum;
	private Integer vehicleStartTime;
	private Integer vehicleStartInterval;
	private Integer passengerSum;
	private Integer carSum;
	private Integer truckSum;
	private Integer motorSum;
	private Integer capacity;

	@Override
	public String[] toStrings() {
		String[] values = new String[13];
		values[0] = id + "";
		values[1] = name;
		values[2] = longitude + "";
		values[3] = latitude + "";
		values[4] = type + "";
		values[5] = vehicleSum + "";
		values[6] = vehicleStartTime + "";
		values[7] = vehicleStartInterval + "";
		values[8] = passengerSum + "";
		values[9] = carSum + "";
		values[10] = truckSum + "";
		values[11] = motorSum + "";
		values[12] = capacity + "";

		return values;
	}
}
