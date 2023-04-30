package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.model.Client;
import aplication.programming.nttdata.services.IClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Resource
    private IClientService clientService;

    @PostMapping
    public Mono<ResponseEntity<Client>> create(@RequestBody Client request){
        return clientService.create(request)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Client>>> allClient(){
        return Mono.just(ResponseEntity.ok().body(clientService.allClient()));
    }

    @PutMapping("/{idClient}")
    public Mono<ResponseEntity<String>> update(@PathVariable Long idClient, @RequestBody Client request){
        return clientService.update(idClient, request)
                .thenReturn(ResponseEntity.ok().body("User updated successfully"));
    }

    @DeleteMapping("/{idClient}")
    public Mono<ResponseEntity<String>> delete(@PathVariable Long idClient){
        return clientService.delete(idClient)
                .thenReturn(ResponseEntity.ok().body("User deleted successfully"));
    }
}
