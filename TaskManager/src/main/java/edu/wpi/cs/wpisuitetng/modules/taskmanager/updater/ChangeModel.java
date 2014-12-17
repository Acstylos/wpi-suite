package edu.wpi.cs.wpisuitetng.modules.taskmanager.updater;

/* Model interface. */
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/* Change type. */
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/* JSON methods. */
import com.google.gson.Gson;

/**
 * Flat data type representing a change in the database.
 */
public class ChangeModel extends AbstractModel {

    /**
     * Type of change. Put : Create, Get : Read, Post : Update, Delete : Delete.
     */
    HttpMethod request;

    /**
     * Enum of changeable objects. One for every Model type in TaskManager. Used
     * to represent which object type is being updated.
     */
    public enum ChangeObjectType {
        ACTION, TASK, BUCKET, WORKFLOW
    }

    /**
     * The object type. Used with the ID to indicate what object to update. If
     * request is Create or Delete, the ID is that of the parent object. For
     * instance, upon deleting a task, you would have Delete : Task : <Parent
     * ID>.
     */
    ChangeObjectType objectType;

    /**
     * Id of the object to update. If request is Create or Delete, this is the
     * ID of the parent object. See objectType documentation.
     */
    int id;

    /**
     * Create a ChangeModel with null defaults.
     */
    public ChangeModel() {
        this.request = null;
        this.objectType = null;
        this.id = 0;
    }

    /**
     * Create a new ChangeModel.
     *
     * @param request
     *            CRUD operation
     * @param objectType
     *            type of model for this change
     * @param id
     *            ID of change
     */
    public ChangeModel(HttpMethod request, ChangeObjectType objectType, int id) {
        this.request = request;
        this.objectType = objectType;
        this.id = id;
    }

    /**
     * Unused.
     */
    @Override
    public void save() {
    }

    /**
     * Unused.
     */
    @Override
    public void delete() {
    }

    /**
     * Generate a JSON string representing this object.
     *
     * @return JSON formatted string containing the Change structure.
     */
    @Override
    public String toJson() {
        return new Gson().toJson(this, ChangeModel.class);
    }

    /**
     * Create a new ChangeModel with the fields in a JSON string.
     *
     * @param json
     *            JSON object with fields for ChangeModel
     * @return generated ChangeModel
     */
    public static ChangeModel fromJson(String json) {
        return new Gson().fromJson(json, ChangeModel.class);
    }

    /**
     * Creates a list of ChangeModel with the fields in a JSON string.
     *
     * @param json
     *            String with list of change parameters.
     * @return List of ChangeModels from the JSON string.
     */
    public static ChangeModel[] fromJsonArray(String json) {
        return new Gson().fromJson(json, ChangeModel[].class);
    }

    /**
     * Unused.
     */
    @Override
    public Boolean identify(Object o) {
        return null;
    }

    /**
     * Getter for CRUD operation.
     *
     * @return CRUD operation as an HttpMethod
     */
    public HttpMethod getRequest() {
        return request;
    }

    /**
     * Setter for the CRUD operation.
     *
     * @param request
     *            CRUD operation as an HttpMethod
     */
    public void setRequest(HttpMethod request) {
        this.request = request;
    }

    /**
     * Getter for changing object's type.
     *
     * @return object type
     */
    public ChangeObjectType getObjectType() {
        return objectType;
    }

    /**
     * Setter for changing object's type.
     *
     * @param objectType
     *            type of the object
     */
    public void setObjectType(ChangeObjectType objectType) {
        this.objectType = objectType;
    }

    /**
     * Getter for changing object's id.
     *
     * @return Object's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for changing object's id.
     *
     * @param id
     *            new id.
     */
    public void setId(int id) {
        this.id = id;
    }

}
