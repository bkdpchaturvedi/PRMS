///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package sg.edu.nus.iss.phoenix.schedule.service;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import sg.edu.nus.iss.phoenix.authenticate.service.*;
//import java.util.ArrayList;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
//
///**
// *
// * @author Drake
// */
public class ScheduleServiceTest {
//
//    private ProgramSlot toProgramSlot;
//    private ProgramSlot returnProgramSlot;
//    private ProgramSlot returnProgramSlots;
//    private static String username;
//    private static ScheduleService service;
//
//    public ScheduleServiceTest() {
//
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//        service = new ScheduleService();
//        username = "pointyhead";
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//        service = null;
//        username = null;
//    }
//
//    @Before
//    public void setUp() {
//        toProgramSlot = new ProgramSlot(LocalDateTime.parse("2000-01-01T00:00:00"),LocalTime.parse("00:10:00") , "news", "dillabert", producer, LocalDateTime.MINLocalDateTime.MIN);
//    }
//
//    @After
//    public void tearDown() {
//        toProgramSlot = null;
//        returnProgramSlot = null;
//    }
//
//    @Test
//    public void testValidLogin() {
//        toProgramSlot.setPassword("pointyhead");
//        assertNull(returnProgramSlot);
//        returnProgramSlot = service.validateProgramSlotIdPassword(toProgramSlot);
//        assertNotNull(returnProgramSlot);
//        assertNotNull(returnProgramSlot.getRoles());
//    }
//
//    @Test
//    public void testInvalidLogin() {
//        toProgramSlot.setPassword("test");
//        assertNull(returnProgramSlot);
//        returnProgramSlot = service.validateProgramSlotIdPassword(toProgramSlot);
//        assertNull(returnProgramSlot);
//    }
//
//    @Test
//    public void testInvalidProgramSlot() {
//        toProgramSlot.setId("ghost");
//        assertNull(returnProgramSlot);
//        returnProgramSlot = service.validateProgramSlotIdPassword(toProgramSlot);
//        assertNull(returnProgramSlot);
//    }
//
//    @Test
//    public void testEvaluateAccessPreviledge() {
//        toProgramSlot.setRoles(new ArrayList<Role>() {
//            {
//                add(new Role("manager"));
//                add(new Role("producer"));
//                add(new Role("presenter"));
//                add(new Role("admin"));
//                add(new Role("ghost"));
//            }
//        });
//        assertNull(returnProgramSlot);
//        returnProgramSlot = service.evaluateAccessPreviledge(toProgramSlot);
//        assertNotNull(returnProgramSlot.getRoles().get(0).getAccessPrivilege());
//        assertNotNull(returnProgramSlot.getRoles().get(1).getAccessPrivilege());
//        assertNotNull(returnProgramSlot.getRoles().get(2).getAccessPrivilege());
//        assertNotNull(returnProgramSlot.getRoles().get(3).getAccessPrivilege());
//        assertNull(returnProgramSlot.getRoles().get(4).getAccessPrivilege());
//    }
}
