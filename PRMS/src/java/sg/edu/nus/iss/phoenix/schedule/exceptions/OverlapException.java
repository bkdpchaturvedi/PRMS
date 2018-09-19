/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.exceptions;

import sg.edu.nus.iss.phoenix.core.exceptions.*;

/**
 * DuplicateException exception. This exception will be thrown from ProgramSlot
 * DAO object when creating for new Program Slot if there is a Program Slot
 * record has existed between creating Program Slot date time and duration
 *
 */
/**
 *
 * @author MyatMin
 */
public class OverlapException extends Exception {

    /**
     * Constructor for DuplicateException. The input message is returned in
     * toString() message.
     */
    public OverlapException(String message) {
        super(message);
    }
}
