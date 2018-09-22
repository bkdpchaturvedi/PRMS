/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.dao.DBConnector;
import sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException;
import sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.dao.WeeklyScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;
import sg.edu.nus.iss.phoenix.utilities.ExceptionHelper;

/**
 * WeeklySchedule Data Access Object (DAO). This class contains all database
 * handling that is needed to permanently store and retrieve WeeklySchedule
 * object instances.
 *
 * @author MyatMin
 */
public class WeeklyScheduleDAOImpl extends DBConnector implements WeeklyScheduleDAO {

    private static final Logger LOG = Logger.getLogger(WeeklyScheduleDAOImpl.class.getName());

    @Override
    public WeeklySchedule createValueObject() {
        return new WeeklySchedule();
    }

    @Override
    public void create(WeeklySchedule input) throws SQLException, InvalidDataException, DuplicateException {
        String sql = "";
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            sql = "INSERT INTO `phoenix`.`weekly-schedule` "
                    + "(`startDate`"
                    + ", `assignedBy`)"
                    + " VALUES (?,?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(input.getSartDate()));
            preparedStatement.setString(2, input.getAssignedBy());
            int rowcount = databaseUpdate(preparedStatement);
            LOG.log(Level.INFO, "{0} Weekly Schedule was created", input.getSartDate());
        } catch (SQLException e) {
            ExceptionHelper.throwCreationException(e, LOG, input.getSartDate().toString() + " Weekly Schedule");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }
    }

    @Override
    public void delete(WeeklySchedule input) throws NotFoundException, SQLException {
        String sql = "";
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            sql = "DELETE FROM `phoenix`.`weekly-schedule` "
                    + "WHERE StartDate = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(input.getSartDate()));
            int rowcount = databaseUpdate(preparedStatement);
            if (rowcount == 0) {
                LOG.log(Level.INFO, "{0} Weekly Schedule object not found", input.getSartDate().toString());
                throw new NotFoundException("Weekly Schedule object not found.");
            }
            LOG.log(Level.INFO, "{0} Weekly Schedule was deleted", input.getSartDate());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }
    }
    
    @Override
    public WeeklySchedule get(LocalDate startDate) throws SQLException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(WeeklySchedule input) throws SQLException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * databaseUpdate-method. This method is a helper method for internal use.
     * It will execute all database handling that will change the information in
     * tables. SELECT queries will not be executed here however. The return
     * value indicates how many rows were affected. This method will also make
     * sure that if cache is used, it will reset when data changes.
     *
     * @param preparedStatement This parameter contains the SQL statement to be
     * executed.
     * @return
     * @throws java.sql.SQLException
     */
    protected int databaseUpdate(PreparedStatement preparedStatement)
            throws SQLException {

        int result = preparedStatement.executeUpdate();

        return result;
    }

}
