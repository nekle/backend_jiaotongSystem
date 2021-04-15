package com.bjtu.websystem.service;

import com.bjtu.websystem.model.datasetModels.OtherSettings;

/**
 * @author Nekkl
 * @version 1.0
 * @description: OtherService Interface
 * @date 2021/4/14 17:52
 */
public interface OtherSettingsService {
	public OtherSettings getOtherSettings();
	public int updateOtherSettings(OtherSettings otherSettings);
}
