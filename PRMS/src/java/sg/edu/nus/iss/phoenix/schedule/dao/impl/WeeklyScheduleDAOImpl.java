/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import sg.edu.nus.iss.phoenix.core.dao.DBConnector;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.dao.WeeklyScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;

/**
 * WeeklySchedule Data Access Object (DAO). This class contains all database
 * handling that is needed to permanently store and retrieve WeeklySchedule
 * object instances.
 *
 * @author MyatMin
 */
public class WeeklyScheduleDAOImpl extends DBConnector implements WeeklyScheduleDAO {

    @Override
    public WeeklySchedule createValueObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(WeeklySchedule input) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WeeklySchedule get(LocalDate startDate) throws SQLException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load(WeeklySchedule input) throws SQLException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
