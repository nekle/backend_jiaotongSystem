package com.bjtu.websystem.service.Impl;

import com.bjtu.websystem.common.utils.toFile.EditVars;
import com.bjtu.websystem.mapper.LinkMapper;
import com.bjtu.websystem.model.datasetModels.Link;
import com.bjtu.websystem.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    LinkMapper linkMapper;

    @Override
    public List<Link> getAllLinks() {
        return linkMapper.selectList(null);
    }

    @Override
    public int editLinkById(Link link){

        int result = linkMapper.updateById(link);
        // 将数据库修改作用到csv文件
        EditVars editVars = new EditVars();
        String dir = "C:\\Users\\Nekkl\\Desktop\\un_un";
        boolean flag = editVars.writeAllLinks(dir, linkMapper);

        return (result == 1 && flag) ? 1 : 0;

    }
}
