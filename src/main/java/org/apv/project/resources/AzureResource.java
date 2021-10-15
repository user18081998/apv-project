package org.apv.project.resources;

import lombok.extern.java.Log;
import org.apv.project.entities.Managers;
import org.apv.project.entities.Printable;
import org.w3c.dom.ls.LSOutput;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/azure")
@RequestScoped
@Log
public class AzureResource {
    @Inject
    Managers managers;

    @GET
    public List<Printable> getAllResources(){
        return
                managers.getInstance().getResourceManager()
                        .genericResources()
                        .list()
                        .mapPage(rs -> new Printable(rs.name(), rs.id(), rs.type()))
                        .stream()
                        .collect(Collectors.toList());
    }

    @PUT
    public List<Printable> deleteResource(String id){
        managers.getInstance().resourceManager.genericResources().deleteById(id);
        return getAllResources();
    }
}


