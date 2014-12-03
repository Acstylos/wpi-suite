/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.taskmanager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;

/**
 * A mock data implementation for server-side testing.
 * This class was copied by The Floor Is Java from requirementsmanager,
 * and was written by Team Rolling Thunder. It was copied for the purpose
 * of testing.
 * 
 * @author justinhess
 * @version $Revision: 1.0 $
 */
@Deprecated
public class MockData implements Data {

    private final Set<Object> objects;

    /**
     * Create a MockData instance initially containing the given set of objects
     * 
     * @param objects The set of objects this "database" starts with
     */
    public MockData(Set<Object> objects) {
        this.objects = objects;
    }

    /**
     * Method delete.
     * 
     * @param arg0 T @return T * @see edu.wpi.cs.wpisuitetng.database.Data#delete(T) * @see edu.wpi.cs.wpisuitetng.database.Data#delete(T)
     */
    @Override
    public <T> T delete(T arg0) {
        if (objects.contains(arg0)) {
            objects.remove(arg0);
            return arg0;
        }
        return null;
    }

    /**
     * Method deleteAll.
     * 
     * @param arg0 T @return List<T> * @see edu.wpi.cs.wpisuitetng.database.Data#deleteAll(T) * @see edu.wpi.cs.wpisuitetng.database.Data#deleteAll(T)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> deleteAll(T arg0) {
        List<T> deleted = new ArrayList<T>();
        for (Object obj : objects) {
            if (arg0.getClass().isInstance(obj)) {
                deleted.add((T) obj);
            }
        }
        // can't remove in the loop, otherwise you get an exception
        objects.removeAll(deleted);
        return deleted;
    }

    /**
     * Method retrieve.
     * 
     * @param type Class
     * @param fieldName String
     * @param value Object @return List<Model> * @see edu.wpi.cs.wpisuitetng.database.Data#retrieve(Class, String, Object) * @see edu.wpi.cs.wpisuitetng.database.Data#retrieve(Class, String, Object)
     */
    @Override
    public List<Model> retrieve(Class<?> type, String fieldName, Object value) {
        List<Model> rv = new ArrayList<Model>();
        for (Object obj : objects) {
            if (!type.isInstance(obj)) {
                continue;
            }
            Method[] methods = obj.getClass().getMethods();
            for (Method method : methods) {
                if (method.getName().equalsIgnoreCase("get" + fieldName)) {
                    try {
                        if (method.invoke(obj).equals(value)) {
                            rv.add((Model) obj);
                        }
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return rv;
    }

    /**
     * Method retrieveAll.
     * 
     * @param arg0 T @return List<T> * @see edu.wpi.cs.wpisuitetng.database.Data#retrieveAll(T) * @see edu.wpi.cs.wpisuitetng.database.Data#retrieveAll(T)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> retrieveAll(T arg0) {
        List<T> all = new ArrayList<T>();
        for (Object obj : objects) {
            if (arg0.getClass().isInstance(obj)) {
                all.add((T) obj);
            }
        }
        return all;
    }

    /**
     * Method save.
     * 
     * @param arg0 T @return boolean * @see edu.wpi.cs.wpisuitetng.database.Data#save(T) * @see edu.wpi.cs.wpisuitetng.database.Data#save(T)
     */
    @Override
    public <T> boolean save(T arg0) {
        objects.add(arg0);
        return true;
    }

    /**
     * Method update.
     * 
     * @param arg0 Class
     * @param arg1 String
     * @param arg2 Object
     * @param arg3 String
     * @param arg4 Object @see edu.wpi.cs.wpisuitetng.database.Data#update(Class, String, Object, String, Object)
     */
    @Override
    public void update(Class<?> arg0, String arg1, Object arg2, String arg3, Object arg4) {
        // TODO Auto-generated method stub

    }

    /**
     * Method andRetrieve.
     * 
     * @param arg0 Class
     * @param arg1 String[]
     * @param arg2 List<Object> @return List<Model> * @throws WPISuiteException * @throws IllegalArgumentException * @throws IllegalAccessException * @throws InvocationTargetException * @see edu.wpi.cs.wpisuitetng.database.Data#andRetrieve(Class, String[], List<Object>) * @throws WPISuiteException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @see edu.wpi.cs.wpisuitetng.database.Data#andRetrieve(Class, String[], List<Object>)
     */
    @Override
    public List<Model> andRetrieve(Class<?> arg0, String[] arg1, List<Object> arg2) throws WPISuiteException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method complexRetrieve.
     * 
     * @param arg0 Class
     * @param arg1 String[]
     * @param arg2 List<Object>
     * @param arg3 Class
     * @param arg4 String[]
     * @param arg5 List<Object> @return List<Model> * @throws WPISuiteException * @throws IllegalArgumentException * @throws IllegalAccessException * @throws InvocationTargetException * @see edu.wpi.cs.wpisuitetng.database.Data#complexRetrieve(Class, String[], List<Object>, Class, String[], List<Object>) * @throws WPISuiteException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @see edu.wpi.cs.wpisuitetng.database.Data#complexRetrieve(Class, String[], List<Object>, Class, String[], List<Object>)
     */
    @Override
    public List<Model> complexRetrieve(Class<?> arg0, String[] arg1, List<Object> arg2, Class<?> arg3, String[] arg4, List<Object> arg5) throws WPISuiteException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method deleteAll.
     * 
     * @param arg0 T
     * @param arg1 Project @return List<Model> * @see edu.wpi.cs.wpisuitetng.database.Data#deleteAll(T, Project) * @see edu.wpi.cs.wpisuitetng.database.Data#deleteAll(T, Project)
     */
    @Override
    public <T> List<Model> deleteAll(T arg0, Project arg1) {
        List<Model> toDelete = retrieveAll(arg0, arg1);
        objects.removeAll(toDelete);
        return toDelete;
    }

    /**
     * Method orRetrieve.
     * 
     * @param arg0 Class
     * @param arg1 String[]
     * @param arg2 List<Object> @return List<Model> * @throws WPISuiteException * @throws IllegalAccessException * @throws InvocationTargetException * @see edu.wpi.cs.wpisuitetng.database.Data#orRetrieve(Class, String[], List<Object>) * @throws WPISuiteException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @see edu.wpi.cs.wpisuitetng.database.Data#orRetrieve(Class, String[], List<Object>)
     */
    @Override
    public List<Model> orRetrieve(Class<?> arg0, String[] arg1, List<Object> arg2) throws WPISuiteException, IllegalAccessException, InvocationTargetException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Method filterByProject.
     * 
     * @param models List<Model>
     * @param project Project @return List<Model>
     */
    private List<Model> filterByProject(List<Model> models, Project project) {
        List<Model> filteredModels = new ArrayList<Model>();
        for (Model m : models) {
            if (m.getProject().getName().equalsIgnoreCase(project.getName())) {
                filteredModels.add(m);
            }
        }
        return filteredModels;
    }

    /**
     * Method retrieve.
     * 
     * @param arg0 Class
     * @param arg1 String
     * @param arg2 Object
     * @param arg3 Project @return List<Model> * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.database.Data#retrieve(Class, String, Object, Project) * @throws WPISuiteException
     * @see edu.wpi.cs.wpisuitetng.database.Data#retrieve(Class, String, Object, Project)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public List<Model> retrieve(Class arg0, String arg1, Object arg2, Project arg3) throws WPISuiteException {
        return filterByProject(retrieve(arg0, arg1, arg2), arg3);
    }

    /**
     * Method retrieveAll.
     * 
     * @param arg0 T
     * @param arg1 Project @return List<Model> * @see edu.wpi.cs.wpisuitetng.database.Data#retrieveAll(T, Project) * @see edu.wpi.cs.wpisuitetng.database.Data#retrieveAll(T, Project)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> List<Model> retrieveAll(T arg0, Project arg1) {
        return filterByProject((List<Model>) retrieveAll(arg0), arg1);
    }

    /**
     * Method save.
     * 
     * @param arg0 T
     * @param arg1 Project @return boolean * @see edu.wpi.cs.wpisuitetng.database.Data#save(T, Project) * @see edu.wpi.cs.wpisuitetng.database.Data#save(T, Project)
     */
    @Override
    public <T> boolean save(T arg0, Project arg1) {
        ((Model) arg0).setProject(arg1);
        save(arg0);
        return true;
    }

}
