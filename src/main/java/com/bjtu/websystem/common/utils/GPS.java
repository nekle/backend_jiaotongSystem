package com.bjtu.websystem.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 坐标对象，由经纬度构成
 * Created by xfkang on 2018/3/28.
 */
@Data
@AllArgsConstructor
public class GPS {
    private double lon;
    private double lat;
}
