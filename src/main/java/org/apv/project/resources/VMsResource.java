package org.apv.project.resources;

import com.azure.core.management.Region;
import com.azure.resourcemanager.compute.ComputeManager;
import com.azure.resourcemanager.compute.models.KnownLinuxVirtualMachineImage;
import com.azure.resourcemanager.compute.models.VirtualMachine;
import org.apv.project.credentials.Credentials;
import org.apv.project.entities.Managers;
import org.apv.project.entities.Printable;
import org.apv.project.entities.VM;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/azure/vm")
public class VMsResource {
    @Inject
    Managers managers;
    @Inject
    Credentials credentials;

    @GET
    public List<Printable> listVms(){
        return managers.getComputeManager().virtualMachines().list().mapPage(vm -> new Printable(vm.computerName(), vm.vmId(), vm.type())).stream().collect(Collectors.toList());
    }

    @GET
    @Path("/details")
    public VM getVmDetails(String vmId){
        return VM.fromVirtualMachine(managers.getComputeManager().virtualMachines().getById(vmId));
    }

    @PUT
    @Path("/deallocate")
    public Response deallocateVm(String vmId){
        try{
            ComputeManager manager = managers.getComputeManager();
            manager.virtualMachines().getById(vmId).deallocate();
            return Response.ok(VM.fromVirtualMachine(manager.virtualMachines().getById(vmId))).build();
        } catch(Exception e){
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/start")
    public Response startVm(String vmId){
        try{
            ComputeManager manager = managers.getComputeManager();
            manager.virtualMachines().getById(vmId).start();
            return Response.ok(VM.fromVirtualMachine(manager.virtualMachines().getById(vmId))).build();
        } catch(Exception e){
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/{id}")
    public VM createVm(@PathParam("id") String vmName){
        String RG_NAME="java-sdk-group";
        String PUB_SSH = credentials.getSshPubKey().get();
        VirtualMachine vm=  managers.getComputeManager().virtualMachines()
                .define(vmName)
                .withRegion(Region.EUROPE_WEST)
                .withNewResourceGroup(RG_NAME)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withoutPrimaryPublicIPAddress()
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_18_04_LTS)
                .withRootUsername("user1808")
                .withRootPassword("Standard_B1ls")
                .withSsh(PUB_SSH)
                .withSize("Standard_B1ls")
                .create();
        return VM.fromVirtualMachine(vm);
    }
}
