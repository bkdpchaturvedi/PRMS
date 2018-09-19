/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactory;
import sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.core.exceptions.ServiceException;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.exceptions.OverlapException;

/**
 *
 * @author MyatMin
 */
public class ScheduleService {

    private static final Logger LOG = Logger.getLogger(ScheduleService.class.getName());
    
    /**
     * Creates representation of an instance of resource to persistent data
     * table
     *
     * @param input
     * @throws SQLException
     */
    public void createProgramSlot(ProgramSlot input) throws OverlapException, NotFoundException, DuplicateException {
        try {
            if (DAOFactory.getProgramSlotDAO().checkOverlap(input)) {
                throw new OverlapException("Record exists on the given date time and duration.");
            }
            DAOFactory.getProgramSlotDAO().create(input);
        }
        catch (SQLIntegrityConstraintViolationException e) {
            throw new NotFoundException("Cannot be created without valid primary key.");
        }
        catch (SQLException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    /**
     * Deletes representation of an instance of resource to persistent data
     * table
     *
     * @param input
     * @throws SQLException
     */
    public void deleteProgramSlot(ProgramSlot input) throws SQLException {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves representation of an instance of resource from persistent data
     * table
     *
     * @param dateOfProgram
     * @throws SQLException
     */
    public List<ProgramSlot> findProgramSlots(LocalDateTime dateOfProgram) {
        ArrayList<ProgramSlot> currentList = new ArrayList<ProgramSlot>();
        try {
            ProgramSlot srchProgramSlot = DAOFactory.getProgramSlotDAO().createValueObject();
            srchProgramSlot.setDateOfProgram(dateOfProgram);
            ;
            currentList = (ArrayList<ProgramSlot>) DAOFactory.getProgramSlotDAO()
                    .search(srchProgramSlot);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return currentList;
    }

    /**
     * Retrieves representation of all instance of resource from persistent data
     * table
     *
     * @return @throws SQLException
     */
    public List<ProgramSlot> getAllProgramSlots() {
        //TODO return proper representation object
        ArrayList<ProgramSlot> currentList = new ArrayList<ProgramSlot>();
        try {
            currentList = (ArrayList<ProgramSlot>) DAOFactory.getProgramSlotDAO()
                    .loadAll();
        } catch (SQLException | NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return currentList;
    }

    /**
     * Retrieves representation of an instance of resource from persistent data
     * table
     *
     * @return
     */
    public ProgramSlot getProgramSlot(LocalDateTime dateOfProgram) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param input
     * @throws SQLException
     */
    private void populateSchedule(ProgramSlot input) throws SQLException {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * Updates representation of an instance of resource to persistent data
     * table
     *
     * @param input
     * @throws SQLException
     */
    public void updateProgramSlot(ProgramSlot input) throws SQLException {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
