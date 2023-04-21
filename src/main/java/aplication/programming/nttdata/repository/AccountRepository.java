package aplication.programming.nttdata.repository;

import aplication.programming.nttdata.model.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {

    @Query("select a.* from account a where a.account_number = :accountNumber and a.account_type = :accountType")
    Mono<Account> findByAccountNumberByAccountType(String accountNumber, String accountType);
    Flux<Account> findAccountByIdClient(Long idClient);
}
