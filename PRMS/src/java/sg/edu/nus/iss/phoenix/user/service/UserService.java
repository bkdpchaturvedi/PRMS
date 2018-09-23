/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.util.logging.*;
import sg.edu.nus.iss.phoenix.user.dao.RoleDao;
import sg.edu.nus.iss.phoenix.user.dao.UserDao;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactory;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 *
 * @author chaturbkdp
 */
public class UserService {

    public UserService() {
    }

    private static final Logger logger
            = Logger.getLogger(UserService.class.getName());

    /**
     * assign/remove user roles based on the action selected
     *
     * @param input
     * @return
     */
    public boolean assignRemoveUserRoles(User input) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param currentRoles
     * @param existingRoles
     */
    public void compare(List<Role> currentRoles, List<Role> existingRoles) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param input
     */
    public Boolean createUser(User input) {
        throw new UnsupportedOperationException();

    }

    /**
     *
     * @param input
     */
    public Boolean updateUser(User input) {
        throw new UnsupportedOperationException();

    }

    /**
     *
     * @param input
     */
    public Boolean deleteUser(String id) {
        throw new UnsupportedOperationException();

    }

    /**
     *
     * @return
     */
    public List<User> findAllPresenters() {
        throw new UnsupportedOperationException();

    }

    /**
     *
     * @return
     */
    public List<User> findAllProducers() {
        throw new UnsupportedOperationException();

    }

    /**
     *
     * @return
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
     *
     * @param input
     * @return
     */
    public User getUser(User input) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    public User getUserById(String Id) {
        throw new UnsupportedOperationException();
    }
}
