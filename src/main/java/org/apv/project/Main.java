package org.apv.project;

import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.Region;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.resourcemanager.compute.ComputeManager;
import com.azure.resourcemanager.compute.models.KnownLinuxVirtualMachineImage;
import com.azure.resourcemanager.compute.models.VirtualMachineSizeTypes;
import com.azure.resourcemanager.network.NetworkManager;
import com.azure.resourcemanager.resources.ResourceManager;
import com.azure.resourcemanager.storage.StorageManager;

public class Main {

//    public static void main(String[] args) {
////        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
////                .clientId(AZURE_CLIENT_ID)
////                .clientSecret(AZURE_CLIENT_SECRET)
////                .tenantId(AZURE_TENANT_ID)
////                .build();
//        System.out.println("line 0, defining compute manager");
//        AzureProfile azureProfile= new AzureProfile(AzureEnvironment.AZURE);
//        ComputeManager computeManager = ComputeManager.authenticate(
//                new DefaultAzureCredentialBuilder().build(),
//                azureProfile
//                );
//        System.out.println("Tenant ID : "+azureProfile.getTenantId());
////        System.out.println("line 2 : creating vms");
////        computeManager.virtualMachines()
////                .define(VM_NAME)
////                .withRegion(Region.EUROPE_WEST)
////                .withNewResourceGroup(RG_NAME)
////                .withNewPrimaryNetwork("10.0.0.0/28")
////                .withPrimaryPrivateIPAddressDynamic()
////                .withoutPrimaryPublicIPAddress()
////                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_18_04_LTS)
////                .withRootUsername("user1808")
////                .withRootPassword("Standard_B1ls")
////                .withSsh(PUB_SSH)
////                .withSize("Standard_B1ls")
////                .create();
//
//        System.out.println("listing virtual machines");
//        computeManager
//                .virtualMachines()
//                .list()
//                .mapPage(vm -> "name : "+vm.name()+ " id: "+ vm.id())
//                .forEach(System.out::println);
//        System.out.println("listing networks");
//        NetworkManager
//                .authenticate(new DefaultAzureCredentialBuilder().build(), azureProfile)
//                .networks()
//                .list()
//                .mapPage(network -> "name : "+network.name()+ " id: "+ network.id())
//                .forEach(System.out::println);
////        System.out.println("listing disks");
////        StorageManager.authenticate(new DefaultAzureCredentialBuilder().build(), azureProfile).storageSkus().list().forEach(System.out::println);
//        System.out.println("listing all resources hopefully");
//        ResourceManager.authenticate(new DefaultAzureCredentialBuilder().build(), azureProfile)
//                .withSubscription(AZURE_SUBSCRIPTION_ID)
//                .genericResources().list().mapPage(rs -> "RS type: " +rs.resourceType()+" name: "+rs.name()+" id: "+rs.id())
//                .forEach(System.out::println);
//
//
//    }
//
}
