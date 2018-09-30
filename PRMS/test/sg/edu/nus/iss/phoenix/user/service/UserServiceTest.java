/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.service;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import sg.edu.nus.iss.phoenix.schedule.exceptions.OverlapException;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 *
 * @author MyatMin
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {

    private User toCreate;
    private User toUpdate;
    private static UserService service;

    public UserServiceTest() {

    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        service = new UserService();
    }

    @AfterClass
    public static void tearDownClass() {
        service = null;
    }

    @Before
    public void setUp() {
        toCreate = new User();
        toUpdate = new User();
        toUpdate.appointAll("test02", "password", "test02", "presenter");
    }

    @After
    public void tearDown() {
        toCreate = null;
        toUpdate = null;
    }

    @Test
    public void test01_createUser_withNotExistedKeys_shouldCreate() throws OverlapException,SQLException, DuplicateException, InvalidDataException {
       // thrown.expect(InvalidDataException.class);
        toCreate.appointAll("testuser"+ ZonedDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")), "password", "test01", "presenter");
        service.createUser(toCreate);
    }

    @Test
    public void test02_createUser_withNullKeys_shouldThrowInvalidData() throws OverlapException,SQLException, DuplicateException, InvalidDataException {
        thrown.expect(InvalidDataException.class);
        service.createUser(toCreate);
    }

    @Test
    public void test03_updateUser_withNewRole_shouldUpdate() {
        try {
            ArrayList<Role> roles = new ArrayList<Role>(){};
            roles.add(new Role("producer"));
            toUpdate.setRoles(roles);
            service.updateUser(toUpdate);
            
        } catch (Exception ex) {
            fail(ex.getMessage());
            
        }
    }

    @Test
    public void test04_updateUser_withNewName_shouldUpdate() throws NotFoundException, OverlapException,SQLException, DuplicateException, InvalidDataException {
       // thrown.expect(InvalidDataException.class);
        toUpdate.setName("test04");
        service.updateUser(toUpdate);
        
    }

    @Test
    public void test05_updateUser_withNewPassword_shouldUpdate() throws NotFoundException, OverlapException,SQLException, DuplicateException, InvalidDataException {
        
        toUpdate.setPassword("password2");
        service.updateUser(toUpdate);
        
    }
}
