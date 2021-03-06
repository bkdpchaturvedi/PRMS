/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.core.exceptions;

/**
 * DuplicateException. This exception will be thrown from DAO object if creating
 * for new object fails to create regarding of the primary key has been existed.
 *
 */
/**
 *
 * @author MyatMin
 */
public class DuplicateException extends Exception {

    /**
     * Constructor for DuplicateException. The input message is returned in
     * toString() message.
     *
     * @param message
     */
    public DuplicateException(String message) {
        super(message);
    }

    /**
     * Constructor for DuplicateException with cause. The input message is
     * returned in toString() message.
     *
     * @param message
     * @param cause
     */
    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
