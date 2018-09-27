/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.restful;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;
import sg.edu.nus.iss.phoenix.core.restful.JSONEnvelop;
import sg.edu.nus.iss.phoenix.user.service.UserService;

/**
 * REST Web Service
 *
 * @author chaturbkdp
 */
@Path("user")
@RequestScoped
public class UserRESTService {

    @Context
    private UriInfo context;
    private UserService service;

    /**
     * Creates a new instance of UserRESTService
     */
    public UserRESTService() {
        service = new UserService();
    }

    /**
     * Retrieves representation of an instance of resource
     *
     * @param id
     * @return an instance of JSONEnvelop of User
     */
    @GET
    @Path("/getbyid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONEnvelop<User> getUser(@PathParam("id") String id) {
        JSONEnvelop<User> result = new JSONEnvelop<User>();
        try {
            result.setData(service.getUserById(id));
        } catch (Exception ex) {
            result.setError(new sg.edu.nus.iss.phoenix.core.restful.Error("Error while Retrieving users ", ex.getMessage()));
        }

        return result;
    }

    /**
     * Retrieves representation of all instances of resource
     *
     * @return an instance of JSONEnvelop of user array list
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONEnvelop<List<User>> getAllUsers() {

        JSONEnvelop<List<User>> result;
        result = new JSONEnvelop<>();

        try {
            result.setData(service.getAllUsers());
        } catch (Exception ex) {
            result.setError(new sg.edu.nus.iss.phoenix.core.restful.Error("Error while Retrieving users ", ex.getMessage()));
        }

        //TODO return proper representation object
        return result;
        // throw new UnsupportedOperationException();
    }

     /**
     * Retrieves representation of all instances of resource
     *
     * @return an instance of JSONEnvelop of presenter array list  
     */
    @GET
    @Path("/presenters")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONEnvelop<List<User>> getAllPresenters() {

        JSONEnvelop<List<User>> result;
        result = new JSONEnvelop<>();

        try {
            result.setData(service.findAllPresenters());
        } catch (Exception ex) {
            result.setError(new sg.edu.nus.iss.phoenix.core.restful.Error("Error while Retrieving presenters ", ex.getMessage()));
        }

        //TODO return proper representation object
        return result;
        // throw new UnsupportedOperationException();
    }
    
     /**
     * Retrieves representation of all instances of resource
     *
     * @return an instance of JSONEnvelop of producer array list 
     */
    @GET
    @Path("/producers")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONEnvelop<List<User>> getAllProducers() {

        JSONEnvelop<List<User>> result;
        result = new JSONEnvelop<>();

        try {
            result.setData(service.findAllProducers());
        } catch (Exception ex) {
            result.setError(new sg.edu.nus.iss.phoenix.core.restful.Error("Error while Retrieving producers ", ex.getMessage()));
        }

        //TODO return proper representation object
        return result;
        // throw new UnsupportedOperationException();
    }
    
    /**
     * POST method for creating an instance of resource
     *
     * @param input representation for the resource
     */
    @PUT
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<Boolean> creaeUser(User input) {
        JSONEnvelop<Boolean> result = new JSONEnvelop<Boolean>();
        try {
            result.setData(service.createUser(input));
        } catch (Exception ex) {
            result.setError(new sg.edu.nus.iss.phoenix.core.restful.Error("Error while Creating users ", ex.getMessage()));
        }

        return result;
    }

    /**
     * PUT method for updating or creating an instance of resource
     *
     * @param input
     */
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<Boolean> updateUser(User input) {
        JSONEnvelop<Boolean> result = new JSONEnvelop<Boolean>();
        try {
            result.setData(service.updateUser(input));
        } catch (Exception ex) {
            result.setError(new sg.edu.nus.iss.phoenix.core.restful.Error("Error while Updating users ", ex.getMessage()));
        }

        return result;
    }

    /**
     * DELETE method for deleting an instance of resource
     *
     * @param id unique identifier for user
     */
    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<Boolean> deleteUser(
            @PathParam("id") String id
    ) {
      JSONEnvelop<Boolean> result = new JSONEnvelop<Boolean>();
        try {
            result.setData(service.deleteUser(id));
        } catch (Exception ex) {
            result.setError(new sg.edu.nus.iss.phoenix.core.restful.Error("Error while Deleting users ", ex.getMessage()));
        }

        return result;
    }

    /**
     * Retrieves representation of all instances of resource
     *
     * @return an instance of JSONEnvelop of producer array list
     */
    @GET
    @Path("/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONEnvelop<List<Role>> getAllRoles() {

        JSONEnvelop<List<Role>> result;
        result = new JSONEnvelop<>();

        try {
            result.setData(service.getAllRoles());
        } catch (Exception ex) {
            result.setError(new sg.edu.nus.iss.phoenix.core.restful.Error("Error while Retrieving producers ", ex.getMessage()));
        }

        //TODO return proper representation object
        return result;
        // throw new UnsupportedOperationException();
    }
}
