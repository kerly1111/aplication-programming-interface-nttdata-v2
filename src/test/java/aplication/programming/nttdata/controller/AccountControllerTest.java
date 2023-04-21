package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.common.exception.NttdataException;
import aplication.programming.nttdata.services.IAccountService;
import aplication.programming.nttdata.vo.request.AccountRequestVO;
import aplication.programming.nttdata.vo.response.AccountResponseVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class AccountControllerTest {

    @Mock
    private IAccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private AccountRequestVO accountRequestVO;

    private AccountResponseVO accountResponseVO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        accountRequestVO = AccountRequestVO.builder()
                .id(Long.valueOf(1))
                .accountNumber("478758")
                .accountType("Ahorro")
                .initialBalance(200.00)
                .status(Boolean.TRUE)
                .identification("1234567890")
                .name("Jose Lema")
                .build();

        accountResponseVO = AccountResponseVO.builder()
                .accountNumber("478758")
                .accountType("Ahorro")
                .initialBalance(2000.00)
                .status(Boolean.TRUE)
                .name("Jose Lema")
                .build();
    }

    @Test
    void create() {
        Mockito.when(accountService.create(accountRequestVO)).thenReturn(Mono.just(new AccountResponseVO()));
    }

    @Test
    void allAccount() {
        Mockito.when(accountService.allAccount()).thenReturn(Flux.just(accountResponseVO));
        Assertions.assertNotNull(accountController.allAccount());
    }

    @Test
    void update() {
        Mockito.doThrow(new NttdataException()).when(accountService).update(accountRequestVO.getId(), accountRequestVO);
    }

    @Test
    void delete() {
        Mockito.doThrow(new NttdataException()).when(accountService).delete(accountRequestVO.getId());
    }
}