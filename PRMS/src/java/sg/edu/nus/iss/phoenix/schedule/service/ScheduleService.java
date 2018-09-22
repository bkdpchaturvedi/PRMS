/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactory;
import sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException;
import sg.edu.nus.iss.phoenix.core.exceptions.InUseException;
import sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.core.exceptions.ServiceException;
import sg.edu.nus.iss.phoenix.schedule.dao.ProgramSlotDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;
import sg.edu.nus.iss.phoenix.schedule.exceptions.OverlapException;
import sg.edu.nus.iss.phoenix.utilities.DateHelper;

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
     * @throws sg.edu.nus.iss.phoenix.schedule.exceptions.OverlapException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException
     */
    public void createProgramSlot(ProgramSlot input)
            throws OverlapException, InvalidDataException, DuplicateException {
        try {
            populateSchedule(input);
            if (DAOFactory.getProgramSlotDAO().checkOverlap(input)) {
                throw new OverlapException("Other Program Slot object exists on the given date time and duration.");
            }
            DAOFactory.getProgramSlotDAO().create(input);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, null, e);
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
    public void deleteProgramSlot(ProgramSlot input) throws NotFoundException, InUseException {
         try {
            DAOFactory.getProgramSlotDAO().delete(input);
            depopulateSchedule(input);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, null, e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Retrieves representation of an instance of resource from persistent data
     * table
     *
     * @param dateOfProgram
     * @return
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
     * @return
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
     * Creating the relative representation of an instance of resources to
     * persistent data table
     *
     * @param input
     * @throws SQLException
     */
    private void populateSchedule(ProgramSlot input) throws DuplicateException, InvalidDataException {
        try {
            DAOFactory.getAnnualScheduleDAO().get(
                    input.getDateOfProgram().get(WeekFields.ISO.weekBasedYear())
            );
        } catch (NotFoundException e) {
            try {
                DAOFactory.getAnnualScheduleDAO().create(
                        new AnnualSchedule(
                                DateHelper.getWeekYear(input.getDateOfProgram().toLocalDate()),
                                input.getAssignedBy()
                        )
                );
                for (int i = 1; i <= 52; i++) {
                    DAOFactory.getWeeklyScheduleDAO().create(
                            new WeeklySchedule(
                                    DateHelper.getWeekStartDate(input.getDateOfProgram().toLocalDate(), i),
                                    input.getAssignedBy()
                            )
                    );
                }
            } catch (SQLException e1) {
                LOG.log(Level.SEVERE, null, e1);
                throw new ServiceException(e.getMessage(), e);
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, null, e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Deleting the relative representation of an instance of resources to
     * persistent data table
     *
     * @param input
     * @throws SQLException
     */
    private void depopulateSchedule(ProgramSlot input) {
        try {
            Integer count = DAOFactory.getProgramSlotDAO().checkExisitCount(input.getDateOfProgram(), ProgramSlotDAO.DateRangeFilter.BY_YEAR);
            if (count == 0) {
                for (int i = 1; i <= 52; i++) {
                    try {
                        DAOFactory.getWeeklyScheduleDAO().delete(
                                new WeeklySchedule(
                                        DateHelper.getWeekStartDate(input.getDateOfProgram().toLocalDate(), i)
                                )
                        );
                    } catch (NotFoundException e) {
                        LOG.log(Level.INFO, e.getMessage());
                    }
                }
                
                try {
                    DAOFactory.getAnnualScheduleDAO().delete(new AnnualSchedule(DateHelper.getWeekYear(input.getDateOfProgram().toLocalDate())));
                } catch (NotFoundException e) {
                    LOG.log(Level.INFO, e.getMessage());
                }
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, null, e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Updates representation of an instance of resource to persistent data
     * table
     *
     * @param input
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InUseException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws sg.edu.nus.iss.phoenix.schedule.exceptions.OverlapException
     */
    public void updateProgramSlot(ProgramSlot input, ProgramSlot origin) throws InvalidDataException, DuplicateException, InUseException, NotFoundException, OverlapException {
        try {
            populateSchedule(input);
            if (DAOFactory.getProgramSlotDAO().checkOverlap(input, origin)) {
                throw new OverlapException("Other Program Slot object exists on the given date time and duration.");
            }
            DAOFactory.getProgramSlotDAO().update(input, origin);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, null, e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
