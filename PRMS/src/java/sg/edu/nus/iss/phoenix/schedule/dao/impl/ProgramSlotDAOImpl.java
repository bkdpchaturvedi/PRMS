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
import java.util.ArrayList;
import java.util.List;
import sg.edu.nus.iss.phoenix.core.dao.DBConnector;
import sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.dao.ProgramSlotDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 * ProgramSlot Data Access Object (DAO). This class contains all database
 * handling that is needed to permanently store and retrieve ProgramSlot object
 * instances.
 *
 * @author MyatMin
 */
public class ProgramSlotDAOImpl extends DBConnector implements ProgramSlotDAO {

    @Override
    public ProgramSlot createValueObject() {
        return new ProgramSlot();
    }

    @Override
    public Boolean checkOverlap(ProgramSlot input) throws SQLException {
        openConnection();
        LocalDateTime programSlotStarts = input.getDateOfProgram();
        LocalDateTime programSlotEnds = input.getDateOfProgram().plusSeconds(input.getDuration().getSecond());
        try {
            String sql = "SELECT * FROM `program-slot`"
                    + " WHERE DateOfProgram between '" + programSlotStarts + "' and '" + programSlotEnds + "' "
                    + " OR ADDTIME(DateOfProgram, duration) between '" + programSlotStarts + "' and '" + programSlotEnds + "' "
                    + " OR (DateOfProgram < '" + programSlotStarts + "' AND ADDTIME(DateOfProgram, duration) > '" + programSlotEnds + "')";
            List<ProgramSlot> searchResults = listQuery(connection.prepareStatement(sql));
            return searchResults.isEmpty();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void create(ProgramSlot input) throws DuplicateException, SQLException {
        String sql = "";
        PreparedStatement preparedStatement = null;
        openConnection();
        try {
            sql = "INSERT INTO `phoenix`.`program-slot` (`dateOfProgram`, `duration`, `program-name`, `presenter`, `producer`, `assingedBy`) VALUES (?,?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(input.getDateOfProgram()));
            preparedStatement.setTime(2, Time.valueOf(input.getDuration()));
            preparedStatement.setString(3, input.getRadioProgram().getName());
            preparedStatement.setString(4, input.getPresenter().getId());
            preparedStatement.setString(5, input.getProducer().getId());
            preparedStatement.setString(6, input.getAssignedBy());
            int rowcount = databaseExecute(preparedStatement);
            if (rowcount != 1) {
                throw new DuplicateException("There is a Program Slot Object already exists on this date of program", null);
            }

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection();
        }

    }

    @Override
    public void delete(ProgramSlot input) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        String sql = "SELECT * FROM `program-slot` ORDER BY `dateOfProgram` ASC; ";
        List<ProgramSlot> searchResults = listQuery(connection.prepareStatement(sql));
        closeConnection();
        System.out.println("record size" + searchResults.size());
        return searchResults;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    /**
     * databaseExecute-method. This method is a helper method for internal use.
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
    protected int databaseExecute(PreparedStatement preparedStatement)
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
                throw new NotFoundException("Program Slot Object Not Found!");
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
                } catch (SQLException ex) {
                    throw ex;
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

    @Override
    public List<ProgramSlot> search(ProgramSlot input) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ProgramSlot input) throws NotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
