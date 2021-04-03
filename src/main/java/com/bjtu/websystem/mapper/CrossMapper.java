package com.bjtu.websystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjtu.websystem.model.Cross;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CrossMapper extends BaseMapper<Cross> {

    @Select("select * from cross_tbl where type = #{type}")
    List<Cross> getCrossByType(int type);
}
