package com.me.my_application.resource;

import com.me.my_application.entity.MyEntity;
import com.me.my_application.resource.api.MyEntityApi;
import com.me.my_application.resource.api.StatusApi;
import io.quarkus.logging.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/my-application")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiResource {

    @GET
    @Path("/")
    public List<MyEntityApi> get(@QueryParam("my_enum") StatusApi myEnum) {
        Log.info(myEnum);

        return MyEntity.<MyEntity>findAll().stream()
                .map(this::mapToMyEntityApi)
                .collect(Collectors.toList());
    }

    private MyEntityApi mapToMyEntityApi(MyEntity myEntity) {
        return new MyEntityApi(
                myEntity.uuid,
                myEntity.name,
                switch (myEntity.status) {
                    case STATUS_1 -> StatusApi.STATUS_1;
                    case STATUS_2 -> StatusApi.STATUS_2;
                }
        );
    }

}