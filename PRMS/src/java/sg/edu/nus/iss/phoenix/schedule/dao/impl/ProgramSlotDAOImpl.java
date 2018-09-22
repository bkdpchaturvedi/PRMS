/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.dao.DBConnector;
import sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException;
import sg.edu.nus.iss.phoenix.core.exceptions.InUseException;
import sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.dao.ProgramSlotDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.utilities.DateHelper;
import sg.edu.nus.iss.phoenix.utilities.ExceptionHelper;

/**
 * ProgramSlot Data Access Object (DAO). This class contains all database
 * handling that is needed to permanently store and retrieve ProgramSlot object
 * instances.
 *
 * @author MyatMin
 */
public class ProgramSlotDAOImpl extends DBConnector implements ProgramSlotDAO {


    private static final Logger LOG = Logger.getLogger(ProgramSlotDAOImpl.class.getName());
    
    @Override
    public ProgramSlot createValueObject() {
        return new ProgramSlot();
    }
    
    @Override
    public Integer checkExisitCount(LocalDateTime input, DateRangeFilter filter) throws SQLException {
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            Integer rowcount = 0;
            String sql = "SELECT COUNT(*) FROM `phoenix`.`program-slot` WHERE ";
            switch (filter) {
                case BY_YEAR:
                    sql += "dateOfProgram >= ? AND dateOfProgram < ?";
                    preparedStatement = connection.prepareCall(sql);
                    preparedStatement.setDate(1, Date.valueOf(DateHelper.getWeekStartDate(input.toLocalDate(), 1)));
                    preparedStatement.setDate(2, Date.valueOf(DateHelper.getWeekEndDate(input.toLocalDate(), 52).plusDays(1)));
                    break;
                case BY_WEEK:
                    sql += "dateOfProgram >= ? AND dateOfProgram < ?";
                    preparedStatement = connection.prepareCall(sql);
                    preparedStatement.setDate(1, Date.valueOf(DateHelper.getWeekStartDate(input.toLocalDate())));
                    preparedStatement.setDate(2, Date.valueOf(DateHelper.getWeekEndDate(input.toLocalDate().plusDays(1))));
                    break;
                case BY_DATE:
                    sql += "dateOfProgram >= ? AND dateOfProgram < ?";
                    preparedStatement = connection.prepareCall(sql);
                    preparedStatement.setDate(1, Date.valueOf(input.truncatedTo(ChronoUnit.DAYS).toLocalDate()));
                    preparedStatement.setDate(2, Date.valueOf(input.truncatedTo(ChronoUnit.DAYS).plusDays(1).toLocalDate()));
                    break;
                case BY_HOUR:
                    sql += "dateOfProgram >= ? AND dateOfProgram < ?";
                    preparedStatement = connection.prepareCall(sql);
                    preparedStatement.setTimestamp(1, Timestamp.valueOf(input.truncatedTo(ChronoUnit.HOURS)));
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(input.truncatedTo(ChronoUnit.HOURS).plusHours(1)));
                    break;
            }
            ResultSet resultSet = databaseQuery(preparedStatement);
            if (resultSet.next()) {
                rowcount = resultSet.getInt(1);
                LOG.log(Level.INFO, "{0} Program Slot object(s) found.", rowcount);
            }
            return rowcount;
        } finally {
            closeConnection();
        }
    }

    @Override
    public Boolean checkOverlap(ProgramSlot input) throws SQLException {
        return checkOverlap(input, null);
    }
    
    @Override
    public Boolean checkOverlap(ProgramSlot input, ProgramSlot origin) throws SQLException {
        LocalDateTime programSlotStarts = input.getDateOfProgram();
        LocalDateTime programSlotEnds = input.getDateOfProgram()
                .plusSeconds(input.getDuration().toSecondOfDay());
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            Integer rowcount = 0;
            String sql = "SELECT COUNT(*) FROM `phoenix`.`program-slot`"
                    + " WHERE ((DateOfProgram >= ? AND DateOfProgram < ?)"
                    + " OR (ADDTIME(DateOfProgram, duration) > ?"
                    + " AND ADDTIME(DateOfProgram, duration) < ?)"
                    + " OR (DateOfProgram < ?"
                    + " AND ADDTIME(DateOfProgram, duration) > ?))";
            if (origin != null) {
                sql += " AND DateOfProgram <> ?";
            }
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(programSlotStarts));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(programSlotEnds));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(programSlotStarts));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(programSlotEnds));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(programSlotStarts));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(programSlotEnds));
            if (origin != null) {
                preparedStatement.setTimestamp(7, Timestamp.valueOf(origin.getDateOfProgram()));
            }
            ResultSet resultSet = databaseQuery(preparedStatement);
            if (resultSet.next()) {
                rowcount = resultSet.getInt(1);
                LOG.log(Level.INFO, "{0} overlap Program Slot found.", rowcount);
                return resultSet.getInt(1) > 0;
            }
            return rowcount != 0;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }
    }

    @Override
    public void create(ProgramSlot input) throws DuplicateException, InvalidDataException, SQLException {
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            String sql = "INSERT INTO `phoenix`.`program-slot`"
                    + "(`dateOfProgram`"
                    + ", `duration`"
                    + ", `program-name`"
                    + ", `presenter`"
                    + ", `producer`"
                    + ", `assignedBy`)"
                    + " VALUES (?,?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(
                    1,
                    Timestamp.valueOf(input.getDateOfProgram())
            );
            preparedStatement.setTime(2, Time.valueOf(input.getDuration()));
            preparedStatement.setString(3, input.getRadioProgram().getName());
            preparedStatement.setString(4, input.getPresenter().getId());
            preparedStatement.setString(5, input.getProducer().getId());
            preparedStatement.setString(6, input.getAssignedBy());
            int rowcount = databaseUpdate(preparedStatement);
            LOG.log(Level.INFO, "{0} Program Slot was created.", input.getDateOfProgram().toString());
        } catch (SQLException e) {
            ExceptionHelper.throwCreationException(e, LOG, input.getDateOfProgram().toString() + " Program Slot");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }
    }

    @Override
    public void delete(ProgramSlot input) throws NotFoundException, InUseException, SQLException {
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            String sql = "DELETE FROM `phoenix`.`program-slot` WHERE "
                    + "`dateOfProgram` = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(
                    1,
                    Timestamp.valueOf(input.getDateOfProgram())
            );
            int rowcount = databaseUpdate(preparedStatement);
            if (rowcount == 0) {
                LOG.log(Level.INFO, "{0} Program Slot object not found", input.getDateOfProgram().toString());
                throw new NotFoundException("Program Slot object not found.");
            }
            LOG.log(Level.INFO, "{0} Program Slot was deleted.", input.getDateOfProgram().toString());
        } catch (SQLException e) {
            ExceptionHelper.throwDeletionException(e, LOG, input.getDateOfProgram().toString() + " Program Slot");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }
    }

    @Override
    public ProgramSlot get(LocalDateTime dateOfProgram) throws SQLException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(ProgramSlot input) throws SQLException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProgramSlot> loadAll() throws SQLException, NotFoundException {

        super.openConnection();

        String sql = "SELECT * FROM `phoenix`.`program-slot` ORDER BY `dateOfProgram` ASC; ";
        List<ProgramSlot> searchResults = listQuery(connection.prepareStatement(sql));
        closeConnection();
        System.out.println("record size" + searchResults.size());
        return searchResults;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public List<ProgramSlot> search(ProgramSlot input) throws SQLException {
        openConnection();
        try {
            String sql = "SELECT * FROM `phoenix`.`program-slot`";
//                    + " WHERE YEEAR(DateOfProgram) = '" + year ;
            List<ProgramSlot> result = listQuery(connection.prepareStatement(sql));
            LOG.log(Level.INFO, "{0} overlap Program Slot found.", result.size());
            return null;// !result.isEmpty();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void update(ProgramSlot input, ProgramSlot origin) throws InvalidDataException, DuplicateException, InUseException, NotFoundException, SQLException {
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            String sql = "UPDATE `phoenix`.`program-slot` SET "
                    + "`dateOfProgram` = ?"
                    + ", `duration` = ?"
                    + ", `program-name` = ?"
                    + ", `presenter` = ?"
                    + ", `producer` = ?"
                    + ", `assignedBy` = ?"
                    + " WHERE dateOfProgram = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(
                    1,
                    Timestamp.valueOf(input.getDateOfProgram())
            );
            preparedStatement.setTime(2, Time.valueOf(input.getDuration()));
            preparedStatement.setString(3, input.getRadioProgram().getName());
            preparedStatement.setString(4, input.getPresenter().getId());
            preparedStatement.setString(5, input.getProducer().getId());
            preparedStatement.setString(6, input.getAssignedBy());
            preparedStatement.setTimestamp(
                    7,
                    Timestamp.valueOf(origin.getDateOfProgram())
            );
            int rowcount = databaseUpdate(preparedStatement);
            if (rowcount == 0) {
                LOG.log(Level.INFO, "{0} Program Slot object not found", origin.getDateOfProgram().toString());
                throw new NotFoundException("Program Slot object not found.");
            }
            LOG.log(Level.INFO, "{0} Program Slot was update.", input.getDateOfProgram().toString());
        } catch (SQLException e) {
            ExceptionHelper.throwUpdationException(e, LOG, input.getDateOfProgram().toString() + " Program Slot");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }
    }
    
    /**
     * databaseQuery-method. This method is a helper method for internal use.
     * It will execute all database handling that will query the information in
     * tables. For the SELECT queries only to be executed here. The return
     * value indicates the records set from the table.
     *
     * @param preparedStatement This parameter contains the SQL statement to be
     * executed.
     * @return
     * @throws java.sql.SQLException
     */
    protected ResultSet databaseQuery(PreparedStatement preparedStatement)
            throws SQLException {

        return preparedStatement.executeQuery();
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
     * @param programSlot Class-instance where resulting data will be stored.
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws java.sql.SQLException
     */
    protected void singleQuery(PreparedStatement preparedStatement, ProgramSlot programSlot)
            throws NotFoundException, SQLException {

        ResultSet result = null;
        openConnection();
        try {
            result = preparedStatement.executeQuery();

            if (result.next()) {
                programSlot.appointAll(
                        result.getTimestamp("dateOfProgram").toLocalDateTime(),
                        result.getTime("duration").toLocalTime(),
                        result.getString("program-name"),
                        result.getString("presenter"),
                        result.getString("producer"),
                        result.getString("assignedBy")
                );

            } else {
                LOG.log(Level.INFO, "{0} Program Slot object not found", programSlot.getDateOfProgram().toString());
                throw new NotFoundException("Program Slot object not found.");
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

    /**
     * listQuery-method. This method is a helper method for internal use. It
     * will execute all database queries that will return multiple rows. The
     * result set will be converted to the List of valueObjects. If no rows were
     * found, an empty List will be returned.
     *
     * @param preparedStatement This parameter contains the SQL statement to be
     * executed.
     * @return
     * @throws java.sql.SQLException
     */
    protected List<ProgramSlot> listQuery(PreparedStatement preparedStatement)
            throws SQLException {

        ArrayList<ProgramSlot> searchResults = new ArrayList<>();
        ResultSet result = null;
        openConnection();
        try {
            result = preparedStatement.executeQuery();

            while (result.next()) {
                ProgramSlot temp = createValueObject();
                try {
                    temp.appointAll(
                            result.getTimestamp("dateOfProgram").toLocalDateTime(),
                            result.getTime("duration").toLocalTime(),
                            result.getString("program-name"),
                            result.getString("presenter"),
                            result.getString("producer"),
                            result.getString("assignedBy")
                    );
                    searchResults.add(temp);
                } catch (SQLException e) {
                    throw e;
                }

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

        return (List<ProgramSlot>) searchResults;
    }

}
