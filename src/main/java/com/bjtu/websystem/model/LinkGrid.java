package com.bjtu.websystem.model;/**
 * @author zhangsan
 * @date 2021/3/31
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
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
