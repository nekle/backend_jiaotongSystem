package com.bjtu.websystem.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@TableName("link_tbl")
@Data
@AllArgsConstructor
public class Link {
    private int id;
    private int cross1;
    private int cross2;
    private int a;
    private int b;
}
