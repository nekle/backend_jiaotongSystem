package com.bjtu.websystem.service.Impl;

import com.bjtu.websystem.mapper.OtherSettingsMapper;
import com.bjtu.websystem.model.datasetModels.OtherSettings;
import com.bjtu.websystem.service.OtherSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Nekkl
 * @version 1.0
 * @description: Implementation of OtherSettingsService
 * @date 2021/4/14 17:52
 */
@Service
public class OtherSettingsImpl implements OtherSettingsService {

	@Autowired
	OtherSettingsMapper otherSettingsMapper;

	@Override
	public OtherSettings getOtherSettings() {

//		Integer isOpened = projectMapper.selectCount(new QueryWrapper<Project>().eq("isOpen", 1));
		Integer isOpened = 1;
		if (isOpened == 1){
			return otherSettingsMapper.selectOne(null);
		}else {
			return null;
		}

	}

	@Override
	public int updateOtherSettings(OtherSettings otherSettings) {
		return otherSettingsMapper.update(otherSettings, null);
	}
}
