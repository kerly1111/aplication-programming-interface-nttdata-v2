package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.MockUtils;
import aplication.programming.nttdata.services.IAccountService;
import aplication.programming.nttdata.vo.response.AccountResponseVO;
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
@ContextConfiguration(classes = {AccountControllerTest.class})
class AccountControllerTest {

    @Mock
    private IAccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    void create() {
        Mockito.when(accountService.create(Mockito.any()))
                .thenReturn(Mono.just(new AccountResponseVO()));
        StepVerifier.create(accountController.create(MockUtils.buildAccountRequestVO()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void allAccount() {
        Mockito.when(accountService.allAccount())
                .thenReturn(Flux.just(MockUtils.buildAccountResponseVO()));
        StepVerifier.create(accountController.allAccount())
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(accountService.update(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(accountController.update(1L, MockUtils.buildAccountRequestVO()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(accountService.delete(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(accountController.delete(1L))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }
}