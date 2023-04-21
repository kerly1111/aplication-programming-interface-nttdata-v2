package aplication.programming.nttdata.services;

import aplication.programming.nttdata.vo.request.MovementRequestVO;
import aplication.programming.nttdata.vo.response.MovementResponseVO;
import aplication.programming.nttdata.vo.response.StatementResponseVO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.util.List;

@Validated
public interface IMovementService {

    Mono<MovementResponseVO> create(MovementRequestVO request);

    Flux<MovementResponseVO> allMovement();

    Mono<Void> update(Long idMovement, MovementRequestVO request);

    Mono<Void>  delete(Long idMovement);

    Flux<StatementResponseVO> report(Date dateStart, Date dateEnd, String identification);

}
