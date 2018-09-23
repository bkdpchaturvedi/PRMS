/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.core.restful;

import java.io.Serializable;

/**
 *
 * @author MyatMin
 */
public class ErrorConstant implements Serializable {

    /**
     * Static final variables. This data to define the error codes for
     * responding error messages from REST services.
     */
    public static final String ERR_RUNTIME = "RUNTIME";
    public static final String ERR_DUPLICATE = "DUPLICATE";
    public static final String ERR_INUSE = "INUSE";
    public static final String ERR_INVALID = "INVALID";
    public static final String ERR_NOTFOUND = "NOTFOUND";
    public static final String ERR_SERVICE = "SERVICE";
    public static final String ERR_SCHEDULE_OVERLAP = "SCHEDULE_OVERLAP";
}
