package aplication.programming.nttdata.controller;

import aplication.programming.nttdata.services.IAccountService;
import aplication.programming.nttdata.vo.request.AccountRequestVO;
import aplication.programming.nttdata.vo.response.AccountResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("/cuentas")
public class AccountController {

    @Resource
    private IAccountService accountService;

    @PostMapping
    public Mono<ResponseEntity<AccountResponseVO>> create(@RequestBody AccountRequestVO request){
        return accountService.create(request)
                .map(response -> ResponseEntity.ok().body(response));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<AccountResponseVO>>> allAccount(){
        return Mono.just(ResponseEntity.ok().body(accountService.allAccount()));
    }

    @PutMapping("/{idAccount}")
    public Mono<ResponseEntity<String>> update(@PathVariable Long idAccount, @RequestBody AccountRequestVO request){
        return accountService.update(idAccount, request)
                .thenReturn(ResponseEntity.ok().body("Account updated successfully"));
    }

    @DeleteMapping("/{idAccount}")
    public Mono<ResponseEntity<String>> delete(@PathVariable Long idAccount){
        return accountService.delete(idAccount)
                .thenReturn(ResponseEntity.ok().body("Account deleted successfully"));
    }
}
