/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.restful;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;
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
import sg.edu.nus.iss.phoenix.utilities.DateHelper;
import sg.edu.nus.iss.phoenix.utilities.ErrorHelper;

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
    private static final Logger LOG = Logger.getLogger(ScheduleRESTService.class.getName());

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
    public JSONEnvelop<ProgramSlot> getProgramSlot(
            @PathParam("dateOfProgram") String dateOfProgram
    ) {
        JSONEnvelop<ProgramSlot> result = new JSONEnvelop<>();
        try {;
            result.setData(
                    service.getProgramSlot(DateHelper.getUTC(dateOfProgram))
            );
        } catch (Exception e) {
            result.setError(ErrorHelper.createError(e, LOG));
        }
        return result;
    }

    /**
     * Retrieves representation of all instances of resource
     *
     * @return an instance of JSONEnvelop of program slot array list
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONEnvelop<List<ProgramSlot>> getAllProgramSlots() {
        JSONEnvelop<List<ProgramSlot>> result;
        result = new JSONEnvelop<>();
        try {
            result.setData(service.getAllProgramSlots());
        } catch (Exception e) {
            result.setError(ErrorHelper.createError(e, LOG));
        }
        return result;
    }

    /**
     * POST method for creating an instance of resource
     *
     * @param input representation for the resource
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<Boolean> creaeProgramSlot(ProgramSlot input) {
        JSONEnvelop<Boolean> result = new JSONEnvelop<>();
        try {
            input.setDateOfProgram(DateHelper.getUTC(input.getDateOfProgram().toString()));
            service.createProgramSlot(input);
            result.setData(true);
        } catch (Exception e) {
            result.setError(ErrorHelper.createError(e, LOG));
        }
        return result;
    }

    /**
     * PUT method for updating or creating an instance of resource
     *
     * @param input
     * @return
     */
    @PUT
    @Path("/{dateOfProgram}")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<Boolean> updateRadioProgram(@PathParam("dateOfProgram") String dateOfProgram, ProgramSlot input) {
        JSONEnvelop<Boolean> result = new JSONEnvelop<>();
        try {
            input.setDateOfProgram(DateHelper.getUTC(input.getDateOfProgram().toString()));
            service.updateProgramSlot(input, DateHelper.getUTC(dateOfProgram));
            result.setData(true);
        } catch (Exception e) {
            result.setError(ErrorHelper.createError(e, LOG));
        }
        return result;
    }

    /**
     * DELETE method for deleting an instance of resource
     *
     * @param dateOfProgram date time of the resource
     * @return
     */
    @DELETE
    @Path("/{dateOfProgram}")
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<Boolean> deleteRadioProgram(
            @PathParam("dateOfProgram") String dateOfProgram
    ) {
        JSONEnvelop<Boolean> result = new JSONEnvelop<>();
        try {
            service.deleteProgramSlot(DateHelper.getUTC(dateOfProgram));
            result.setData(true);
        } catch (Exception e) {
            result.setError(ErrorHelper.createError(e, LOG));
        }
        return result;
    }

    /**
     * Retrieves representation of filtered instances of resource
     *
     * @param dateOfProgram date for th e filtering the resource
     * @return
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONEnvelop<List<ProgramSlot>> findProgramSlots(
            @QueryParam("dateOfProgram") String dateOfProgram
    ) {
        JSONEnvelop<List<ProgramSlot>> result;
        result = new JSONEnvelop<>();
        try {
            result.setData(service.findProgramSlots(
                    DateHelper.getUTC(dateOfProgram, ChronoUnit.DAYS)
            ));
        } catch (Exception e) {
            result.setError(ErrorHelper.createError(e, LOG));
        }

        //TODO return proper representation object
        return result;
    }
}
