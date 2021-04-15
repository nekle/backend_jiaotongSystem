package com.bjtu.websystem.model.readFileModels;/**
 * @author zhangsan
 * @date 2021/3/31
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nekkl
 * @version 1.0
 * @description: 存储cross_xy_LONLAT.csv文件里一行数据的信息
 * @date 2021/3/31 19:47
 */
@Data
@AllArgsConstructor
public class CrossGrid {
    private int id;
    private double longitude;
    private double latitude;
}
