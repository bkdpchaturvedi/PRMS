/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.restful;

import java.util.ArrayList;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;
import sg.edu.nus.iss.phoenix.core.restful.JSONEnvelop;

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
     *
     * @param dateOfProgram date time of the resource
     * @return an instance of JSONEnvelop of program slot
     */
    @GET
    @Path("/{dateOfProgram}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONEnvelop<ProgramSlot> getProgramSlot(@PathParam("dateOfProgram") String dateOfProgram) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves representation of all instances of resource
     *
     * @return an instance of JSONEnvelop of program slot array list
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONEnvelop<ArrayList<ProgramSlot>> getAllProgramSlots() {

        JSONEnvelop<ArrayList<ProgramSlot>> result;
        result = new JSONEnvelop<>();
        result.setData(new ArrayList<>());
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * POST method for creating an instance of resource
     *
     * @param input representation for the resource
     * @param username representation for the actor's username
     */
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<Boolean> creaeProgramSlot(ProgramSlot input) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of resource
     *
     * @param content representation for the resource
     */
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<Boolean> updateRadioProgram(ProgramSlot input) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * DELETE method for deleting an instance of resource
     *
     * @param dateOfProgram date time of the resource
     */
    @DELETE
    @Path("/delete/{dateOfProgram}")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<Boolean> deleteRadioProgram(@PathParam("dateOfProgram") String dateOfProgram) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves representation of filtered instances of resource
     *
     * @param filter date for the filtering the resource
     * @return
     */
    @GET
    @Path("/")
    public JSONEnvelop<List<ProgramSlot>> findProgramSlots(@QueryParam("date") String filter) {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
}
