/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.restful;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;
import sg.edu.nus.iss.phones.core.restful.JSONEnvelop;

/**
 * REST Web Service
 *
 * @author MyatMin
 */
@Path("schedule")
@RequestScoped
public class ScheduleRESTService {

    @Context
    private UriInfo context;
    
    private ScheduleService service;

    /**
     * Creates a new instance of ScheduleRESTService
     */
    public ScheduleRESTService() {
        service = new ScheduleService();
    }

    /**
     * Retrieves representation of an instance of resource
     * @param dateOfProgram
     * @return an instance of program slot
     */
    @GET
    @Path("/{dateofProgram}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONEnvelop<ProgramSlot> getProgramSlot(@PathParam("dateofProgram") Timestamp dateOfProgram) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONEnvelop<ArrayList<ProgramSlot>> getAllRadioPrograms() {
        
        JSONEnvelop<ArrayList<ProgramSlot>> result;
        result = new JSONEnvelop<>();
        result.setData(new ArrayList<> ());
        return result;        
    }

    /**
     * PUT method for updating or creating an instance of ScheduleRESTService
     * @param content representation for the resource
     */
    @PUT
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}