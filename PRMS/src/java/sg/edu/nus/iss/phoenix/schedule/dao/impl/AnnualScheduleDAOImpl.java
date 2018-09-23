/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.dao.DBConnector;
import sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException;
import sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.dao.AnnualScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;
import sg.edu.nus.iss.phoenix.utilities.ExceptionHelper;

/**
 * AnnualSchedule Data Access Object (DAO). This class contains all database
 * handling that is needed to permanently store and retrieve AnnualSchedule
 * object instances.
 *
 * @author MyatMin
 */
public class AnnualScheduleDAOImpl extends DBConnector implements AnnualScheduleDAO {

    private static final Logger LOG = Logger.getLogger(AnnualScheduleDAOImpl.class.getName());

    @Override
    public AnnualSchedule createValueObject() {
        return new AnnualSchedule();
    }
    @Override
    public void create(AnnualSchedule input) throws SQLException, InvalidDataException, DuplicateException {
        String sql = "";
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            sql = "INSERT INTO `phoenix`.`annual-schedule` " 
                    + "(`year`" 
                    + ", `assignedBy`)" 
                    + " VALUES (?,?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, input.getYear());
            preparedStatement.setString(2, input.getAssignedBy());
            int rowcount = databaseUpdate(preparedStatement);
            LOG.log(Level.INFO, "{0} Annual Schedule was created", input.getYear().toString());
        } catch (SQLException e) {
            ExceptionHelper.throwCreationException(e, LOG, input.getYear().toString() + " Annual Schedule");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }
    }

    @Override
    public void delete(AnnualSchedule input) throws NotFoundException, SQLException {
        String sql = "";
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            sql = "DELETE FROM `phoenix`.`annual-schedule` "
                    + "WHERE year = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, input.getYear());
            int rowcount = databaseUpdate(preparedStatement);
            if (rowcount == 0) {
                LOG.log(Level.INFO, "{0} Annual Schedule object not found", input.getYear().toString());
                throw new NotFoundException("Annual Schedule object not found.");
            }
            LOG.log(Level.INFO, "{0} Annual Schedule was deleted", input.getYear());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }
    }
    
    @Override
    public AnnualSchedule get(Integer year) throws SQLException, NotFoundException {
        AnnualSchedule annualSchedule = createValueObject();
        annualSchedule.setYear(year);
        load(annualSchedule);
        return annualSchedule;
    }

    @Override
    public void load(AnnualSchedule input) throws SQLException, NotFoundException {
        if (input.getYear()== null) {
            throw new NotFoundException("Can not select without Primary-Key!");
        }
        String sql = "SELECT * FROM `annual-schedule` WHERE (`year` = ? ); ";
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, input.getYear());
            singleQuery(preparedStatement, input);
            LOG.log(Level.INFO, "{0} Annual Schedule was loaded", input.getYear());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }
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
    
    /**
     * databaseQuery-method. This method is a helper method for internal use. It
     * will execute all database queries that will return only one row. The
     * result set will be converted to valueObject. If no rows were found,
     * NotFoundException will be thrown.
     *
     * @param preparedStatement This parameter contains the SQL statement to be
     * executed.
     * @param annualSchedule
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws java.sql.SQLException
     */
    protected void singleQuery(PreparedStatement preparedStatement, AnnualSchedule annualSchedule)
            throws NotFoundException, SQLException {

        ResultSet result = null;
        openConnection();
        try {
            result = preparedStatement.executeQuery();

            if (result.next()) {
                annualSchedule.appointAll(
                        result.getInt("year"),
                        result.getString("assignedBy")
                );
            } else {
                LOG.log(Level.INFO, "{0} Annual Schedule object not found", annualSchedule.getYear().toString());
                throw new NotFoundException("Annual Schedule object not found.");
            }
        } finally {
            if (result != null) {
                result.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }
    }
}
