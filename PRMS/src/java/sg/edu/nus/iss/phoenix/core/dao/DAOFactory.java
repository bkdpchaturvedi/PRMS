/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.core.dao;

import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.dao.impl.RoleDaoImpl;
import sg.edu.nus.iss.phoenix.authenticate.dao.impl.UserDaoImpl;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.radioprogram.dao.impl.ProgramDAOImpl;
import sg.edu.nus.iss.phoenix.schedule.dao.AnnualScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.dao.ProgramSlotDAO;
import sg.edu.nus.iss.phoenix.schedule.dao.WeeklyScheduleDAO;
import sg.edu.nus.iss.phoenix.schedule.dao.impl.AnnualScheduleDAOImpl;
import sg.edu.nus.iss.phoenix.schedule.dao.impl.ProgramSlotDAOImpl;
import sg.edu.nus.iss.phoenix.schedule.dao.impl.WeeklyScheduleDAOImpl;

/**
 *
 * @author projects
 */
public class DAOFactory {

    private static UserDao userDAO = new UserDaoImpl();
    private static RoleDao roleDAO = new RoleDaoImpl();
    private static ProgramDAO programDAO = new ProgramDAOImpl();
    private static ProgramSlotDAO programSlotDAO = new ProgramSlotDAOImpl();
    private static AnnualScheduleDAO annualScheduleDAO = new AnnualScheduleDAOImpl();
    private static WeeklyScheduleDAO weeklyScheduleDAO = new WeeklyScheduleDAOImpl();

    /**
     * Get the ProgramDAO from of this factory
     *
     * @return instance of ProgramDAO
     */
    public static ProgramDAO getProgramDAO() {
        return programDAO;
    }

    /**
     * Get the RoleDAO from this factory
     *
     * @return instance of RoleDAO
     */
    public static RoleDao getRoleDAO() {
        return roleDAO;
    }

    /**
     * Get the UserDAO from this factory
     *
     * @return instance of UserDAO
     */
    public static UserDao getUserDAO() {
        return userDAO;
    }

    /**
     * Get the ProgramSlotDAO from this factory
     *
     * @return instance of ProgramSlotDAO
     */
    public static ProgramSlotDAO getProgramSlotDAO() {
        return programSlotDAO;
    }

    /**
     * Get the AnnualScheduleDAO from this factory
     *
     * @return instance of AnnualScheduleDAO
     */
    public static AnnualScheduleDAO getAnnualScheduleDAO() {
        return annualScheduleDAO;
    }

    /**
     * Get the WeeklyScheduleDAO from this factory
     *
     * @return instance of WeeklyScheduleDAO
     */
    public static WeeklyScheduleDAO getWeeklyScheduleDAO() {
        return weeklyScheduleDAO;
    }

}

///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package sg.edu.nus.iss.phoenix.core.dao;
//
//import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
//import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
//import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
//import sg.edu.nus.iss.phoenix.schedule.dao.AnnualScheduleDAO;
//import sg.edu.nus.iss.phoenix.schedule.dao.ProgramSlotDAO;
//import sg.edu.nus.iss.phoenix.schedule.dao.WeeklyScheduleDAO;
//
///**
// *
// * @author projects
// */
//public interface DAOFactory {
//
//    /**
//     * Get the ProgramDAO from of this factory
//     *
//     * @return instance of ProgramDAO
//     */
//    abstract ProgramDAO getProgramDAO();
//
//    /**
//     * Get the RoleDAO from this factory
//     *
//     * @return instance of RoleDAO
//     */
//    abstract RoleDao getRoleDAO();
//
//    /**
//     * Get the UserDAO from this factory
//     *
//     * @return instance of UserDAO
//     */
//    abstract UserDao getUserDAO();
//
//    /**
//     * Get the ProgramSlotDAO from this factory
//     *
//     * @return instance of ProgramSlotDAO
//     */
//    abstract ProgramSlotDAO getProgramSlotDAO();
//
//    /**
//     * Get the AnnualScheduleDAO from this factory
//     *
//     * @return instance of AnnualScheduleDAO
//     */
//    abstract AnnualScheduleDAO getAnnualScheduleDAO();
//
//    /**
//     * Get the WeeklyScheduleDAO from this factory
//     *
//     * @return instance of WeeklyScheduleDAO
//     */
//    abstract  WeeklyScheduleDAO getWeeklyScheduleDAO();
//
//}
