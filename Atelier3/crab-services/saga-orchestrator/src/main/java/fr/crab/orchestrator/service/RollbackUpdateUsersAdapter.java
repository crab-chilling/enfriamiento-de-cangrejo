package fr.crab.orchestrator.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jvnet.hk2.annotations.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class RollbackUpdateUsersAdapter implements JavaDelegate {

    private final WebClient webClient;

    @Value("${gateway.url}")
    private String gatewayUrl;

    private ModelMapper modelMapper;

    public RollbackUpdateUsersAdapter() {
        this.modelMapper = new ModelMapper();
        this.webClient = WebClient.create();
    }


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //
    }
}
