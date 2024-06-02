package fr.crab.orchestrator.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class UpdateSoldCardAdapter implements JavaDelegate {
    private final WebClient webClient;

    @Value("${gateway.url}")
    private String gatewayUrl;

    private ModelMapper modelMapper;

    public UpdateSoldCardAdapter() {
        this.modelMapper = new ModelMapper();
        this.webClient = WebClient.create();
    }


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //
    }
}
