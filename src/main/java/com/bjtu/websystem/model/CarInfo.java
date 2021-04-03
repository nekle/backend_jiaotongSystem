package com.bjtu.websystem.model;/**
 * @author zhangsan
 * @date 2021/3/31
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nekkl
 * @version 1.0
 * @description: 汽车位置对象，每一个对象标志了汽车的信息和当前的位置
 * @date 2021/3/31 19:20
 */
@Data
@AllArgsConstructor
public class CarInfo {
    private int carNum;
    private int globalTime;
    private int crossType;
    private int carType;
    private int passengerNum;
    private int oCrossNum;
    private int dCrossNum;
    private int grid;
}
