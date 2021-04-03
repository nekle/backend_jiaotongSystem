package com.bjtu.websystem.model;/**
 * @author zhangsan
 * @date 2021/4/3
 */

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/4/3 20:22
 */
@Data
@AllArgsConstructor
public class CarInfoModel {
	private HashMap<Integer, Trail> carInfoHashMap;
	private int eTime;
	private int lTime;
}
