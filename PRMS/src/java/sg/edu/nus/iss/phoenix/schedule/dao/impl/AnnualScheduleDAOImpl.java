/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao.impl;

import java.sql.SQLException;
import sg.edu.nus.iss.phoenix.core.dao.DBConnector;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.dao.AnnualScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualSchedule;

/**
 * AnnualSchedule Data Access Object (DAO). This class contains all database
 * handling that is needed to permanently store and retrieve AnnualSchedule
 * object instances.
 *
 * @author MyatMin
 */
public class AnnualScheduleDAOImpl extends DBConnector implements AnnualScheduleDAO {

    @Override
    public AnnualSchedule createValueObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(AnnualSchedule input) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnnualSchedule get(Integer year) throws SQLException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(AnnualSchedule input) throws SQLException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
