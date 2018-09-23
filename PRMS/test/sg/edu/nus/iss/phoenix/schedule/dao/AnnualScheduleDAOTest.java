/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.phoenix.schedule.dao;

import java.sql.SQLException;
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
import sg.edu.nus.iss.phoenix.schedule.dao.impl.AnnualScheduleDAOImpl;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;

/**
 *
 * @author MyatMin
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AnnualScheduleDAOTest {

    private AnnualSchedule toCreate;
    private static AnnualScheduleDAO annualScheduleDAO;

    public AnnualScheduleDAOTest() {

    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpClass() {
        annualScheduleDAO = new AnnualScheduleDAOImpl();
    }

    @AfterClass
    public static void tearDownClass() {
        annualScheduleDAO = null;
    }

    @Before
    public void setUp() {
        toCreate = new AnnualSchedule(1999, "pointyhead");
    }

    @After
    public void tearDown() {
        toCreate = null;
    }

    @Test
    public void test01_create_withNotExistedKeys_shouldThrowInvalidData() throws DuplicateException, InvalidDataException, SQLException {
        thrown.expect(InvalidDataException.class);
        toCreate.setAssignedBy("wizard");
        annualScheduleDAO.create(toCreate);
    }

    @Test
    public void test02_create_withNullKeys_shouldThrowInvalidData() throws DuplicateException, InvalidDataException, SQLException {
        thrown.expect(InvalidDataException.class);
        toCreate.setAssignedBy(null);
        annualScheduleDAO.create(toCreate);
    }

    @Test
    public void test03_create_withNewYear_shouldCreate() {
        try {
            annualScheduleDAO.create(toCreate);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test04_get_withExistedYear_shouldGet() {
        try {
            AnnualSchedule result = annualScheduleDAO.get(toCreate.getYear());
            assertNotNull(result);
            assertEquals(toCreate.getYear(), result.getYear());
            assertEquals(toCreate.getAssignedBy(), result.getAssignedBy());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test05_get_withNotExistedYear_shouldThrowNotFound() throws SQLException, NotFoundException {
        thrown.expect(NotFoundException.class);
        annualScheduleDAO.get(2000);
    }
    
    @Test
    public void test06_load_withExistedYear_shouldLoad() {
        try {
            AnnualSchedule result = new AnnualSchedule(toCreate.getYear());
            annualScheduleDAO.load(result);
            assertEquals(toCreate.getYear(), result.getYear());
            assertEquals(toCreate.getAssignedBy(), result.getAssignedBy());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
    public void test07_load_withNotExistedYear__shouldThrowNotFound() throws SQLException, NotFoundException {
        thrown.expect(NotFoundException.class);
        AnnualSchedule result = new AnnualSchedule(2000);
        annualScheduleDAO.load(result);
    }
    
    @Test
    public void test08_create_withExactYear_shouldThrowDuplicate() throws DuplicateException, InvalidDataException, SQLException {
        thrown.expect(DuplicateException.class);
        annualScheduleDAO.create(toCreate);
    }

    @Test
    public void test09_delete_withNotValidOrigin_shouldThrowNotFound() throws NotFoundException, InUseException, SQLException {
        thrown.expect(NotFoundException.class);
        annualScheduleDAO.delete(2000);
    }

    @Test
    public void test10_delete_withValidOrigin_shouldDelete() {
        try {
            annualScheduleDAO.delete(toCreate.getYear());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
}
