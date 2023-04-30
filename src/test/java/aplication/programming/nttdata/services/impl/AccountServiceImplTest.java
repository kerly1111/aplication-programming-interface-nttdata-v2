package aplication.programming.nttdata.services.impl;

import aplication.programming.nttdata.MockUtils;
import aplication.programming.nttdata.common.exception.NttdataError;
import aplication.programming.nttdata.repository.AccountRepository;
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
@ContextConfiguration(classes = {AccountServiceImplTest.class})
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void create() {
        Mockito.when(clientRepository.findByIdentification(Mockito.any()))
                        .thenReturn(Mono.just(MockUtils.buildClient()));
        Mockito.when(accountRepository.save(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        StepVerifier.create(accountService.create(MockUtils.buildAccountRequestVO()))
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void createFail() {
        Mockito.when(clientRepository.findByIdentification(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        Mockito.when(accountRepository.save(Mockito.any()))
                .thenReturn(Mono.error(NttdataError.NTT004));
        StepVerifier.create(accountService.create(MockUtils.buildAccountRequestVO()))
                .expectError()
                .verify();
    }

    @Test
    void createFailClient() {
        Mockito.when(clientRepository.findByIdentification(Mockito.any()))
                .thenReturn(Mono.error(NttdataError.NTT004));
        StepVerifier.create(accountService.create(MockUtils.buildAccountRequestVO()))
                .expectError()
                .verify();
    }

    @Test
    void allAccount() {
        Mockito.when(accountRepository.findAll())
                .thenReturn(Flux.just(MockUtils.buildAccount()));
        Mockito.when(clientRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        StepVerifier.create(accountService.allAccount())
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void allAccountFail() {
        Mockito.when(accountRepository.findAll())
                .thenReturn(Flux.error(NttdataError.NTT004));
        StepVerifier.create(accountService.allAccount())
                .expectError()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(accountRepository.save(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(accountService.update(1L, MockUtils.buildAccountRequestVO()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateFail() {
        Mockito.when(accountRepository.save(Mockito.any()))
                .thenReturn(Mono.error(NttdataError.NTT004));
        StepVerifier.create(accountService.update(1L, MockUtils.buildAccountRequestVO()))
                .expectError()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(accountRepository.deleteById(Mockito.anyLong()))
                .thenReturn(Mono.empty());
        StepVerifier.create(accountService.delete(1L))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteFail() {
        Mockito.when(accountRepository.deleteById(Mockito.anyLong()))
                .thenReturn(Mono.error(NttdataError.NTT004));
        StepVerifier.create(accountService.delete(1L))
                .expectError()
                .verify();
    }
}