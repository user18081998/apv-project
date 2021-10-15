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
import org.apv.project.credentials.Credentials;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

@ApplicationScoped
@Log
@Getter
public class Managers {
    @Inject
    Credentials credentials;
//    private AzureProfile azureProfile;

    public ComputeManager computeManager;
    public NetworkManager networkManager;
    public ResourceManager resourceManager;

    public ResourceManager getResourceManager(){
        AzureProfile azureProfile= getAzureProfile();
        ClientSecretCredential clientSecretCredential = getClientSecretCredentials();
        return ResourceManager
                .authenticate(clientSecretCredential, azureProfile)
                .withSubscription(credentials.getSubscriptionId());
    }
    public NetworkManager getNeworkManager(){
        AzureProfile azureProfile= getAzureProfile();
        ClientSecretCredential clientSecretCredential = getClientSecretCredentials();
        return NetworkManager
                .authenticate(clientSecretCredential, azureProfile);
    }
    public ComputeManager getComputeManager(){
        AzureProfile azureProfile= getAzureProfile();
        ClientSecretCredential clientSecretCredential = getClientSecretCredentials();
        return ComputeManager.authenticate(
                clientSecretCredential,
                azureProfile
        );
    }
    private AzureProfile getAzureProfile(){
        return new AzureProfile(credentials.getTenantId(), credentials.getSubscriptionId(), AzureEnvironment.AZURE);
    }
    private ClientSecretCredential getClientSecretCredentials(){
        return new ClientSecretCredentialBuilder()
                .clientId(credentials.getClientId())
                .clientSecret(credentials.getClientSecret())
                .tenantId(credentials.getTenantId())
                .build();
    }
}
