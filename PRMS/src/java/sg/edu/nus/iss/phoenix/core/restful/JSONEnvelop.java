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
public class JSONEnvelop<T extends Object> implements Serializable {

    private T data;
    private Error error;

    /**
     * Get- and Set-methods for persistent variables. The default behaviour does
     * not make any checks against malformed data, so these might require some
     * manual additions.
     *
     * @return
     */
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    /**
     * Constructors. The first one takes no arguments and provides the most
     * simple way to create object instance. The another one takes arguments,
     * which is the details attributes of the object instance.
     */
    public JSONEnvelop() {
    }

    public JSONEnvelop(T data, Error error) {
        this.data = data;
        this.error = error;
    }

}
