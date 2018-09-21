/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.SQLException;
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
import sg.edu.nus.iss.phoenix.core.exceptions.InUseException;
import sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException;
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
    public void test01_createProgramSlot_withNotExistedKeys_shouldThrowInvalidData() throws OverlapException, DuplicateException, InvalidDataException {
        thrown.expect(InvalidDataException.class);
        toProgramSlot.getRadioProgram().setName("spell");
        toProgramSlot.getPresenter().setId("wizard");
        toProgramSlot.getProducer().setId("wizard");
        toProgramSlot.setAssignedBy("wizard");
        service.createProgramSlot(toProgramSlot);
    }

    @Test
    public void test02_createProgramSlot_withNullKeys_shouldThrowInvalidData() throws OverlapException, DuplicateException, InvalidDataException {
        thrown.expect(InvalidDataException.class);
        toProgramSlot.getRadioProgram().setName(null);
        toProgramSlot.getPresenter().setId(null);
        toProgramSlot.getProducer().setId(null);
        toProgramSlot.setAssignedBy(null);
        service.createProgramSlot(toProgramSlot);
    }

    @Test
    public void test03_createProgramSlot_withNewPeriod_shouldCreate() {
        try {
            service.createProgramSlot(toProgramSlot);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void test04_createProgramSlot_withExactDateOfProgram_shouldThrowOverlap() throws OverlapException, DuplicateException, InvalidDataException {
        thrown.expect(OverlapException.class);
        service.createProgramSlot(toProgramSlot);
    }

    @Test
    public void test05_createProgramSlot_withIntersetWithEndPeriod_shouldThrowOverlap() throws OverlapException, DuplicateException, InvalidDataException {
        toProgramSlot.setDateOfProgram(LocalDateTime.parse("1999-12-31T23:55:00"));
        thrown.expect(OverlapException.class);
        service.createProgramSlot(toProgramSlot);
    }

    @Test
    public void test06_createProgramSlot_withIntersetWithFrontPeriod_shouldThrowOverlap() throws OverlapException, DuplicateException, InvalidDataException {
        toProgramSlot.setDateOfProgram(LocalDateTime.parse("2000-01-01T00:05:00"));
        thrown.expect(OverlapException.class);
        service.createProgramSlot(toProgramSlot);
    }

    @Test
    public void test07_createProgramSlot_withIntersetWithWholePeriod_shouldThrowOverlap() throws OverlapException, DuplicateException, InvalidDataException {
        toProgramSlot.setDateOfProgram(LocalDateTime.parse("1999-12-31T23:55:00"));
        toProgramSlot.setDuration(LocalTime.parse("00:20:00"));
        thrown.expect(OverlapException.class);
        service.createProgramSlot(toProgramSlot);
    }
    
    @Test
    public void test08_updateProgramSlot_withNotExistedKeys_shouldThrowInvalidData() throws InvalidDataException, DuplicateException, InUseException, NotFoundException, OverlapException{
        thrown.expect(InvalidDataException.class);
        ProgramSlot origin = new ProgramSlot(LocalDateTime.parse("2000-01-01T00:00:00"));
        toProgramSlot.setDateOfProgram(LocalDateTime.parse("2000-01-01T00:05:00"));
        toProgramSlot.getRadioProgram().setName("spell");
        toProgramSlot.getPresenter().setId("wizard");
        toProgramSlot.getProducer().setId("wizard");
        toProgramSlot.setAssignedBy("wizard");
        service.updateProgramSlot(toProgramSlot, origin);
    }
    
    @Test
    public void test09_updateProgramSlot_withNullKeys_shouldThrowInvalidData() throws InvalidDataException, DuplicateException, InUseException, NotFoundException, OverlapException{
        thrown.expect(InvalidDataException.class);
        ProgramSlot origin = new ProgramSlot(LocalDateTime.parse("2000-01-01T00:00:00"));
        toProgramSlot.setDateOfProgram(LocalDateTime.parse("2000-01-01T00:05:00"));
        toProgramSlot.getRadioProgram().setName(null);
        toProgramSlot.getPresenter().setId(null);
        toProgramSlot.getProducer().setId(null);
        toProgramSlot.setAssignedBy(null);
        service.updateProgramSlot(toProgramSlot, origin);
    }
    
    @Test
    public void test10_updateProgramSlot_withNewPeriod_shouldUpdate() {
        ProgramSlot origin = new ProgramSlot(toProgramSlot.getDateOfProgram());
        toProgramSlot.setDateOfProgram(LocalDateTime.parse("2000-01-01T00:05:00"));
        try {
            service.updateProgramSlot(toProgramSlot, origin);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test11_updateProgramSlot_withNotValidOrigin_shouldThrowNotFound() throws InvalidDataException, DuplicateException, InUseException, NotFoundException, OverlapException{
        ProgramSlot origin = new ProgramSlot(LocalDateTime.parse("2000-01-01T00:20:00"));
        toProgramSlot.setDateOfProgram(LocalDateTime.parse("2000-01-01T00:20:00"));
        thrown.expect(NotFoundException.class);
        service.updateProgramSlot(toProgramSlot, origin);
    }
    
    @Test
    public void test12_updateProgramSlot_withNotValidOrigin_shouldThrowOverlap() throws InvalidDataException, DuplicateException, InUseException, NotFoundException, OverlapException{
        ProgramSlot origin = new ProgramSlot(LocalDateTime.parse("2000-01-01T00:00:00"));
        thrown.expect(OverlapException.class);
        service.updateProgramSlot(toProgramSlot, origin);
    }
}