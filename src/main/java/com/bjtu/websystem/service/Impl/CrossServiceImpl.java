package com.bjtu.websystem.service.Impl;

import com.bjtu.websystem.common.utils.toFile.EditVars;
import com.bjtu.websystem.mapper.CrossMapper;
import com.bjtu.websystem.mapper.toFileMappers.BusPointMapper;
import com.bjtu.websystem.mapper.toFileMappers.CivilPointMapper;
import com.bjtu.websystem.mapper.toFileMappers.SettlePointMapper;
import com.bjtu.websystem.model.datasetModels.Cross;
import com.bjtu.websystem.service.CrossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CrossServiceImpl implements CrossService {

    @Autowired
    CrossMapper crossMapper;
    @Autowired
    BusPointMapper busPointMapper;
    @Autowired
    CivilPointMapper civilPointMapper;
    @Autowired
    SettlePointMapper settlePointMapper;

    @Override
    public Cross getCrossById(int id) {
        return crossMapper.selectById(id);
    }



    @Override
    public List<Cross> getAllCrosses() {
        return crossMapper.selectList(null);
    }

    @Override
    public List<Cross> getCrossByType(int type) {
        return crossMapper.getCrossByType(type);
    }

    @Override
    public int editCrossById(Cross cross) {

        // 修改数据库
        int result = crossMapper.updateById(cross);

        // 将数据库修改作用到csv文件
        EditVars editVars = new EditVars();
        String dir = "C:\\Users\\Nekkl\\Desktop\\un_un";
        boolean flag1 = editVars.writeAllPoints(dir, crossMapper);
        boolean flag2 = editVars.writeBUSPoints(dir, busPointMapper);
        boolean flag3 = editVars.writeCIVILPoints(dir, civilPointMapper);
        boolean flag4 = editVars.writeSETTLEPoints(dir, settlePointMapper);
        if ( result == 1&& flag1 && flag2 && flag3 && flag4){
            result = 1;
            return result;
        }
        result = 0;
        return result;
    }
}
