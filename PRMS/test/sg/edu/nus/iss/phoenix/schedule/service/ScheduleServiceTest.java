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

    private ProgramSlot toCreate;
    private ProgramSlot toUpdate;
    private static ScheduleService service;

    public ScheduleServiceTest() {

    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        service = new ScheduleService();
    }

    @AfterClass
    public static void tearDownClass() {
        service = null;
    }

    @Before
    public void setUp() {
        toCreate = new ProgramSlot(LocalDateTime.parse("2000-01-01T00:00:00"), LocalTime.parse("00:10:00"), "news", "dilbert", "wally", "pointyhead");
        toUpdate = new ProgramSlot(LocalDateTime.parse("2000-01-01T00:05:00"), LocalTime.parse("00:15:00"), "news", "dilbert", "dogbert", "catbert");
    }

    @After
    public void tearDown() {
        toCreate = null;
        toUpdate = null;
    }

    @Test
    public void test01_createProgramSlot_withNotExistedKeys_shouldThrowInvalidData() throws OverlapException, DuplicateException, InvalidDataException {
        thrown.expect(InvalidDataException.class);
        toCreate.getRadioProgram().setName("spell");
        toCreate.getPresenter().setId("wizard");
        toCreate.getProducer().setId("wizard");
        toCreate.setAssignedBy("wizard");
        service.createProgramSlot(toCreate);
    }

    @Test
    public void test02_createProgramSlot_withNullKeys_shouldThrowInvalidData() throws OverlapException, DuplicateException, InvalidDataException {
        thrown.expect(InvalidDataException.class);
        toCreate.getRadioProgram().setName(null);
        toCreate.getPresenter().setId(null);
        toCreate.getProducer().setId(null);
        toCreate.setAssignedBy(null);
        service.createProgramSlot(toCreate);
    }

    @Test
    public void test03_createProgramSlot_withNewPeriod_shouldCreate() {
        try {
            service.createProgramSlot(toCreate);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void test04_createProgramSlot_withExactDateOfProgram_shouldThrowOverlap() throws OverlapException, DuplicateException, InvalidDataException {
        thrown.expect(OverlapException.class);
        service.createProgramSlot(toCreate);
    }

    @Test
    public void test05_createProgramSlot_withIntersetWithEndPeriod_shouldThrowOverlap() throws OverlapException, DuplicateException, InvalidDataException {
        toCreate.setDateOfProgram(LocalDateTime.parse("1999-12-31T23:55:00"));
        thrown.expect(OverlapException.class);
        service.createProgramSlot(toCreate);
    }

    @Test
    public void test06_createProgramSlot_withIntersetWithFrontPeriod_shouldThrowOverlap() throws OverlapException, DuplicateException, InvalidDataException {
        toCreate.setDateOfProgram(LocalDateTime.parse("2000-01-01T00:05:00"));
        thrown.expect(OverlapException.class);
        service.createProgramSlot(toCreate);
    }

    @Test
    public void test07_createProgramSlot_withIntersetWithWholePeriod_shouldThrowOverlap() throws OverlapException, DuplicateException, InvalidDataException {
        toCreate.setDateOfProgram(LocalDateTime.parse("1999-12-31T23:55:00"));
        toCreate.setDuration(LocalTime.parse("00:20:00"));
        thrown.expect(OverlapException.class);
        service.createProgramSlot(toCreate);
    }

    @Test
    public void test08_updateProgramSlot_withNotExistedKeys_shouldThrowInvalidData() throws InvalidDataException, DuplicateException, InUseException, NotFoundException, OverlapException {
        thrown.expect(InvalidDataException.class);
        toUpdate.getRadioProgram().setName("spell");
        toUpdate.getPresenter().setId("wizard");
        toUpdate.getProducer().setId("wizard");
        toUpdate.setAssignedBy("wizard");
        service.updateProgramSlot(toUpdate, new ProgramSlot(toCreate.getDateOfProgram()));
    }

    @Test
    public void test09_updateProgramSlot_withNullKeys_shouldThrowInvalidData() throws InvalidDataException, DuplicateException, InUseException, NotFoundException, OverlapException {
        thrown.expect(InvalidDataException.class);
        toUpdate.getRadioProgram().setName(null);
        toUpdate.getPresenter().setId(null);
        toUpdate.getProducer().setId(null);
        toUpdate.setAssignedBy(null);
        service.updateProgramSlot(toUpdate, new ProgramSlot(toCreate.getDateOfProgram()));
    }

    @Test
    public void test10_updateProgramSlot_withNewPeriod_shouldUpdate() {
        try {
            service.updateProgramSlot(toUpdate, new ProgramSlot(toCreate.getDateOfProgram()));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void test11_updateProgramSlot_withNotValidOrigin_shouldThrowNotFound() throws InvalidDataException, DuplicateException, InUseException, NotFoundException, OverlapException {
        thrown.expect(NotFoundException.class);
        toUpdate.setDateOfProgram(LocalDateTime.parse("2000-01-01T00:20:00"));
        service.updateProgramSlot(toUpdate, new ProgramSlot(toCreate.getDateOfProgram()));
    }

    @Test
    public void test12_updateProgramSlot_withNotValidOrigin_shouldThrowOverlap() throws InvalidDataException, DuplicateException, InUseException, NotFoundException, OverlapException {
        thrown.expect(OverlapException.class);
        service.updateProgramSlot(toCreate, new ProgramSlot(toCreate.getDateOfProgram()));
    }

    @Test
    public void test13_deleteProgramSlot_withNotValidOrigin_shouldThrowNotFound() throws NotFoundException, InUseException {
        thrown.expect(NotFoundException.class);
        service.deleteProgramSlot(new ProgramSlot(toCreate.getDateOfProgram()));
    }

    @Test
    public void test14_deleteProgramSlot_withValidOrigin_shouldDelete() {
        try {
            service.deleteProgramSlot(new ProgramSlot(toUpdate.getDateOfProgram()));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
}
