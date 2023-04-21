package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.services.IMovementService;
import aplication.programming.nttdata.vo.request.MovementRequestVO;
import aplication.programming.nttdata.vo.response.MovementResponseVO;
import aplication.programming.nttdata.vo.response.StatementResponseVO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovementController {

    @Resource
    private IMovementService movementService;

    @PostMapping
    public Mono<MovementResponseVO> create(@RequestBody MovementRequestVO request){
        return this.movementService.create(request);
    }

    @GetMapping
    public Flux<MovementResponseVO> allMovement(){
        return this.movementService.allMovement();
    }

    @PutMapping("/{idMovement}")
    public String update(@PathVariable Long idMovement, @RequestBody MovementRequestVO request){
        this.movementService.update(idMovement, request);
        return "Movimeinto actualizado exitosamente";
    }

    @DeleteMapping("/{idMovement}")
    public String delete(@PathVariable Long idMovement){
        this.movementService.delete(idMovement);
        return "Movimeinto eliminado exitosamente";
    }

    @GetMapping("/report")
    public Flux<StatementResponseVO> report(@RequestParam Date dateStart, @RequestParam Date dateEnd,
                                                  @RequestParam String identification){
        return this.movementService.report(dateStart, dateEnd, identification);
    }

}
