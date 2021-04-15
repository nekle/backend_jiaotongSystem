package com.bjtu.websystem.service;

import com.bjtu.websystem.model.datasetModels.Cross;

import java.util.List;

public interface CrossService {
    public Cross getCrossById(int id);
    public List<Cross> getAllCrosses();
    public List<Cross> getCrossByType(int type);
    public int editCrossById(Cross cross);
}
