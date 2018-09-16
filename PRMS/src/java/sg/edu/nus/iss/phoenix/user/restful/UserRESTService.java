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
import sg.edu.nus.iss.phoenix.user.entity.User;
import sg.edu.nus.iss.phoenix.core.restful.JSONEnvelop;
import sg.edu.nus.iss.phoenix.user.service.UserService;

/**
 * REST Web Service
 *
 * @author chaturbkdp
 */
@Path("User")
@RequestScoped
public class UserRESTService {

    @Context
    private UriInfo context;
    private UserService service;

    /**
     * Creates a new instance of UserRESTService
     */
    public UserRESTService() {
        service=new UserService();
    }
    
    
    /**
     * Retrieves representation of an instance of resource
     *
     * @param id
     * @return an instance of JSONEnvelop of User
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONEnvelop<User> getUser(@PathParam("id") String id) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
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
     * POST method for creating an instance of resource
     *
     * @param input representation for the resource
     */
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<Boolean> creaeUser(User input) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of resource
     *
     * @param content representation for the resource
     */
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<Boolean> updateUser(User input) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
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
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
