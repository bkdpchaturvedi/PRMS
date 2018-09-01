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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Error(String description, String error) {
        this.description = description;
        this.error = error;
    }

}
