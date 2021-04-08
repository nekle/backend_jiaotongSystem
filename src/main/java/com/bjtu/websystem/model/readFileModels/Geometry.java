package com.bjtu.websystem.model.readFileModels;/**
 * @author zhangsan
 * @date 2021/4/1
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/4/1 10:47
 */
@Data
@AllArgsConstructor
public class Geometry {
	private int startTime;
	private int endTime;
	private int carType;
	private double[][] coordinates;
	private Integer[] times;
}
