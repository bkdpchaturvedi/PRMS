/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import java.util.logging.*;
import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactory;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;

/**
 *
 * @author chaturbkdp
 */
public class UserService {

    public UserService() {
    }
    
    private static final Logger logger = 
			Logger.getLogger(UserService.class.getName());
    /**
     * assign/remove user roles based on the action selected
     * @param input
     * @return 
     */
    public boolean assignRemoveUserRoles(User input)
    {
        throw new UnsupportedOperationException();
    }
    /**
     * 
     * @param currentRoles
     * @param existingRoles 
     */
    public void compare(List<Role> currentRoles, List<Role> existingRoles){
         throw new UnsupportedOperationException();
    }
    
    /**
     * 
     * @param input 
     */
    public void createUser(User input){
        throw new UnsupportedOperationException();
    
    }
    /**
     * 
     * @param input 
     */
    public void updateUser(User input){
        throw new UnsupportedOperationException();
    
    }
    /**
     * 
     * @param input 
     */
    public void deleteUser(String id){
        throw new UnsupportedOperationException();
    
    }
    /**
     * 
     * @return 
     */
    public User[] findAllPresenters(){
        throw new UnsupportedOperationException();
    
    }
    /**
     * 
     * @return 
     */
    public User[] findAllProducers(){
        throw new UnsupportedOperationException();
    
    }
    
    /**
     * 
     * @return 
     */
    public User[] getAllUsers(){
        throw new UnsupportedOperationException();
    
    }
    /**
     * 
     * @param input
     * @return 
     */
    public User getUser(User input) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    
}