package com.bjtu.websystem.service;

import com.bjtu.websystem.model.Link;

import java.util.List;

public interface LinkService {
    /**
        * @description: 获取所有连接Link
        * @author Nekkl
        * @date: 2021/4/6 19:41
        * @param: []
        * @return: List<Link>
     */
    public List<Link> getAllLinks();
}
