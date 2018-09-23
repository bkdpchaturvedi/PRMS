/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;
import sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException;
import sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.user.dao.impl.UserDaoImpl;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 *
 * @author MyatMin
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOTest {

    private User toCreate;
    private User toUpdate;
    private static UserDao userDAO;

    public UserDAOTest() {

    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        userDAO = new UserDaoImpl();
    }

    @AfterClass
    public static void tearDownClass() {
        userDAO = null;
    }

    @Before
    public void setUp() {
        toCreate = new User();
        toUpdate = new User();
        toUpdate.appointAll("testuser2", "Password", "testuser2", "presenter");
    }

    @After
    public void tearDown() {
        toCreate = null;
        toUpdate = null;
    }

    @Test
    public void test01_createUser_withNotExistedKeys_shouldCreate() throws DuplicateException, InvalidDataException, SQLException {
        thrown.expect(InvalidDataException.class);
        toCreate.appointAll("testuser1", "Password", "testuser1", "presenter");
        userDAO.create(toCreate);
    }

    @Test
    public void test02_createUser_withNullKeys_shouldThrowInvalidData() throws DuplicateException, InvalidDataException, SQLException {
        thrown.expect(InvalidDataException.class);
        userDAO.create(toCreate);
    }

    @Test
    public void test03_updateUser_withNewRole_shouldUpdate() {
        try {
            ArrayList<Role> roles = new ArrayList<Role>(){};
            roles.add(new Role("presenter"));
            toUpdate.setRoles(roles);
            userDAO.create(toUpdate);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void test04_updateUser_withNewName_shouldUpdate() {
        try {
            toUpdate.setName("test04");
            userDAO.create(toUpdate);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void test05_updateUser_withNewPassword_shouldUpdate() throws SQLException, NotFoundException {
        thrown.expect(NotFoundException.class);
        toUpdate.setPassword("password2");
        userDAO.save(toUpdate);
    }
}
