package aplication.programming.nttdata.services.impl;

import aplication.programming.nttdata.MockUtils;
import aplication.programming.nttdata.common.exception.NttdataError;
import aplication.programming.nttdata.repository.AccountRepository;
import aplication.programming.nttdata.repository.ClientRepository;
import aplication.programming.nttdata.repository.MovementRepository;
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

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
@ContextConfiguration(classes = {MovementServiceImplTest.class})
class MovementServiceImplTest {

    @Mock
    private MovementRepository movementRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private MovementServiceImpl movementService;

    @Test
    void create() {
        Mockito.when(accountRepository.findByAccountNumberByAccountType(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        Mockito.when(movementRepository.getBalance(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        Mockito.when(movementRepository.save(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildMovement()));
        StepVerifier.create(movementService.create(MockUtils.buildMovementRequestVO()))
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void createFail() {
        Mockito.when(accountRepository.findByAccountNumberByAccountType(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccountFail()));
        Mockito.when(movementRepository.getBalance(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(800.00));
        Mockito.when(movementRepository.save(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildMovement()));
        StepVerifier.create(movementService.create(MockUtils.buildMovementRequestVO()))
                .expectError()
                .verify();
    }

    @Test
    void createFailSave() {
        Mockito.when(accountRepository.findByAccountNumberByAccountType(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        Mockito.when(movementRepository.getBalance(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(800.00));
        Mockito.when(movementRepository.save(Mockito.any()))
                .thenReturn(Mono.error(NttdataError.NTT011));
        StepVerifier.create(movementService.create(MockUtils.buildMovementRequestVO()))
                .expectError()
                .verify();
    }

    @Test
    void allAccount() {
        Mockito.when(movementRepository.findAll())
                .thenReturn(Flux.just(MockUtils.buildMovement()));
        Mockito.when(accountRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        StepVerifier.create(movementService.allMovement())
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(accountRepository.findByAccountNumberByAccountType(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccount()));
        Mockito.when(movementRepository.getBalance(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        Mockito.when(movementRepository.save(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(movementService.update(1L, MockUtils.buildMovementRequestVO()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateFail() {
        Mockito.when(accountRepository.findByAccountNumberByAccountType(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildAccountFail()));
        Mockito.when(movementRepository.getBalance(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        Mockito.when(movementRepository.save(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(movementService.update(1L, MockUtils.buildMovementRequestVO()))
                .expectError()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(movementRepository.deleteById(Mockito.anyLong()))
                .thenReturn(Mono.empty());
        StepVerifier.create(movementService.delete(1L))
                .expectComplete()
                .verify();
    }

    @Test
    void report() {
        Mockito.when(clientRepository.findByIdentification(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        Mockito.when(accountRepository.findAccountByIdClient(Mockito.any()))
                .thenReturn(Flux.just(MockUtils.buildAccount()));
        Mockito.when(movementRepository.getBalanceByDate(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        Mockito.when(movementRepository.getBalancePrevious(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        StepVerifier.create(movementService.report(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "123"))
                .consumeNextWith(Assertions::assertNotNull)
                .expectComplete()
                .verify();
    }

    @Test
    void reportFail() {
        Mockito.when(clientRepository.findByIdentification(Mockito.any()))
                .thenReturn(Mono.just(MockUtils.buildClient()));
        Mockito.when(accountRepository.findAccountByIdClient(Mockito.any()))
                .thenReturn(Flux.error(NttdataError.NTT012));
        Mockito.when(movementRepository.getBalanceByDate(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        Mockito.when(movementRepository.getBalancePrevious(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Mono.just(200.00));
        StepVerifier.create(movementService.report(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "123"))
                .expectError()
                .verify();
    }
}