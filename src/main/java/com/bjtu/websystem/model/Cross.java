package com.bjtu.websystem.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("cross_tbl")
@AllArgsConstructor
public class Cross {
    private int id;
    private String name;
    private double longitude;
    private double latitude;
    private int type;
    private int car_sum;
    private int car_start_time;
    private int car_start_interval;
    private int passenger_sum;
    private int car_passenger_sum;
    private int truck_sum;
    private int motor_sum;
    private int capacity;
}
