/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.exceptions.OverlapException;

/**
 *
 * @author MyatMin
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScheduleServiceTest {

    private ProgramSlot toProgramSlot;
    private static String username;
    private static ScheduleService service;

    public ScheduleServiceTest() {

    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        service = new ScheduleService();
        username = "pointyhead";
    }

    @AfterClass
    public static void tearDownClass() {
        service = null;
        username = null;
    }

    @Before
    public void setUp() {
        toProgramSlot = new ProgramSlot(LocalDateTime.parse("2000-01-01T00:00:00"), LocalTime.parse("00:10:00"), "news", "dilbert", "wally", username);
    }

    @After
    public void tearDown() {
        toProgramSlot = null;
    }

    @Test
    public void test3_createProgramSlot_withNewPeriod_shouldCreate() {
        try {
            service.createProgramSlot(toProgramSlot);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void test4_overlapProgramSlot_withOverlapPeriod_shouldThrowOverlap() throws OverlapException, NotFoundException, DuplicateException {
        thrown.expect(OverlapException.class);
        service.createProgramSlot(toProgramSlot);
    }

    @Test
    public void test1_createProgramSlot_withNotExistedKeys_shouldThrowNotFound() throws OverlapException, NotFoundException, DuplicateException {
        thrown.expect(NotFoundException.class);
        toProgramSlot.getRadioProgram().setName("spell");
        toProgramSlot.getPresenter().setId("wizard");
        toProgramSlot.getProducer().setId("wizard");
        service.createProgramSlot(toProgramSlot);
    }

    @Test
    public void test2_createProgramSlot_withNullKeys_shouldThrowNotFound() throws OverlapException, NotFoundException, DuplicateException {
        thrown.expect(NotFoundException.class);
        toProgramSlot.getRadioProgram().setName(null);
        toProgramSlot.getPresenter().setId(null);
        toProgramSlot.getProducer().setId(null);
        service.createProgramSlot(toProgramSlot);
    }

}
