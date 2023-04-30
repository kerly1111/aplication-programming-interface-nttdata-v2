package aplication.programming.nttdata.services.impl;

import aplication.programming.nttdata.MockUtils;
import aplication.programming.nttdata.common.exception.NttdataError;
import aplication.programming.nttdata.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@SpringBootTest
@ContextConfiguration(classes = {ClientServiceImplTest.class})
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void create(){
        Mockito.when(clientRepository.save(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        StepVerifier.create(clientService.create(MockUtils.buildClient()))
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void createFail(){
        Mockito.when(clientRepository.save(Mockito.any()))
                .thenReturn(Mono.error(NttdataError.NTT001));
        StepVerifier.create(clientService.create(MockUtils.buildClient()))
                .expectError()
                .verify();
    }

    @Test
    void allClient() {
        Mockito.when(clientRepository.findAll())
                .thenReturn(Flux.just(MockUtils.buildClient()));
        StepVerifier.create(clientService.allClient())
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void allClientFail() {
        Mockito.when(clientRepository.findAll())
                .thenReturn(Flux.error(NttdataError.NTT007));
        StepVerifier.create(clientService.allClient())
                .expectError()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(clientRepository.save(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(clientService.update(1L, MockUtils.buildClient()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateFail() {
        Mockito.when(clientRepository.save(Mockito.any()))
                .thenReturn(Mono.error(NttdataError.NTT008));
        StepVerifier.create(clientService.update(1L, MockUtils.buildClient()))
                .expectError()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(clientRepository.deleteById(Mockito.anyLong()))
                .thenReturn(Mono.empty());
        StepVerifier.create(clientService.delete(1L))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteFail() {
        Mockito.when(clientRepository.deleteById(Mockito.anyLong()))
                .thenReturn(Mono.error(NttdataError.NTT009));
        StepVerifier.create(clientService.delete(1L))
                .expectError()
                .verify();
    }
}