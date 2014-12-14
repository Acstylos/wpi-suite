/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    twack
 *    mpdelladonna
 *    bgaffey
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.core.models;

import java.util.Arrays;
import java.util.LinkedHashSet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * The Data Model representation of a Project. Offers
 * serialization and database interaction.
 * 
 * @author mdelladonna, twack, bgaffey
 */

public class Project extends AbstractModel {

    private String name;
    private String idNum;
    private String[] supportedModules;
    private User owner;
    protected LinkedHashSet<User> team;
    private String automaticEmailAddress;
    private String automaticEmailPassword;

    /**
     * Primary constructor for a Project
     * 
     * @param name - the project name
     * @param idNum - the project ID number as a string
     * @param owner - The User who owns this project
     * @param team - The User[] who are associated with the project
     * @param supportedModules - the modules supported by this project
     */
    public Project(String name, String idNum, User owner, User[] team, String[] supportedModules) {
        this.name = name;
        this.idNum = idNum;
        this.owner = owner;
        this.supportedModules = supportedModules;
        this.automaticEmailAddress = "";
        this.automaticEmailPassword = "";

        if (team != null) {
            this.team = new LinkedHashSet<User>(Arrays.asList(team));
        } else {
            this.team = new LinkedHashSet<User>();
        }
    }

    /**
     * Secondary constructor for a Project
     * 
     * @param name the project name
     * @param idNum the ID number to associate with this Project.
     */
    public Project(String name, String idNum) {
        this.name = name;
        this.idNum = idNum;
        this.team = new LinkedHashSet<User>();
    }

    /* Accessors */
    public String getName() {
        return name;
    }

    public String getIdNum() {
        return idNum;
    }
    
    public void setAutomaticEmailAddress(String automaticEmailAddress) {
        this.automaticEmailAddress = automaticEmailAddress;
    }
    
    public void setAutomaticEmailPasswowrd(String automaticEmailPassword) {
        this.automaticEmailPassword = automaticEmailPassword;
    }

    /* Mutators */
    public void setName(String newName) {
        this.name = newName;
    }

    protected void setIdNum(String newId) {
        this.idNum = newId;
    }
    
    public String getAutomaticEmailAddress() {
        return this.automaticEmailAddress;
    }
    
    /**
     * This is not set if run on a deserialized version on the client
     */
    public String getAutomaticEmailPassword() {
        return this.automaticEmailPassword;
    }

    /* database interaction */

    /**
     * Implements Model-specific save logic
     */
    public void save() {
        throw new UnsupportedOperationException(); // TODO: implement saving during API - DB Layer Link up
    }

    /**
     * Implements Model-specific delete logic
     */
    public void delete() {
        throw new UnsupportedOperationException(); // TODO: implement deleting during API - DB Layer Link up
    }

    public String getProjectName() {
        return name;
    }

    /* Serializing */

    /**
     * Serializes this Project's member variables into
     * a JSON string.
     * 
     * @return the JSON string representation of this Project
     */
    public String toJson() {
        String json = null;

        json = "{";
        json += "\"name\":\"" + this.name + "\"";
        json += ",\"idNum\":\"" + this.idNum + "\"";

        if (this.owner != null) {
            json += ",\"owner\":" + this.owner.toJson();
        }

        if (this.supportedModules != null && this.supportedModules.length > 0) {
            json += ",\"supportedModules\":[";
            for (String str : this.supportedModules) {
                json += "\"" + str + "\",";
            }

            //remove that last comma
            json = json.substring(0, json.length() - 1);
            json += "]";
        }

        if (this.team != null && this.team.size() > 0) {
            json += ",\"team\":[";

            for (User u : this.team) {
                json += u.toJson() + ",";
            }
            //remove that last comma
            json = json.substring(0, json.length() - 1);
            json += "]";
        }
        
        if (this.automaticEmailAddress != null) {
            json += ",\"automaticEmailAddress\":\"" + automaticEmailAddress + "\"";
        }
        
        json += "}";
        return json;
    }

    /**
     * Static Project method that serializes a list of Projects
     * into JSON strings.
     * 
     * @param u The list of Projects to serialize
     * @return a comma delimited list of Project JSON strings.
     */
    public static String toJson(Project[] projects) {
        return new Gson().toJson(projects, Project[].class);
    }

    /**
     * Deserializes the given JSON String into a Project's member variables
     * 
     * @return the Project from the given JSON string representation
     */
    public static Project fromJson(String json) {
        Gson gson;
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Project.class, new ProjectDeserializer());
        gson = builder.create();

        return gson.fromJson(json, Project.class);
    }

    /* Built-in overrides/overloads */

    /**
     * Returns the JSON representation of this object as
     * the toString.
     * 
     * @return the String returned by toJSON()
     * @see Project.toJSON()
     */
    public String toString() {
        return toJson();
    }

    @Override
    public Boolean identify(Object o) {
        Boolean b = false;

        if (o instanceof Project) {
            if (((Project) o).getIdNum().equalsIgnoreCase(this.idNum)) {
                b = true;
            }
        }

        if (o instanceof String)
            if (((String) o).equalsIgnoreCase((this.idNum)))
                b = true;

        return b;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Project other = (Project) obj;
        if (this.idNum == null) {
            if (other.idNum != null) {
                return false;
            }
        } else if (!this.idNum.equals(other.idNum)) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.owner == null) {
            if (other.owner != null) {
                return false;
            }
        } else if (!this.owner.equals(other.owner)) {
            return false;
        }
        if (!Arrays.equals(this.supportedModules, other.supportedModules)) {
            return false;
        }
        if (this.team == null) {
            if (other.team != null) {
                return false;
            }
        } else if (!this.team.equals(other.team)) {
            return false;
        }
        return true;
    }

    public String[] getSupportedModules() {
        return supportedModules;
    }

    public void setSupportedModules(String[] supportedModules) {
        this.supportedModules = supportedModules;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User[] getTeam() {
        return Arrays.copyOf(team.toArray(), team.size(), User[].class);
    }

    /**
     * adds a team member to the team
     * 
     * @param u - the user to add to the team
     * @return true if the user was added, false if the user was already in the team
     */
    public boolean addTeamMember(User user) {
        return team.add(user);
    }

    /**
     * removes a team member from the team
     * 
     * @param u - the team member to remove from the team
     * @return - true if the member was removed, false if they were not in the team
     */
    public boolean removeTeamMember(User user) {
        return team.remove(user);
    }

    /**
     * NotImplemented
     */
    @Override
    public Project getProject() {
        throw new UnsupportedOperationException();
    }

    /**
     * NotImplemented
     */
    @Override
    public void setProject(Project aProject) {
        throw new UnsupportedOperationException();
    }
}
