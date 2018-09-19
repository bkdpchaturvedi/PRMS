/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.core.exceptions;

/**
 * ServiceException. This exception will be thrown from Service object
 * when the underlying DAO object from it thrown exception which to be replace with this un.
 *
 */
/**
 *
 * @author MyatMin
 */
public class ServiceException extends RuntimeException {

    /**
     * Constructor for ServiceException. The input message is returned in
     * toString() message.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
