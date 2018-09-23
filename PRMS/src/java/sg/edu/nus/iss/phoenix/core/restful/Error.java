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
public class Error implements Serializable {

    private String description;
    private String error;

    /**
     * Get- and Set-methods for persistent variables. The default behaviour does
     * not make any checks against malformed data, so these might require some
     * manual additions.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Constructors. The first one takes no arguments and provides the most
     * simple way to create object instance. The another one takes arguments,
     * which is the details attributes of the object instance.
     */
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Error(String error, String description) {
        this.error = error;
        this.description = description;
    }

}
