/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.taskmanager.model;

import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;

/**
 * Stores the next available ID for a model.
 */
class IDModel extends AbstractModel {

    int ID;
    String modelName;
    Project project;

    public IDModel(String modelName, int ID, Project aProject) {
        this.modelName = modelName;
        this.ID = ID;
        this.project = aProject;
    }

    public int getNextID(Data db) throws WPISuiteException {
        List<Model> models = db.retrieve(IDModel.class, "modelName", modelName,
                project);
        if (models.size() == 0)
            return 1;
        else
            return ((IDModel) models.get(0)).getID() + 1;
    }

    public void increment(Data db) throws WPISuiteException {
        ID = getNextID(db);
        db.save(this, this.project);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String toJSON() {
        String json;
        Gson gson = new Gson();
        json = gson.toJson(this, IDModel.class);
        return json;
    }

    public static String toJSON(IDModel[] idlist) {
        String json;
        Gson gson = new Gson();
        json = gson.toJson(idlist, IDModel.class);
        return json;
    }

    public static IDModel fromJSON(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, IDModel.class);
    }

    public Boolean identify(Object o) {
        Boolean returnValue = false;
        if (o instanceof IDModel && modelName == ((IDModel) o).getModelName())
            returnValue = true;
        return returnValue;
    }

    public void save() {
    }

    public void delete() {
    }

    @Override
    public String toJson() {
        // TODO Auto-generated method stub
        return null;
    }
}
