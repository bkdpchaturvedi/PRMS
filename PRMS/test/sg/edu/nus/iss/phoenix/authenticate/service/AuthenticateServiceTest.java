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
    public void testValidLogin() {
        toUser.setPassword("pointyhead");
        assertNull(returnUser);
        returnUser = service.validateUserIdPassword(toUser);
        assertNotNull(returnUser);
        assertNotNull(returnUser.getRoles());
    }

    @Test
    public void testInvalidLogin() {
        toUser.setPassword("spell");
        assertNull(returnUser);
        returnUser = service.validateUserIdPassword(toUser);
        assertNull(returnUser);
    }

    @Test
    public void testInvalidUser() {
        toUser.setId("wizard");
        assertNull(returnUser);
        returnUser = service.validateUserIdPassword(toUser);
        assertNull(returnUser);
    }

    @Test
    public void testEvaluateAccessPreviledge() {
        toUser.setRoles(new ArrayList<Role>() {
            {
                add(new Role("manager"));
                add(new Role("producer"));
                add(new Role("presenter"));
                add(new Role("admin"));
                add(new Role("wizard"));
            }
        });
        assertNull(returnUser);
        returnUser = service.evaluateAccessPreviledge(toUser);
        assertNotNull(returnUser.getRoles().get(0).getAccessPrivilege());
        assertNotNull(returnUser.getRoles().get(1).getAccessPrivilege());
        assertNotNull(returnUser.getRoles().get(2).getAccessPrivilege());
        assertNotNull(returnUser.getRoles().get(3).getAccessPrivilege());
        assertNull(returnUser.getRoles().get(4).getAccessPrivilege());
    }
}
