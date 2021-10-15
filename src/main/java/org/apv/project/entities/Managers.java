package org.apv.project.entities;

import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.resourcemanager.compute.ComputeManager;
import com.azure.resourcemanager.network.NetworkManager;
import com.azure.resourcemanager.resources.ResourceManager;
import lombok.Getter;
import lombok.extern.java.Log;
import lombok.extern.jbosslog.JBossLog;
import lombok.extern.log4j.Log4j;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Map;

@ApplicationScoped
@JBossLog
@Getter
public class Managers {
    private AzureProfile azureProfile;
    private String subscriptionId;
    private String tenantId;
    private String clientId;
    private String clientSecret;

    public ComputeManager computeManager;
    public NetworkManager networkManager;
    public ResourceManager resourceManager;

    @PostConstruct
    public Managers getInstance(){
        System.out.println
                ("initiating managers");
        subscriptionId = ConfigProvider.getConfig().getValue("AZURE.SUBSCRIPTION.ID",String.class);
        tenantId = ConfigProvider.getConfig().getValue("AZURE.TENANT.ID",String.class);
        clientId = ConfigProvider.getConfig().getValue("AZURE.CLIENT.ID",String.class);
        clientSecret = ConfigProvider.getConfig().getValue("AZURE.CLIENT.SECRET",String.class);

        System.out.println("subscriptionId : "+subscriptionId);
        System.out.println("tenantId : "+tenantId);
        System.out.println("clientId : "+clientId);

        var map = Map.of("AZURE_CLIENT_ID", clientId, "AZURE_CLIENT_SECRET", clientSecret);
        azureProfile=new AzureProfile(subscriptionId,tenantId,AzureEnvironment.AZURE);

        var clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        computeManager = ComputeManager.authenticate(
                clientSecretCredential,
                azureProfile
        );
        if (computeManager==null) System.out.println("compute manager null");
        networkManager = NetworkManager
                .authenticate(clientSecretCredential, azureProfile);
        if (networkManager==null) System.out.println("network manager null");
        resourceManager = ResourceManager
                .authenticate(clientSecretCredential, azureProfile)
                .withSubscription(subscriptionId);
        if (resourceManager==null) System.out.println("resource manager null");
        System.out.println("done initiating managers");
        return this;
    }
}
