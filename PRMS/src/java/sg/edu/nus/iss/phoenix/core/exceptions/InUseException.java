/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.core.exceptions;

/**
 * InUseException exception. This exception will be thrown from DAO object
 * if deleting the records which is in use in other table of resource.
 *
 */
/**
 *
 * @author MyatMin
 */
public class InUseException extends Exception {

    /**
     * Constructor for InUseException. The input message is returned in
     * toString() message.
     *
     * @param message
     */
    public InUseException(String message) {
        super(message);
    }

    /**
     * Constructor for InUseException with cause. The input
     * message is returned in toString() message.
     *
     * @param message
     * @param cause
     */
    public InUseException(String message, Throwable cause) {
        super(message, cause);
    }

}
