package com.bjtu.websystem.service.Impl;

import com.bjtu.websystem.mapper.CrossMapper;
import com.bjtu.websystem.model.Cross;
import com.bjtu.websystem.service.CrossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CrossServiceImpl implements CrossService {
    @Autowired
    CrossMapper crossMapper;

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
}
