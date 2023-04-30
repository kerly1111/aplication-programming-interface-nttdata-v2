package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.MockUtils;
import aplication.programming.nttdata.services.IMovementService;
import aplication.programming.nttdata.vo.response.MovementResponseVO;
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

import java.sql.Date;
import java.time.LocalDate;

@SpringBootTest
@ContextConfiguration(classes = {MovementControllerTest.class})
class MovementControllerTest {

    @Mock
    private IMovementService movementService;

    @InjectMocks
    private MovementController movementController;

    @Test
    void create() {
        Mockito.when(movementService.create(Mockito.any()))
                .thenReturn(Mono.just(new MovementResponseVO()));
        StepVerifier.create(movementController.create(MockUtils.buildMovementRequestVO()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void allAccount() {
        Mockito.when(movementService.allMovement())
                .thenReturn(Flux.just(MockUtils.buildMovementResponseVO()));
        StepVerifier.create(movementController.allMovement())
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Mockito.when(movementService.update(Mockito.any(), Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(movementController.update(1L, MockUtils.buildMovementRequestVO()))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        Mockito.when(movementService.delete(Mockito.any()))
                .thenReturn(Mono.empty());
        StepVerifier.create(movementController.delete(1L))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }

    @Test
    void report() {
        Mockito.when(movementService.report(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Flux.just(MockUtils.buildStatementResponseVO()));
        StepVerifier.create(movementController.report(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "123"))
                .consumeNextWith(response -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()))
                .expectComplete()
                .verify();
    }
}