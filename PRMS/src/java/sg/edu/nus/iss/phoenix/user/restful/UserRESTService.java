/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.restful;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
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
  
}
