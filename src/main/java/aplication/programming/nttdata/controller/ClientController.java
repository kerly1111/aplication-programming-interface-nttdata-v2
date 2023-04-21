package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.model.Client;
import aplication.programming.nttdata.services.IClientService;
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
    public Mono<Client> create(@RequestBody Client request){
        return this.clientService.create(request);
    }

    @GetMapping
    public Flux<Client> allClient(){
        return this.clientService.allClient();
    }

    @PutMapping("/{idClient}")
    public String update(@PathVariable Long idClient, @RequestBody Client request){
        this.clientService.update(idClient, request);
        return "Usuario actualizado exitosamente";
    }

    @DeleteMapping("/{idClient}")
    public String delete(@PathVariable Long idClient){
        this.clientService.delete(idClient);
        return "Usuario eliminado exitosamente";
    }
}
