package aplication.programming.nttdata.repository;

import aplication.programming.nttdata.model.Movement;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@Repository
public interface MovementRepository extends ReactiveCrudRepository<Movement, Long> {

    @Query("select sum(m.value) from movement m where m.fk_account = :idAccount and m.movement_type = :movementType")
    Mono<Double> getBalance(Long idAccount, String movementType);
    @Query("select sum(m.value) from movement m where m.fk_account = :idAccount and m.movement_type = :movementType and m.date < :dateStart")
    Mono<Double> getBalancePrevious(Long idAccount, String movementType, LocalDate dateStart);
    @Query("select sum(m.value) from movement m where m.fk_account = :idAccount and m.movement_type = :movementType and m.date between :dateStart and :dateEnd")
    Mono<Double> getBalanceByDate(Long idAccount, String movementType, LocalDate dateStart, LocalDate dateEnd);
}
