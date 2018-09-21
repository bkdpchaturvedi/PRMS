/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.authenticate.service;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 *
 * @author Drake
 */
public class AuthenticateServiceTest {

    private User toUser;
    private User returnUser;
    private static AuthenticateService service;

    public AuthenticateServiceTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        service = new AuthenticateService();
    }

    @AfterClass
    public static void tearDownClass() {
        service = null;
    }

    @Before
    public void setUp() {
        toUser = new User();
        toUser.setId("pointyhead");
    }

    @After
    public void tearDown() {
        toUser = null;
        returnUser = null;
    }

    @Test
    public void test1_validateUserIdPassword_withValidDetails_shouldRetrunUser() {
        toUser.setPassword("pointyhead");
        assertNull(returnUser);
        returnUser = service.validateUserIdPassword(toUser);
        assertNotNull(returnUser);
        assertNotNull(returnUser.getRoles());
    }

    @Test
    public void test2_validateUserIdPassword_withInvalidPassword_shouldReturnNull() {
        toUser.setPassword("spell");
        assertNull(returnUser);
        returnUser = service.validateUserIdPassword(toUser);
        assertNull(returnUser);
    }

    @Test
    public void test3_validateUserIdPassword_withInvalidUserId_shouldReturnNull() {
        toUser.setId("wizard");
        assertNull(returnUser);
        returnUser = service.validateUserIdPassword(toUser);
        assertNull(returnUser);
    }

    @Test
    public void test4_evaluateAccessPreviledge_withExistsRoles_shouldReturnRoles() {
        toUser.setRoles(new ArrayList<Role>() {
            {
                add(new Role("manager"));
                add(new Role("producer"));
                add(new Role("presenter"));
                add(new Role("admin"));
            }
        });
        assertNull(returnUser);
        returnUser = service.evaluateAccessPreviledge(toUser);
        assertNotNull(returnUser.getRoles().get(0).getAccessPrivilege());
        assertNotNull(returnUser.getRoles().get(1).getAccessPrivilege());
        assertNotNull(returnUser.getRoles().get(2).getAccessPrivilege());
        assertNotNull(returnUser.getRoles().get(3).getAccessPrivilege());
    }
    
        @Test
    public void test5_evaluateAccessPreviledge_withExistsRoles_shouldReturnNull() {
        toUser.setRoles(new ArrayList<Role>() {
            {
                add(new Role("wizard"));
            }
        });
        assertNull(returnUser);
        returnUser = service.evaluateAccessPreviledge(toUser);
        assertNull(returnUser.getRoles().get(0).getAccessPrivilege());
    }
}
