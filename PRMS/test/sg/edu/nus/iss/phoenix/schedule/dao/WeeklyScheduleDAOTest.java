/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.phoenix.schedule.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import sg.edu.nus.iss.phoenix.schedule.dao.impl.WeeklyScheduleDAOImpl;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;

/**
 *
 * @author MyatMin
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WeeklyScheduleDAOTest {

    private WeeklySchedule toCreate;
    private static WeeklyScheduleDAO weeklyScheduleDAO;

    public WeeklyScheduleDAOTest() {

    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        weeklyScheduleDAO = new WeeklyScheduleDAOImpl();
    }

    @AfterClass
    public static void tearDownClass() {
        weeklyScheduleDAO = null;
    }

    @Before
    public void setUp() {
        toCreate = new WeeklySchedule(LocalDate.parse("1999-12-24"), "pointyhead");
    }

    @After
    public void tearDown() {
        toCreate = null;
    }

    @Test
    public void test01_create_withNotExistedKeys_shouldThrowInvalidData() throws DuplicateException, InvalidDataException, SQLException {
        thrown.expect(InvalidDataException.class);
        toCreate.setAssignedBy("wizard");
        weeklyScheduleDAO.create(toCreate);
    }

    @Test
    public void test02_create_withNullKeys_shouldThrowInvalidData() throws DuplicateException, InvalidDataException, SQLException {
        thrown.expect(InvalidDataException.class);
        toCreate.setAssignedBy(null);
        weeklyScheduleDAO.create(toCreate);
    }

    @Test
    public void test03_create_withNewStartDate_shouldCreate() {
        try {
            weeklyScheduleDAO.create(toCreate);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test04_get_withExistedStartDate_shouldGet() {
        try {
            WeeklySchedule result = weeklyScheduleDAO.get(toCreate.getStartDate());
            assertNotNull(result);
            assertEquals(toCreate.getStartDate(), result.getStartDate());
            assertEquals(toCreate.getAssignedBy(), result.getAssignedBy());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test05_get_withNotExistedStartDate_shouldThrowNotFound() throws SQLException, NotFoundException {
        thrown.expect(NotFoundException.class);
        weeklyScheduleDAO.get(LocalDate.parse("2000-01-04"));
    }
    
    @Test
    public void test06_load_withExistedStartDate_shouldLoad() {
        try {
            WeeklySchedule result = new WeeklySchedule(toCreate.getStartDate());
            weeklyScheduleDAO.load(result);
            assertEquals(toCreate.getStartDate(), result.getStartDate());
            assertEquals(toCreate.getAssignedBy(), result.getAssignedBy());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test07_load_withNotExistedStartDate__shouldThrowNotFound() throws SQLException, NotFoundException {
        thrown.expect(NotFoundException.class);
        WeeklySchedule result = new WeeklySchedule(LocalDate.parse("2000-01-04"));
        weeklyScheduleDAO.load(result);
    }
    
    @Test
    public void test08_create_withExactStartDate_shouldThrowDuplicate() throws DuplicateException, InvalidDataException, SQLException {
        thrown.expect(DuplicateException.class);
        weeklyScheduleDAO.create(toCreate);
    }

    @Test
    public void test09_delete_withNotValidOrigin_shouldThrowNotFound() throws NotFoundException, InUseException, SQLException {
        thrown.expect(NotFoundException.class);
        weeklyScheduleDAO.delete(LocalDate.parse("2000-01-04"));
    }

    @Test
    public void test10_delete_withValidOrigin_shouldDelete() {
        try {
            weeklyScheduleDAO.delete(toCreate.getStartDate());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
}
