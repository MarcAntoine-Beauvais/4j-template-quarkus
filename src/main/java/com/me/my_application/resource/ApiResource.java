package com.me.my_application.resource;

import com.me.my_application.resource.api.StatusApi;
import io.quarkus.logging.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/my-application")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiResource {

    @GET
    @Path("/")
    public StatusApi get(@QueryParam("my_enum") StatusApi myEnum) {
        Log.info(myEnum);
        return myEnum;
    }

}