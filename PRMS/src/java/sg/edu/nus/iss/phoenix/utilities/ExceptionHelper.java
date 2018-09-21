/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.utilities;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException;
import sg.edu.nus.iss.phoenix.core.exceptions.InUseException;
import sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException;
import sg.edu.nus.iss.phoenix.core.exceptions.ServiceException;

/**
 *
 * @author MyatMin
 */
public class ExceptionHelper {
    public static void throwCreationException(Exception ex, Logger LOG, String objectName) throws InvalidDataException, DuplicateException, SQLException {
        if (ex instanceof SQLException) {
            switch (((SQLException) ex).getErrorCode()) {
                case 1048:
                case 1216:
                case 1452:
                case 3673:
                    LOG.log(Level.INFO, "Data value provided in this {0} object are not valid.", objectName);
                    throw new InvalidDataException("Data value provided in this " + objectName + " object are not valid.", ex);
                case 1062:
                case 1169:
                case 1586:
                case 1859:
                    LOG.log(Level.INFO, "There is a {0} object already exists with this primary key", objectName);
                    throw new DuplicateException("There is a " + objectName + " object already exists with this primary key", ex);
                default:
                    throw (SQLException)ex;
            }
        }
        throw new ServiceException(ex.getMessage(), ex.getCause());
    }
    
    public static void throwUpdationException(Exception ex, Logger LOG, String objectName) throws InvalidDataException, DuplicateException, InUseException, SQLException {
        if (ex instanceof SQLException) {
            switch (((SQLException) ex).getErrorCode()) {
                case 1048:
                case 1216:
                case 1452:
                case 3673:
                    LOG.log(Level.INFO, "Data value provided in this {0} object are not valid.", objectName);
                    throw new InvalidDataException("Data value provided in this " + objectName + " object are not valid.", ex);
                case 1062:
                case 1169:
                case 1586:
                case 1859:
                    LOG.log(Level.INFO, "There is a {0} object already exists with this primary key", objectName);
                    throw new DuplicateException("There is a " + objectName + " object already exists with this primary key", ex);
                case 1217:
                case 1451:
                    LOG.log(Level.INFO, "This {0} object is already inuse in the other tables.", objectName);
                    throw new InUseException("This " + objectName + " object is already inuse in the other tables.", ex.getCause());
                default:
                    throw (SQLException)ex;
            }
        }
        throw new ServiceException(ex.getMessage(), ex.getCause());
    }
}
