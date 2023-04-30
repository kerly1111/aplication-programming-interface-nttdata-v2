package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.services.IMovementService;
import aplication.programming.nttdata.vo.request.MovementRequestVO;
import aplication.programming.nttdata.vo.response.MovementResponseVO;
import aplication.programming.nttdata.vo.response.StatementResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.sql.Date;

@RestController
@RequestMapping("/movimientos")
public class MovementController {

    @Resource
    private IMovementService movementService;

    @PostMapping
    public Mono<ResponseEntity<MovementResponseVO>> create(@RequestBody MovementRequestVO request) {
        return movementService.create(request)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<MovementResponseVO>>> allMovement() {
        return Mono.just(ResponseEntity.ok().body(movementService.allMovement()));
    }

    @PutMapping("/{idMovement}")
    public Mono<ResponseEntity<String>> update(@PathVariable Long idMovement, @RequestBody MovementRequestVO request) {
        return movementService.update(idMovement, request)
                .thenReturn(ResponseEntity.ok().body("Movement updated successfully"));
    }

    @DeleteMapping("/{idMovement}")
    public Mono<ResponseEntity<String>> delete(@PathVariable Long idMovement) {
        return movementService.delete(idMovement)
                .thenReturn(ResponseEntity.ok().body("Move successfully removed"));
    }

    @GetMapping("/report")
    public Mono<ResponseEntity<Flux<StatementResponseVO>>> report(@RequestParam Date dateStart, @RequestParam Date dateEnd,
                                            @RequestParam String identification) {
        return Mono.just(ResponseEntity.ok().body(movementService.report(dateStart, dateEnd, identification)));
    }

}
