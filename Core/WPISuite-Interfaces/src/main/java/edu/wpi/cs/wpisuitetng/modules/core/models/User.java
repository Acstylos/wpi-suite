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
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.core.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * The Data Model representation of a User.
 * Implements database interaction and serializing.
 * 
 * @author mdelladonna, twack, bgaffey
 */

public class User extends AbstractModel {
    @Expose private int idNum;
    @Expose private String username;
    @Expose private String name;
    @Expose private Role role;

    transient private String password; // excluded from serialization, still stored.

    /**
     * The primary constructor for a User
     * 
     * @param name User's full name
     * @param username User's username (nickname)
     * @param idNum User's ID number
     */
    public User(String name, String username, String password, int idNum) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.idNum = idNum;
        this.role = Role.USER;
    }

    /**
     * Performs password checking logic. Fails if password field is null, which happens
     * when User is deserialized so as to protect the password.
     * 
     * @param pass the password String to compare
     * @return True if the password matches, False otherwise.
     */
    public boolean matchPassword(String pass) {
        return (this.password == null) ? false : password.equals(pass);
    }

    /**
     * Sets password (please encrypt before using this method)
     * 
     * @param pass
     */
    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getPassword() {
        return this.password;
    }

    /* Accessors */
    public String getName() {
        return name;
    }

    public int getIdNum() {
        return idNum;
    }

    public String getUsername() {
        return username;
    }

    /* Serializing */

    /**
     * Serializes this User model into a JSON string.
     * 
     * @return the JSON representation of this User
     */
    public String toJson() {
        String json;

        Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new UserSerializer()).create();

        json = gson.toJson(this, User.class);

        return json;
    }

    /**
     * Static method offering comma-delimited JSON
     * serializing of User lists
     * 
     * @param u an array of Users
     * @return the serialized array of Users
     */
    public static String toJSON(User[] u) {
        String json = "[";

        for (User a : u) {
            json += a.toJson() + ", ";
        }

        json += "]";

        return json;

    }
public static User[] fromJsonArray(String json){
    final Gson parser = new Gson();
    return parser.fromJson(json,User[].class);
}
    /* Built-in overrides/overloads */

    /**
     * Override of toString() to return a JSON string for now.
     * May override in the future.
     */
    public String toString() {
        return this.toJson();
    }

    @Override
    public Boolean identify(Object o) {
        Boolean b = false;

        if (o instanceof User)
            if (((User) o).username.equalsIgnoreCase(this.username))
                b = true;

        if (o instanceof String)
            if (((String) o).equalsIgnoreCase(this.username))
                b = true;
        return b;
    }

    public User setName(String newName) {
        this.name = newName;
        return this;
    }

    public User setUserName(String newUserName) {
        this.username = newUserName;
        return this;
    }

    public User setIdNum(int newidNum) {
        this.idNum = newidNum;
        return this;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role r) {
        this.role = r;
    }

    public static User fromJSON(String json) {
        // build the custom serializer/deserializer
        Gson gson;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(User.class, new UserDeserializer());

        gson = builder.create();

        return gson.fromJson(json, User.class);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.idNum;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.role == null) ? 0 : this.role.hashCode());
        result = prime * result + ((this.username == null) ? 0 : this.username.hashCode());
        return result;
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
        User other = (User) obj;
        if (this.idNum != other.idNum) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
            return false;
        }
        if (this.role != other.role) {
            return false;
        }
        if (this.username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!this.username.equals(other.username)) {
            return false;
        }
        return true;
    }

    /*
     * Unimplemented methods
     */

    /* database interaction */
    public void save() {
        return;
    }

    public void delete() {
        return;
    }

    @Override
    public Project getProject() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setProject(Project aProject) {
        //Users are not currently Associated with projects directly 
    }

}
