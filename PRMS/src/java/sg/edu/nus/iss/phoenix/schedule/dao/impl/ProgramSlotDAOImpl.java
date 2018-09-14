/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactory;
import sg.edu.nus.iss.phoenix.core.dao.DBConnector;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Boolean checkOverlap(ProgramSlot input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void create(ProgramSlot input) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public List<ProgramSlot> loadAll() throws SQLException,NotFoundException {
        
        super.openConnection();
        
        String sql = "SELECT * FROM `program-slot` ORDER BY `dateOfProgram` ASC; ";
        List<ProgramSlot> searchResults = listQuery(connection.prepareStatement(sql));
        closeConnection();
        System.out.println("record size" + searchResults.size());
        return searchResults;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    protected List<ProgramSlot> listQuery(PreparedStatement stmt) throws SQLException,NotFoundException {
        
        ArrayList<ProgramSlot> searchResults = new ArrayList<>();
        ResultSet result = null;
        ProgramDAO programDAO = DAOFactory.getProgramDAO();
        openConnection();
        try {
            result = stmt.executeQuery();
            
            while (result.next()) {
                ProgramSlot temp = createValueObject();
                try {
                    RadioProgram tempRP = programDAO.getObject(result.getString("program-name"));
                    temp.setRadioProgram(tempRP);
                    temp.setDateOfProgram(result.getDate("dateOfProgram"));
                    temp.setDuration(result.getTime("duration").toLocalTime());
                    temp.setStartTime(result.getDate("startTime").toLocalDate().atStartOfDay());
                    /*
                        Need to Add Present and Producer logic 
                    */
                    
                    searchResults.add(temp);
                } catch (SQLException | NotFoundException ex) {
                    throw ex;
                }
                
            }
            
        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
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
