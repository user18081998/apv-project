package org.apv.project.entities;

import com.azure.resourcemanager.compute.models.VirtualMachine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VM {
    private String id;
    private String name;
    private String computerName;
    private String os;
    private String size;
    private String status;
//    private String osProfile;
    public static VM fromVirtualMachine(VirtualMachine vm){
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
}
