package aplication.programming.nttdata.services.impl;

import aplication.programming.nttdata.model.Client;
import aplication.programming.nttdata.repository.ClientRepository;
import aplication.programming.nttdata.services.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Slf4j
@Service
public class ClientServiceImpl implements IClientService {

    @Resource
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public Mono<Client> create(Client request) {
        return clientRepository.save(request)
                .doOnError(error -> log.error("Error {}", error.getMessage()));
    }

    @Override
    public Flux<Client> allClient() {
        return clientRepository.findAll()
                .doOnError(error -> log.error("Error {}", error.getMessage()));
    }

    @Override
    @Transactional
    public Mono<Void> update(Long idClient, Client request) {
       return clientRepository.save(request)
               .then();
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long idClient) {
       return clientRepository.deleteById(idClient);
    }
}
