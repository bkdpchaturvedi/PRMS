/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.utilities;

import sg.edu.nus.iss.phoenix.core.exceptions.DuplicateException;
import sg.edu.nus.iss.phoenix.core.exceptions.InUseException;
import sg.edu.nus.iss.phoenix.core.exceptions.InvalidDataException;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.core.exceptions.ServiceException;
import sg.edu.nus.iss.phoenix.core.restful.ErrorConstant;
import sg.edu.nus.iss.phoenix.core.restful.Error;
import sg.edu.nus.iss.phoenix.schedule.exceptions.OverlapException;

/**
 *
 * @author MyatMin
 */
public class ErrorHelper {

    public static Error createError(Exception e) {
        if (e instanceof DuplicateException) {
            return new Error(ErrorConstant.ERR_DUPLICATE, e.getMessage());
        }
        if (e instanceof InUseException) {
            return new Error(ErrorConstant.ERR_INUSE, e.getMessage());
        }
        if (e instanceof InvalidDataException) {
            return new Error(ErrorConstant.ERR_INVALID, e.getMessage());
        }
        if (e instanceof NotFoundException) {
            return new Error(ErrorConstant.ERR_NOTFOUND, e.getMessage());
        }
        if (e instanceof NotFoundException) {
            return new Error(ErrorConstant.ERR_NOTFOUND, e.getMessage());
        }
        if (e instanceof ServiceException) {
            return new Error(ErrorConstant.ERR_SERVICE, e.getMessage());
        }
        if (e instanceof OverlapException) {
            return new Error(ErrorConstant.ERR_SCHEDULE_OVERLAP, e.getMessage());
        }
        return new Error(ErrorConstant.ERR_RUNTIME, e.getMessage());
    }
}
