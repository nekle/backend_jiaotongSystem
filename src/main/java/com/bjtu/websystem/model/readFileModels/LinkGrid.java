package com.bjtu.websystem.model.readFileModels;/**
 * @author zhangsan
 * @date 2021/3/31
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nekkl
 * @version 1.0
 * @description: 存储grid_xy_LONLAT.csv文件里一行的数据
 * @date 2021/3/31 19:52
 */
@Data
@AllArgsConstructor
public class LinkGrid {
    private int originCross;
    private int destinationCross;
    private int gridNumber;
    private double longitude;
    private double latitude;
}
