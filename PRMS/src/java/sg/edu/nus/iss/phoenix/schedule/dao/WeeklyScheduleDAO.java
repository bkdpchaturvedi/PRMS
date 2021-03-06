/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException;
import sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklySchedule;

/**
 *
 * @author MyatMin
 */
public interface WeeklyScheduleDAO {

    /**
     * createValueObject-method. This method is used when the Dao class needs to
     * create new value object instance. The reason why this method exists is
     * that sometimes the programmer may want to extend also the valueObject and
     * then this method can be over-rided to return extended valueObject.
     *
     * @return the weekly schedule class instance
     */
    public abstract WeeklySchedule createValueObject();

    /**
     * create-method. This will create new row in database according to supplied
     * valueObject contents. Make sure that values for all NOT NULL columns are
     * correctly specified. Also, if this table does not use automatic
     * surrogate-keys the primary-key must be specified. After INSERT command
     * this method will read the generated primary-key back to valueObject if
     * automatic surrogate-keys were used.
     *
     * @param input This parameter contains the class instance to be created. If
     * automatic surrogate-keys are not used the Primary-key field must be set
     * for this to work properly.
     * @throws java.sql.SQLException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException
     */
    public abstract void create(WeeklySchedule input) throws SQLException, InvalidDataException, DuplicateException;

    /**
     * delete-method. This method will remove the information from database as
     * identified by by primary-key in supplied valueObject. Once valueObject
     * has been deleted it can not be restored by calling save. Restoring can
     * only be done using create method but if database is using automatic
     * surrogate-keys, the resulting object will have different primary-key than
     * what it was in the deleted object. If delete can not find matching row,
     * NotFoundException will be thrown.
     *
     * @param startDate This parameter contains the class instance to be deleted.
     * Primary-key field must be set for this to work properly.
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws java.sql.SQLException
     */
    public abstract void delete(LocalDate startDate)
            throws NotFoundException, SQLException;

    /**
     * get-method. This will create and load valueObject contents from database
     * using given Primary-Key as identifier. This method is just a convenience
     * method for the real load-method which accepts the valueObject as a
     * parameter. Returned valueObject will be created using the
     * createValueObject() method.
     *
     * @param startDate the start date of the week
     * @return the weekly schedule class instance
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws java.sql.SQLException
     */
    public abstract WeeklySchedule get(LocalDate startDate) throws SQLException, NotFoundException;

    /**
     * load-method. This will load valueObject contents from database using
     * Primary-Key as identifier. Upper layer should use this so that
     * valueObject instance is created and only primary-key should be specified.
     * Then call this method to complete other persistent information. This
     * method will overwrite all other fields except primary-key and possible
     * runtime variables. If load can not find matching row, NotFoundException
     * will be thrown.
     *
     * @param input This parameter contains the class instance to be loaded.
     * Primary-key field must be set for this to work properly.
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws java.sql.SQLException
     *
     */
    public abstract void load(WeeklySchedule input) throws SQLException, NotFoundException;
}
