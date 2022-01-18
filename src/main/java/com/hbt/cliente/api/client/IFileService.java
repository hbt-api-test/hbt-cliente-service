package com.hbt.cliente.api.client;

import com.hbt.cliente.api.model.entity.FileName;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.springframework.web.bind.annotation.RequestBody;


import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
@RegisterRestClient
public interface IFileService {


    @DELETE
    @Path("/delete")
    @Produces(MediaType.TEXT_PLAIN)
    void delete(@RequestBody FileName fileName);

}
