package aplication.programming.nttdata.repository;

import aplication.programming.nttdata.model.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<Client, Long> {

    Mono<Client> findByIdentification(String identification);
}
