/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.*;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactory;
import sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException;

/**
 *
 * @author Pradeep
 */
public class UserService {

    public UserService() {
    }

    private static final Logger logger
            = Logger.getLogger(UserService.class.getName());


    /**
     * Compare the list of newly added roles to exiusting roles for a user
     *
     * @param currentRoles
     * @param existingRoles
     * @return list of roles to be removed
     */
    public List<Role> compare(List<Role> currentRoles, List<Role> existingRoles) {
        
        List<Role> listNew = new ArrayList<Role>(currentRoles);
        List<Role> listExist = new ArrayList<Role>(existingRoles);
        listExist.removeAll(listNew);
        return listExist;
     
    }

    /**
     * Creates a user resource in persistent data store
     *
     * @param input
     * @return true if creation is successful
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException
     */
    public Boolean createUser(User input) throws InvalidDataException{
        //throw new UnsupportedOperationException();
        try {
            DAOFactory.getUserDAO().create(input);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    

  /**
     * Updates a user resource in persistent data store
     *
     * @param input
     * @return true if update user is successful
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException
     */
    public Boolean updateUser(User input) throws InvalidDataException{
        //throw new UnsupportedOperationException();

        try {
            DAOFactory.getUserDAO().save(input);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new InvalidDataException(e.getMessage());
        }
        
    }

    

  /**
     * Delete a user resource from persistent data store
     *
     * @param id
     * @return true if delete user is successful
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException
     */
    public Boolean deleteUser(String id) throws InvalidDataException{
        //throw new UnsupportedOperationException();
        try {
            User delUser = new User(id);
            DAOFactory.getUserDAO().delete(delUser);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new InvalidDataException(e.getMessage());
        }
        
    }


  /**
     * Find all the users with a presenter role.
     *
     * @return  list of users with presenter role 
   */
    public List<User> findAllPresenters() {
        //throw new UnsupportedOperationException();
          ArrayList<User> presenterList = new ArrayList<User>();
        try {
            Role searchRole = new Role("presenter");
            presenterList = (ArrayList<User>) DAOFactory.getUserDAO().getUsersByRole(searchRole);
      
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return presenterList;

    }

  /**
     * Find all the users with a producer role.
     *
     * @return  list of users with producer role 
   */
    public List<User> findAllProducers() {
        //throw new UnsupportedOperationException();
               //throw new UnsupportedOperationException();
          ArrayList<User> producerList = new ArrayList<User>();
        try {
            Role searchRole = new Role("producer");
            producerList = (ArrayList<User>) DAOFactory.getUserDAO().getUsersByRole(searchRole);
      
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return producerList;

    }

  /**
     * Get all users in the system.
     *
     * @return  list of all users 
   */
    public List<User> getAllUsers() {
        ArrayList<User> currentList = new ArrayList<User>();
        try {
            currentList = (ArrayList<User>) DAOFactory.getUserDAO()
                    .loadAll();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return currentList;
    }

  /**
     * Search for a user by ID.
     *
     * @param Id
     * @return  a user with matching Id  
   */
    public User getUserById(String Id) {
        //throw new UnsupportedOperationException();
            //throw new UnsupportedOperationException();
            User usr = null;
        try {
             usr=DAOFactory.getUserDAO().searchMatching(Id);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return usr;
        
    }
}
