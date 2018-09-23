/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.phoenix.schedule.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
import sg.edu.nus.iss.phoenix.schedule.dao.impl.ProgramSlotDAOImpl;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.exceptions.OverlapException;

/**
 *
 * @author MyatMin
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProgramSlotDAOTest {

    private ProgramSlot toCreate;
    private ProgramSlot toUpdate;
    private static ProgramSlotDAO programSlotDAO;

    public ProgramSlotDAOTest() {

    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        programSlotDAO = new ProgramSlotDAOImpl();
    }

    @AfterClass
    public static void tearDownClass() {
        programSlotDAO = null;
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
    public void test01_create_withNotExistedKeys_shouldThrowInvalidData() throws DuplicateException, InvalidDataException, SQLException {
        thrown.expect(InvalidDataException.class);
        toCreate.getRadioProgram().setName("spell");
        toCreate.getPresenter().setId("wizard");
        toCreate.getProducer().setId("wizard");
        toCreate.setAssignedBy("wizard");
        programSlotDAO.create(toCreate);
    }

    @Test
    public void test02_create_withNullKeys_shouldThrowInvalidData() throws DuplicateException, InvalidDataException, SQLException {
        thrown.expect(InvalidDataException.class);
        toCreate.getRadioProgram().setName(null);
        toCreate.getPresenter().setId(null);
        toCreate.getProducer().setId(null);
        toCreate.setAssignedBy(null);
        programSlotDAO.create(toCreate);
    }

    @Test
    public void test03_create_withNewPeriod_shouldCreate() {
        try {
            programSlotDAO.create(toCreate);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test04_get_withExistedDateOfProgram_shouldGet() {
        try {
            ProgramSlot result = programSlotDAO.get(toCreate.getDateOfProgram());
            assertNotNull(result);
            assertEquals(toCreate.getRadioProgram().getName(), result.getRadioProgram().getName());
            assertEquals(toCreate.getPresenter().getId(), result.getPresenter().getId());
            assertEquals(toCreate.getProducer().getId(), result.getProducer().getId());
            assertEquals(toCreate.getAssignedBy(), result.getAssignedBy());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test05_get_withNotExistedDateOfProgram__shouldThrowNotFound() throws SQLException, NotFoundException {
        thrown.expect(NotFoundException.class);
        programSlotDAO.get(toUpdate.getDateOfProgram());
    }
    
    @Test
    public void test06_load_withExistedDateOfProgram_shouldLoad() {
        try {
            ProgramSlot result = new ProgramSlot(toCreate.getDateOfProgram());
            programSlotDAO.load(result);
            assertEquals(toCreate.getRadioProgram().getName(), result.getRadioProgram().getName());
            assertEquals(toCreate.getPresenter().getId(), result.getPresenter().getId());
            assertEquals(toCreate.getProducer().getId(), result.getProducer().getId());
            assertEquals(toCreate.getAssignedBy(), result.getAssignedBy());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test07_load_withNotExistedDateOfProgram__shouldThrowNotFound() throws SQLException, NotFoundException {
        thrown.expect(NotFoundException.class);
        ProgramSlot result = new ProgramSlot(toUpdate.getDateOfProgram());
        programSlotDAO.load(result);
    }
    
    @Test
    public void test08_search_withEsixtedDate_shouldFoundOne() {
        try {
            List<ProgramSlot> result = programSlotDAO.search(new ProgramSlot(toCreate.getDateOfProgram()), ProgramSlotDAO.DateRangeFilter.BY_DATE, ProgramSlotDAO.FieldsOpreation.AND);
            assertEquals(1, result.size());
            assertEquals(toCreate.getRadioProgram().getName(), result.get(0).getRadioProgram().getName());
            assertEquals(toCreate.getPresenter().getId(), result.get(0).getPresenter().getId());
            assertEquals(toCreate.getProducer().getId(), result.get(0).getProducer().getId());
            assertEquals(toCreate.getAssignedBy(), result.get(0).getAssignedBy());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test09_search_withNonEsixtedDate_shouldFoundNone() {
        try {;
            List<ProgramSlot> result = programSlotDAO.search(new ProgramSlot(LocalDateTime.parse("1999-12-31T23:55:00")), ProgramSlotDAO.DateRangeFilter.BY_DATE, ProgramSlotDAO.FieldsOpreation.AND);
            assertEquals(0, result.size());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test10_checkExisitCount_withDateRagePresenterProducerOpreatorOR_shouldFoundOne() {
        try {
            ProgramSlot searchProgramSlot = new ProgramSlot(toCreate.getDateOfProgram());
            searchProgramSlot.setPresenter(toCreate.getPresenter());
            searchProgramSlot.setProducer(toCreate.getPresenter());
            Integer result = programSlotDAO.checkExistCount(searchProgramSlot, ProgramSlotDAO.DateRangeFilter.BY_DATE, ProgramSlotDAO.FieldsOpreation.OR);
            assertTrue(1 == result);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test11_checkExisitCount_withDateRagePresenterProducerOpreatorAND_shouldFoundNone() {
        try {
            ProgramSlot searchProgramSlot = new ProgramSlot(toCreate.getDateOfProgram());
            searchProgramSlot.setPresenter(toCreate.getPresenter());
            searchProgramSlot.setProducer(toCreate.getPresenter());
            Integer result = programSlotDAO.checkExistCount(searchProgramSlot, ProgramSlotDAO.DateRangeFilter.BY_DATE, ProgramSlotDAO.FieldsOpreation.AND);
            assertTrue(0 == result);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test12_checkOverlap_withExactDateOfProgram_shouldOverlap() throws SQLException {
        try {
            assertEquals(true, programSlotDAO.checkOverlap(toCreate));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
   @Test
    public void test13_checkOverlap_withIntersetWithEndPeriod_shouldOverlap() throws SQLException {
        try {
            toCreate.setDateOfProgram(LocalDateTime.parse("1999-12-31T23:55:00"));
            assertEquals(true, programSlotDAO.checkOverlap(toCreate));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
   @Test
    public void test14_checkOverlap_withIntersetWithFrontPeriod_shouldOverlap() throws SQLException {
        try {
            toCreate.setDateOfProgram(LocalDateTime.parse("2000-01-01T00:05:00"));
            assertEquals(true, programSlotDAO.checkOverlap(toCreate));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test15_checkOverlap_withIntersetWithWholePeriod_shouldOverlap() throws SQLException {
        try {
            toCreate.setDateOfProgram(LocalDateTime.parse("1999-12-31T23:55:00"));
        toCreate.setDuration(LocalTime.parse("00:20:00"));
            assertEquals(true, programSlotDAO.checkOverlap(toCreate));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test16_checkOverlap_withEarlierPeriod_shouldNotOverlap() throws SQLException {
        try {
            toCreate.setDateOfProgram(LocalDateTime.parse("1999-12-31T23:55:00"));
        toCreate.setDuration(LocalTime.parse("00:05:00"));
            assertEquals(false, programSlotDAO.checkOverlap(toCreate));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
        
    @Test
    public void test17_checkOverlap_withLatererPeriod_shouldNotOverlap() throws SQLException {
        try {
            toCreate.setDateOfProgram(LocalDateTime.parse("2000-01-01T00:10:00"));
        toCreate.setDuration(LocalTime.parse("00:05:00"));
            assertEquals(false, programSlotDAO.checkOverlap(toCreate));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test18_checkOverlap_withIntersetWithEndPeriodForSameOrigin_shouldNotOverlap() throws SQLException {
        try {
            LocalDateTime origin = toCreate.getDateOfProgram();
            toCreate.setDateOfProgram(LocalDateTime.parse("1999-12-31T23:55:00"));
            assertEquals(false, programSlotDAO.checkOverlap(toCreate, origin));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
   @Test
    public void test19_checkOverlap_withIntersetWithFrontPeriodForSameOrigin_shouldNotOverlap() throws SQLException {
        try {
            LocalDateTime origin = toCreate.getDateOfProgram();
            toCreate.setDateOfProgram(LocalDateTime.parse("2000-01-01T00:05:00"));
            assertEquals(false, programSlotDAO.checkOverlap(toCreate, origin));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test20_checkOverlap_withIntersetWithWholePeriodForSameOrigin_shouldNotOverlap() throws SQLException {
        try {
            LocalDateTime origin = toCreate.getDateOfProgram();
            toCreate.setDateOfProgram(LocalDateTime.parse("1999-12-31T23:55:00"));
        toCreate.setDuration(LocalTime.parse("00:20:00"));
            assertEquals(false, programSlotDAO.checkOverlap(toCreate, origin));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void test21_create_withExactDateOfProgram_shouldThrowDuplicate() throws DuplicateException, InvalidDataException, SQLException {
        thrown.expect(DuplicateException.class);
        programSlotDAO.create(toCreate);
    }

    @Test
    public void test22_update_withNotExistedKeys_shouldThrowInvalidData() throws InvalidDataException, DuplicateException, InUseException, NotFoundException, SQLException {
        thrown.expect(InvalidDataException.class);
        toUpdate.getRadioProgram().setName("spell");
        toUpdate.getPresenter().setId("wizard");
        toUpdate.getProducer().setId("wizard");
        toUpdate.setAssignedBy("wizard");
        programSlotDAO.update(toUpdate, toCreate.getDateOfProgram());
    }

    @Test
    public void test23_update_withNullKeys_shouldThrowInvalidData() throws InvalidDataException, DuplicateException, InUseException, NotFoundException, SQLException {
        thrown.expect(InvalidDataException.class);
        toUpdate.getRadioProgram().setName(null);
        toUpdate.getPresenter().setId(null);
        toUpdate.getProducer().setId(null);
        toUpdate.setAssignedBy(null);
        programSlotDAO.update(toUpdate, toCreate.getDateOfProgram());
    }

    @Test
    public void test24_update_withNewPeriod_shouldUpdate() {
        try {
            programSlotDAO.update(toUpdate, toCreate.getDateOfProgram());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void test25_update_withNotValidOrigin_shouldThrowNotFound() throws InvalidDataException, DuplicateException, InUseException, NotFoundException, OverlapException, SQLException {
        thrown.expect(NotFoundException.class);
        toUpdate.setDateOfProgram(LocalDateTime.parse("2000-01-01T00:20:00"));
        programSlotDAO.update(toUpdate, toCreate.getDateOfProgram());
    }

    @Test
    public void test26_delete_withNotValidOrigin_shouldThrowNotFound() throws NotFoundException, InUseException, SQLException {
        thrown.expect(NotFoundException.class);
        programSlotDAO.delete(toCreate.getDateOfProgram());
    }

    @Test
    public void test27_delete_withValidOrigin_shouldDelete() {
        try {
            programSlotDAO.delete(toUpdate.getDateOfProgram());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
}
