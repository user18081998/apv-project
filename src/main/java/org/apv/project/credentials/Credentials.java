package org.apv.project.credentials;

import lombok.Getter;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
@Getter
@Log
public class Credentials {
    private String clientId;
    private String clientSecret;
    private String subscriptionId;
    private String tenantId;

    private Optional<String> sshPubKey;

    @PostConstruct
    public void init(){
        log.info("initializing credentials");
        subscriptionId = ConfigProvider.getConfig().getValue("AZURE.SUBSCRIPTION.ID",String.class);
        tenantId = ConfigProvider.getConfig().getValue("AZURE.TENANT.ID",String.class);
        clientId = ConfigProvider.getConfig().getValue("AZURE.CLIENT.ID",String.class);
        clientSecret = ConfigProvider.getConfig().getValue("AZURE.CLIENT.SECRET",String.class);
        sshPubKey = ConfigProvider.getConfig().getOptionalValue("VM.SSH.PUB",String.class);

        this.log.info("subscriptionId : "+subscriptionId);
        this.log.info("tenantId : "+tenantId);
        this.log.info("clientId : "+clientId);
        log.info("finished initilizing credentials");
    }
}
