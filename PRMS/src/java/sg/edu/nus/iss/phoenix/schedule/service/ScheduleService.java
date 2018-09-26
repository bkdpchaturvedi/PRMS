/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.sql.SQLException;
import java.time.ZonedDateTime;
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
     * @param dateOfProgram
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InUseException
     */
    public void deleteProgramSlot(ZonedDateTime dateOfProgram) throws NotFoundException, InUseException {
         try {
            DAOFactory.getProgramSlotDAO().delete(dateOfProgram);
            depopulateSchedule(dateOfProgram);
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
     */
    public List<ProgramSlot> findProgramSlots(ZonedDateTime dateOfProgram) {
        ArrayList<ProgramSlot> result = new ArrayList<ProgramSlot>();
        try {
            ProgramSlot input = DAOFactory.getProgramSlotDAO().createValueObject();
            input.setDateOfProgram(dateOfProgram);
            result = (ArrayList<ProgramSlot>) DAOFactory.getProgramSlotDAO()
                    .search(input
                            , ProgramSlotDAO.DateRangeFilter.BY_DATE
                            , ProgramSlotDAO.FieldsOpreation.AND);
            for (ProgramSlot programSlot : result) {
                DAOFactory.getUserDAO().load(programSlot.getPresenter());
                DAOFactory.getUserDAO().load(programSlot.getProducer());
            }
        } catch (SQLException | NotFoundException e) {
            LOG.log(Level.SEVERE, null, e);
            throw new ServiceException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Retrieves representation of all instance of resource from persistent data
     * table
     *
     * @return
     */
    public List<ProgramSlot> getAllProgramSlots() {
        ArrayList<ProgramSlot> result = new ArrayList<ProgramSlot>();
        try {
            result = (ArrayList<ProgramSlot>) DAOFactory.getProgramSlotDAO()
                    .loadAll();
            for (ProgramSlot programSlot : result) {
                DAOFactory.getProgramDAO().load(programSlot.getRadioProgram());
                DAOFactory.getUserDAO().load(programSlot.getPresenter());
                DAOFactory.getUserDAO().load(programSlot.getProducer());
            }
        } catch (SQLException | NotFoundException e) {
            LOG.log(Level.SEVERE, null, e);
            throw new ServiceException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Retrieves representation of an instance of resource from persistent data
     * table
     *
     * @param dateOfProgram
     * @return
     */
    public ProgramSlot getProgramSlot(ZonedDateTime dateOfProgram) throws NotFoundException {
        try {
            ProgramSlot result = DAOFactory.getProgramSlotDAO().get(dateOfProgram);
            DAOFactory.getUserDAO().load(result.getPresenter());
            DAOFactory.getUserDAO().load(result.getProducer());
            return result;
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
     * @param origin
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InUseException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws sg.edu.nus.iss.phoenix.schedule.exceptions.OverlapException
     */
    public void updateProgramSlot(ProgramSlot input, ZonedDateTime origin) throws InvalidDataException, DuplicateException, InUseException, NotFoundException, OverlapException {
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
                throw new ServiceException(e1.getMessage(), e1);
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
    private void depopulateSchedule(ZonedDateTime dateOfProgram) {
        try {
            Integer count = DAOFactory.getProgramSlotDAO().checkExistCount(
                    new ProgramSlot(dateOfProgram)
                    , ProgramSlotDAO.DateRangeFilter.BY_YEAR
                    , ProgramSlotDAO.FieldsOpreation.AND);
            if (count == 0) {
                for (int i = 1; i <= 52; i++) {
                    try {
                        DAOFactory.getWeeklyScheduleDAO().delete(
                                DateHelper.getWeekStartDate(dateOfProgram.toLocalDate(), i)
                        );
                    } catch (NotFoundException e) {
                        LOG.log(Level.INFO, e.getMessage());
                    }
                }
                
                try {
                    DAOFactory.getAnnualScheduleDAO().delete(
                            DateHelper.getWeekYear(dateOfProgram.toLocalDate())
                    );
                } catch (NotFoundException e) {
                    LOG.log(Level.INFO, e.getMessage());
                }
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, null, e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
