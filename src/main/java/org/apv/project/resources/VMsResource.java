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
        VirtualMachine vm = managers.getComputeManager().virtualMachines().getById(vmId);
        VM n = new VM();
        n.setId(vm.vmId());
        n.setName(vm.name());
        n.setComputerName(vm.computerName());
        n.setOs(vm.osType().toString());
//        n.setOsProfile();
        n.setSize(vm.size().toString());
        n.setStatus(vm.powerState().toString().split("/")[1]);
        return n;
    }

    @PUT
    @Path("/deallocate")
    public Response deallocateVm(String vmId){
        try{
            managers.getComputeManager().virtualMachines().getById(vmId).deallocate();
        } catch(Exception e){
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/start")
    public Response startVm(String vmId){
        try{
            managers.getComputeManager().virtualMachines().getById(vmId).start();
        } catch(Exception e){
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/{id}")
    public Printable createVm(@PathParam("id") String vmName){
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
        return new Printable(vm.name(),vm.id(),vm.type());
    }
}
