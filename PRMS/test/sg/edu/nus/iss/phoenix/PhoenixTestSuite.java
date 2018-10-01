/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import sg.edu.nus.iss.phoenix.authenticate.service.AuthenticateServiceTest;
import sg.edu.nus.iss.phoenix.schedule.dao.AnnualScheduleDAOTest;
import sg.edu.nus.iss.phoenix.schedule.dao.ProgramSlotDAOTest;
import sg.edu.nus.iss.phoenix.schedule.dao.WeeklyScheduleDAOTest;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleServiceTest;
import sg.edu.nus.iss.phoenix.user.dao.UserDAOTest;
import sg.edu.nus.iss.phoenix.user.service.UserServiceTest;

/**
 *
 * @author chaturbkdp
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AuthenticateServiceTest.class,AnnualScheduleDAOTest.class,ProgramSlotDAOTest.class,WeeklyScheduleDAOTest.class,ScheduleServiceTest.class,UserDAOTest.class,UserServiceTest.class})
public class PhoenixTestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
