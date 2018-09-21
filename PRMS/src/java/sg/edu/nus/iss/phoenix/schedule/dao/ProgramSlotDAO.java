/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException;
import sg.edu.nus.iss.phoenix.core.exceptions.InUseException;
import sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;

/**
 *
 * @author MyatMin
 */
public interface ProgramSlotDAO {

    /**
     * createValueObject-method. This method is used when the Dao class needs to
     * create new value object instance. The reason why this method exists is
     * that sometimes the programmer may want to extend also the valueObject and
     * then this method can be over-rided to return extended valueObject.
     *
     * @return the program slot class instance
     */
    public abstract ProgramSlot createValueObject();

    /**
     * checkOverlap-method. This will check with the existing data from table
     * using given data from input object contents whether their timing
     * overlapping against each other or not.
     *
     * @param input the program slot class instance
     * @return boolean
     * @throws java.sql.SQLException
     */
    public abstract Boolean checkOverlap(ProgramSlot input) throws SQLException;

    /**
     * checkOverlap-method. This will check with the existing data from table
     * using given data from input object contents whether their timing
     * overlapping against each other or not but to discard checking against
     * origin object.
     *
     * @param input the program slot class instance
     * @param origin
     * @return boolean
     * @throws java.sql.SQLException
     */
    public abstract Boolean checkOverlap(ProgramSlot input, ProgramSlot origin) throws SQLException;

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
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException
     * @throws java.sql.SQLException
     */
    public abstract void create(ProgramSlot input)
            throws InvalidDataException, DuplicateException, SQLException;

    /**
     * delete-method. This method will remove the information from database as
     * identified by by primary-key in supplied valueObject. Once valueObject
     * has been deleted it can not be restored by calling save. Restoring can
     * only be done using create method but if database is using automatic
     * surrogate-keys, the resulting object will have different primary-key than
     * what it was in the deleted object. If delete can not find matching row,
     * NotFoundException will be thrown.
     *
     * @param input This parameter contains the class instance to be deleted.
     * Primary-key field must be set for this to work properly.
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws java.sql.SQLException
     */
    public abstract void delete(ProgramSlot input)
            throws NotFoundException, SQLException;

    /**
     * get-method. This will create and load valueObject contents from database
     * using given Primary-Key as identifier. This method is just a convenience
     * method for the real load-method which accepts the valueObject as a
     * parameter. Returned valueObject will be created using the
     * createValueObject() method.
     *
     * @param dateOfProgram This represent primary-key value of the table
     * @return the program slot class instance
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws java.sql.SQLException
     */
    public abstract ProgramSlot get(LocalDateTime dateOfProgram)
            throws SQLException, NotFoundException;

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
    public abstract void load(ProgramSlot input)
            throws SQLException, NotFoundException;

    /**
     * LoadAll-method. This will read all contents from database table and build
     * a List containing valueObjects. Please note, that this method will
     * consume huge amounts of resources if table has lot's of rows. This should
     * only be used when target tables have only small amounts of data.
     *
     * @return list of the class instance
     * @throws java.sql.SQLException
     */
    public abstract List<ProgramSlot> loadAll()
            throws SQLException, NotFoundException;

    /**
     * search-Method. This method provides searching capability to get matching
     * valueObjects from database. It works by searching all objects that match
     * permanent instance variables of given object. Upper layer should use this
     * by setting some parameters in valueObject and then call search. The
     * result will be 0-N objects in a List, all matching those criteria you
     * specified. Those instance-variables that have NULL values are excluded in
     * search-criteria.
     *
     * @param input This parameter contains the class instance where search will
     * be based. Primary-key field should not be set.
     * @return list of the class instance
     * @throws java.sql.SQLException
     */
    public abstract List<ProgramSlot> search(ProgramSlot input)
            throws SQLException;
    
    /**
     * search-Method. This method provides searching capability to get matching
     * valueObjects from database. It works by searching all objects that match
     * permanent instance variables of given object. The
     * result will be 0-N objects in a List, all matching those criteria you
     * specified. Those instance-variables that have NULL values are excluded in
     * search-criteria.
     *
     * @param year
     * @return list of the class instance
     * @throws java.sql.SQLException
     */
    public abstract List<ProgramSlot> search(Integer year)
            throws SQLException;

    /**
     * update-method. This method will save the current state of input to
     * database. Save can not be used to create new instances in database, so
     * upper layer must make sure that the primary-key is correctly specified.
     * Primary-key will indicate which instance is going to be updated in
     * database. If save can not find matching row, NotFoundException will be
     * thrown.
     *
     * @param input This parameter contains the class instance to be saved.
     * Primary-key field must be set for this to work properly.
     * @param origin
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.InUseException
     * @throws sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException
     * @throws java.sql.SQLException
     */
    public abstract void update(ProgramSlot input, ProgramSlot origin)
            throws InvalidDataException, DuplicateException, InUseException, NotFoundException, SQLException;
}
