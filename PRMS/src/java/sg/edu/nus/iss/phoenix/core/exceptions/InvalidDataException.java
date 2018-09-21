/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.core.exceptions;

/**
 * InvalidDataException. This exception will be thrown from DAO object if
 * creating for new object fails to create regarding of the primary key has been
 * existed.
 *
 */
/**
 *
 * @author MyatMin
 */
public class InvalidDataException extends Exception {

    /**
     * Constructor for InvalidDataException. The input message is returned in
     * toString() message.
     *
     * @param message
     */
    public InvalidDataException(String message) {
        super(message);
    }

    /**
     * Constructor for InvalidDataException with cause. The input message is
     * returned in toString() message.
     *
     * @param message
     * @param cause
     */
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
