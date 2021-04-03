package com.bjtu.websystem.service.Impl;

import com.bjtu.websystem.mapper.LinkMapper;
import com.bjtu.websystem.model.Link;
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
}
