/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * WeeklySchedule Value Object. This class is value object representing database
 * table annual-schedule. This class is intented to be used together with 
 * associated Dao object.
 * @author MyatMin
 */
public class WeeklySchedule implements Serializable, Cloneable {
    
    /** 
     * Persistent Instance variables. This data is directly 
     * mapped to the columns of database table.
     */
    private LocalDate startDate;
    private String assignedBy;

    /** 
     * Constructors. 
     * The first one takes no arguments and provides the most simple
     * way to create object instance. The another one takes one
     * argument, which is the primary key and data of the corresponding table.
     */
    public WeeklySchedule() {
    }

    public WeeklySchedule(LocalDate startDate, String assignedBy) {
        this.startDate = startDate;
        this.assignedBy = assignedBy;
    }
    
    /** 
     * Get- and Set-methods for persistent variables. The default
     * behaviour does not make any checks against malformed data,
     * so these might require some manual additions.
     * @return 
     */
    public LocalDate getSartDate() {
        return startDate;
    }

    public void setSartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
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
    public boolean hasEqualMapping(WeeklySchedule valueObject) {
        if (this.startDate == null) {
            if (valueObject.getSartDate()!= null)
                return(false);
        } else if (!this.startDate.equals(valueObject.getSartDate())) {
            return(false);
        }
        if (this.assignedBy == null) {
            if (valueObject.getAssignedBy()!= null)
                return(false);
        } else if (!this.assignedBy.equals(valueObject.getAssignedBy())) {
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
        out.append("\nWeeklySchedule class, mapping to table annual-scheudle\n");
        out.append("Persistent attributes: \n"); 
        out.append("startDate = ").append(this.startDate).append("\n"); 
        out.append("assignedBy = ").append(this.assignedBy).append("\n");
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
         WeeklySchedule cloned = new WeeklySchedule();

        if (this.startDate != null)
             cloned.setSartDate(this.startDate); 
        if (this.assignedBy != null)
             cloned.setAssignedBy(this.assignedBy); 
        return cloned;
    }

}
