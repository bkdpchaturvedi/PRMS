/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.authenticate.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 *
 * @author Drake
 */
public class AuthenticateServiceTest {
    
    private User loginUser;
    private User authenticatedUser;
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
        loginUser = new User();
        loginUser.setId("pointyhead");
    }
    
    @After
    public void tearDown() {
        loginUser = null;
        authenticatedUser = null;
    }
    
    @Test
    public void testValidLogin() {
        loginUser.setPassword("pointyhead");
        authenticatedUser = service.validateUserIdPassword(loginUser);
        assertNotNull(authenticatedUser);
        assertNotNull(authenticatedUser.getRoles());
    }
    
    @Test
    public void testInvalidLogin() {
        loginUser.setPassword("test");
        authenticatedUser = service.validateUserIdPassword(loginUser);
        assertNull(authenticatedUser);
    }
    
    @Test
    public void testInvalidUser() {
        loginUser.setId("ghost");
        authenticatedUser = service.validateUserIdPassword(loginUser);
        assertNull(authenticatedUser);
    }
}
