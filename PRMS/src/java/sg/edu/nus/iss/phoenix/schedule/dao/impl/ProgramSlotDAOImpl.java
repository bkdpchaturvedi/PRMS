/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao.impl;

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
    public Integer checkExistCount(ProgramSlot input, DateRangeFilter filter, FieldsOpreation opreation) throws SQLException {
        openConnection();
        try {
            Integer rowcount = 0;
            String sql = "SELECT COUNT(*) FROM `phoenix`.`program-slot`";
            filterQueryBuilder(input, filter, opreation);
            ResultSet resultSet = databaseQuery(connection.prepareStatement(sql));
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
    public Boolean checkOverlap(ProgramSlot input, LocalDateTime origin) throws SQLException {
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
                preparedStatement.setTimestamp(7, Timestamp.valueOf(origin));
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
    public void delete(LocalDateTime dateOfProgram) throws NotFoundException, InUseException, SQLException {
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            String sql = "DELETE FROM `phoenix`.`program-slot` WHERE "
                    + "`dateOfProgram` = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(
                    1,
                    Timestamp.valueOf(dateOfProgram)
            );
            int rowcount = databaseUpdate(preparedStatement);
            if (rowcount == 0) {
                LOG.log(Level.INFO, "{0} Program Slot object not found", dateOfProgram.toString());
                throw new NotFoundException("Program Slot object not found.");
            }
            LOG.log(Level.INFO, "{0} Program Slot was deleted.", dateOfProgram.toString());
        } catch (SQLException e) {
            ExceptionHelper.throwDeletionException(e, LOG, dateOfProgram.toString() + " Program Slot");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }
    }

    @Override
    public ProgramSlot get(LocalDateTime dateOfProgram) throws SQLException, NotFoundException {
        ProgramSlot result = createValueObject();
        result.setDateOfProgram(dateOfProgram);
        load(result);
        return result;
    }

    @Override
    public void load(ProgramSlot input) throws SQLException, NotFoundException {
        try {
            openConnection();
            String sql = "SELECT * FROM `phoenix`.`program-slot` WHERE dateOfProgram = '" + input.getDateOfProgram() + "'";
            singleQuery(connection.prepareStatement(sql), input);
            LOG.log(Level.INFO, "{0} Program Slot loaded.", input.getDateOfProgram());
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<ProgramSlot> loadAll() throws SQLException {
        List<ProgramSlot> result = new ArrayList<>();
        try {
            openConnection();
            String sql = "SELECT * FROM `phoenix`.`program-slot` ORDER BY `dateOfProgram` ASC; ";
            result = listQuery(connection.prepareStatement(sql));
            LOG.log(Level.INFO, "{0} Program Slot loaded.", result.size());
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public List<ProgramSlot> search(ProgramSlot input, DateRangeFilter filter, FieldsOpreation opreation) throws SQLException {
        List<ProgramSlot> result = new ArrayList<>();
        try {
            openConnection();
            String sql = "SELECT * FROM `phoenix`.`program-slot`";
            sql += filterQueryBuilder(input, filter, opreation);
            sql += " ORDER BY `dateOfProgram` ASC";
            result = listQuery(connection.prepareStatement(sql));
            LOG.log(Level.INFO, "{0} Program Slot found.", result.size());
        } finally {
            closeConnection();
        }
        return result;
    }

    @Override
    public void update(ProgramSlot input, LocalDateTime origin) throws InvalidDataException, DuplicateException, InUseException, NotFoundException, SQLException {
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
                    Timestamp.valueOf(origin)
            );
            int rowcount = databaseUpdate(preparedStatement);
            if (rowcount == 0) {
                LOG.log(Level.INFO, "{0} Program Slot object not found", origin.toString());
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
     * filterQueryBuilder-method. This method is a helper method for internal
     * use. It will build query string for the information searching in tables.
     * Based on the input value object.
     *
     * @param input
     * @param filter
     * @param opreation
     * @return
     */
    protected String filterQueryBuilder(ProgramSlot input, DateRangeFilter filter, FieldsOpreation opreation) {
        String sql = "";
        String queryOpreation  = "";
        switch (opreation) {
            case AND:
                queryOpreation = " AND";
                break;
            case OR:
            queryOpreation = " OR";
            break;
        }
        
        if (input.getDateOfProgram() != null) {
            switch (filter) {
                case BY_FUTURE:
                    sql += queryOpreation + " (DateOfProgram >= '" + LocalDateTime.now()
                            + "')";
                    break;
                case BY_YEAR:
                    sql += queryOpreation + " (dateOfProgram >= '"
                            + DateHelper.getWeekStartDate(
                                    input.getDateOfProgram().toLocalDate(), 1)
                            + "' AND dateOfProgram < '"
                            + DateHelper.getWeekEndDate(
                                    input.getDateOfProgram().toLocalDate(), 52)
                                    .plusDays(1) + "')";
                    break;
                case BY_WEEK:
                    sql += queryOpreation + " (dateOfProgram >= '"
                            + DateHelper.getWeekStartDate(
                                    input.getDateOfProgram().toLocalDate())
                            + "' AND dateOfProgram < '"
                            + DateHelper.getWeekEndDate(
                                    input.getDateOfProgram().toLocalDate()
                                            .plusDays(1)) + "')";
                    break;
                case BY_DATE:
                    sql += queryOpreation + " (dateOfProgram >= '"
                            + input.getDateOfProgram()
                                    .truncatedTo(ChronoUnit.DAYS).toLocalDate()
                            + "' AND dateOfProgram < '"
                            + input.getDateOfProgram()
                                    .truncatedTo(ChronoUnit.DAYS)
                                    .plusDays(1).toLocalDate() + "')";
                    break;
                case BY_HOUR:
                    sql += queryOpreation + " (dateOfProgram >= '"
                            + input.getDateOfProgram()
                                    .truncatedTo(ChronoUnit.HOURS)
                            + "' AND dateOfProgram < '"
                            + input.getDateOfProgram()
                                    .truncatedTo(ChronoUnit.HOURS)
                                    .plusHours(1) + "')";
            }
        }
        if (input.getRadioProgram() != null) {
            sql += queryOpreation + " (program-name = '"
                    + input.getRadioProgram().getName() + "')";
        }
        if (input.getPresenter() != null) {
            sql += queryOpreation + " AND (presenter = '"
                    + input.getPresenter().getId() + "')";
        }
        if (input.getProducer() != null) {
            sql += queryOpreation + " AND (presenter = '"
                    + input.getProducer().getId() + "')";
        }
        if (sql != "") {
            sql = " WHERE" + sql.replaceFirst(queryOpreation, "");
        }
        return sql;
    }

    /**
     * databaseQuery-method. This method is a helper method for internal use. It
     * will execute all database handling that will query the information in
     * tables. For the SELECT queries only to be executed here. The return value
     * indicates the records set from the table.
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
