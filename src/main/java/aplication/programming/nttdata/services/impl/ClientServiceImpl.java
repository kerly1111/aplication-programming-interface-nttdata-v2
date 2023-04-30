package aplication.programming.nttdata.services.impl;

import aplication.programming.nttdata.common.exception.NttdataError;
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
                .onErrorResume(error -> {
                    log.error("An error occurred while creating the client. Detail = {}", error.getMessage());
                    return Mono.error(NttdataError.NTT006);
                })
                .doOnSuccess(success -> log.info("Client created successfully"));
    }

    @Override
    public Flux<Client> allClient() {
        return clientRepository.findAll()
                .onErrorResume(error -> {
                    log.error("An error occurred while searching for customers. Detail = {}", error.getMessage());
                    return Mono.error(NttdataError.NTT007);
                });
    }

    @Override
    @Transactional
    public Mono<Void> update(Long idClient, Client request) {
       return clientRepository.save(request)
               .onErrorResume(error -> {
                   log.error("An error occurred while updating the client. Detail = {}", error.getMessage());
                   return Mono.error(NttdataError.NTT008);
               })
               .doOnSuccess(success -> log.info("Client successfully upgraded"))
               .then();
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long idClient) {
       return clientRepository.deleteById(idClient)
               .onErrorResume(error -> {
                   log.error("An error occurred while deleting the client. Detail = {}", error.getMessage());
                   return Mono.error(NttdataError.NTT009);
               })
               .doOnSuccess(success -> log.info("Client successfully removed"));
    }
}
