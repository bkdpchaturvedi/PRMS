/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 * AnnualSchedule Value Object. This class is value object representing database
 * table program-slot. This class is intented to be used together with 
 * associated Dao object.
 * @author MyatMin
 */
public class ProgramSlot implements Serializable, Cloneable {
    
    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private Timestamp dateOfProgram;
    private Integer duration;
    private RadioProgram radioProgram;
    private User presenter;
    private User producer;

    /** 
     * Constructors. 
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key and data of the corresponding table.
     */
    public ProgramSlot() {
    }

    public ProgramSlot(Timestamp dateOfProgram, Integer duration, RadioProgram radioProgram, User presenter, User producer) {
        this.dateOfProgram = dateOfProgram;
        this.duration = duration;
        this.radioProgram = radioProgram;
        this.presenter = presenter;
        this.producer = producer;
    }

    
    
    /** 
     * Get- and Set-methods for persistent variables. The default
     * behaviour does not make any checks against malformed data,
     * so these might require some manual additions.
     * @return 
     */   
    public Timestamp getDateOfProgram() {
        return dateOfProgram;
    }

    public void setDateOfProgram(Timestamp dateOfProgram) {
        this.dateOfProgram = dateOfProgram;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public RadioProgram getRadioProgram() {
        return radioProgram;
    }

    public void setRadioProgram(RadioProgram radioProgram) {
        this.radioProgram = radioProgram;
    }

    public User getPresenter() {
        return presenter;
    }

    public void setPresenter(User presenter) {
        this.presenter = presenter;
    }

    public User getProducer() {
        return producer;
    }

    public void setProducer(User producer) {
        this.producer = producer;
    }
    
    /** 
     * hasEqualMapping-method will compare two RadioProgram instances
     * and return true if they contain same values in all persistent instance 
     * variables. If hasEqualMapping returns true, it does not mean the objects
     * are the same instance. However it does mean that in that moment, they 
     * are mapped to the same row in database.
     * @param valueObject
     * @return 
     */
    public boolean hasEqualMapping(ProgramSlot valueObject) {
        if (this.dateOfProgram == null) {
            if (valueObject.getDateOfProgram()!= null)
                return(false);
        } else if (!this.dateOfProgram.equals(valueObject.getDateOfProgram())) {
            return(false);
        }
        if (this.duration == null) {
            if (valueObject.getDuration()!= null)
                return(false);
        } else if (!this.duration.equals(valueObject.getDuration())) {
                return(false);
        }
        if (this.radioProgram == null) {
            if (valueObject.getRadioProgram()!= null)
                return(false);
        } else if (!this.radioProgram.equals(valueObject.getRadioProgram())) {
            return(false);
        }
        if (this.presenter == null) {
            if (valueObject.getPresenter()!= null)
                return(false);
        } else if (!this.presenter.equals(valueObject.getPresenter())) {
            return(false);
        }
        if (this.producer == null) {
            if (valueObject.getPresenter()!= null)
                return(false);
        } else if (!this.producer.equals(valueObject.getProducer())) {
            return(false);
        }
        return true;
    }

    /**
     * toString will return String object representing the state of this 
     * valueObject. This is useful during application development, and 
     * possibly when application is writing object states in text log.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("\nAnnualScheudle class, mapping to table annual-scheudle\n");
        out.append("Persistent attributes: \n"); 
        out.append("dateOfProgram = ").append(this.dateOfProgram).append("\n"); 
        out.append("duration = ").append(this.duration).append("\n");
        out.append("radioProgram = ").append(this.radioProgram.getName()).append("\n");
        out.append("presenter = ").append(this.presenter.getName()).append("\n");
        out.append("producer = ").append(this.producer.getName()).append("\n");
        
        return out.toString();
    }

    /**
     * Clone will return identical deep copy of this valueObject.
     * Note, that this method is different than the clone() which
     * is defined in java.lang.Object. Here, the returned cloned object
     * will also have all its attributes cloned.
     * @return 
     * @throws java.lang.CloneNotSupportedException 
     */    
    @Override
    protected Object clone() throws CloneNotSupportedException {
         ProgramSlot cloned = new ProgramSlot();

        if (this.dateOfProgram != null)
             cloned.setDateOfProgram(this.dateOfProgram); 
        if (this.duration != null)
             cloned.setDuration(this.duration); 
        return cloned;
    }

}