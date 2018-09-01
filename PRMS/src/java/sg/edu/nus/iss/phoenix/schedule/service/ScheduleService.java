/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 *
 * @author MyatMin
 */
public class ScheduleService {

    /**
     *
     * @param input
     * @param username
     * @throws SQLException
     */
    public void createProgramSlot(ProgramSlot input, String username) throws SQLException {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param input
     * @throws SQLException
     */
    public void deleteProgramSlot(ProgramSlot input) throws SQLException {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param filter
     * @throws SQLException
     */
    public void findProgramSlots(LocalDate filter) throws SQLException {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @return @throws SQLException
     */
    public List<ProgramSlot> getAllProgramSlots() throws SQLException {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
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
     * @param username
     * @throws SQLException
     */
    private void populateSchedule(ProgramSlot input, String username) throws SQLException {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param input
     * @throws SQLException
     */
    public void updateProgramSlot(ProgramSlot input) throws SQLException {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
