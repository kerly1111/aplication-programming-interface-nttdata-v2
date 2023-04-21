package aplication.programming.nttdata.services;

import aplication.programming.nttdata.vo.request.AccountRequestVO;
import aplication.programming.nttdata.vo.response.AccountResponseVO;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface IAccountService {

    Mono<AccountResponseVO> create(AccountRequestVO request);

    Flux<AccountResponseVO> allAccount();

    Mono<Void> update(Long idAccount, AccountRequestVO request);

    Mono<Void> delete(Long idAccount);
}
