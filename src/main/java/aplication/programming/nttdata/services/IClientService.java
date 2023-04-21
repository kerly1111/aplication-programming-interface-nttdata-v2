package aplication.programming.nttdata.services;

import aplication.programming.nttdata.model.Client;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Validated
public interface IClientService {

    Mono<Client> create(Client request);

    Flux<Client> allClient();

    Mono<Void> update(Long idClient, Client request);

    Mono<Void> delete(Long idClient);
}
