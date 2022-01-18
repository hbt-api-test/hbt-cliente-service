package com.hbt.cliente.api.config;

import io.quarkus.runtime.StartupEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.otaibe.commons.quarkus.eureka.client.service.EurekaClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
@Getter
@Setter
@Slf4j
public class EagerBeansLoader {

    @Inject
    EurekaClient eurekaClient;

    public void init(@Observes StartupEvent event) {
        log.info("init start");
        getEurekaClient().registerApp(); //not enough just to inject bean ...
        log.info("init end");
    }

}
