package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.MockUtils;
import aplication.programming.nttdata.model.Client;
import aplication.programming.nttdata.services.IClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ContextConfiguration(classes = {ClientControllerTest.class})
class ClientControllerTest {

    @Mock
    private IClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @Test
    void create() {
        Mockito.when(clientService.create(Mockito.any()))
                .thenReturn(Mono.just(new Client()));
        StepVerifier.create(clientController.create(MockUtils.buildClient()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void allClient() {
        Mockito.when(clientService.allClient())
                .thenReturn(Flux.just(MockUtils.buildClient()));
        StepVerifier.create(clientController.allClient())
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(clientService.update(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(clientController.update(1L, MockUtils.buildClient()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(clientService.delete(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(clientController.delete(1L))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }
}