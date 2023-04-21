package aplication.programming.nttdata.services.impl;

import aplication.programming.nttdata.common.exception.NttdataException;
import aplication.programming.nttdata.model.Account;
import aplication.programming.nttdata.repository.AccountRepository;
import aplication.programming.nttdata.repository.ClientRepository;
import aplication.programming.nttdata.services.IAccountService;
import aplication.programming.nttdata.vo.request.AccountRequestVO;
import aplication.programming.nttdata.vo.response.AccountResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Slf4j
@Service
public class AccountServiceImpl implements IAccountService {

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public Mono<AccountResponseVO> create(AccountRequestVO request) {
        return clientRepository.findByIdentification(request.getIdentification())
                .doOnError(error -> {
                    log.error("Error search. Detail = {}", error.getMessage());
                    Mono.error(new NttdataException(error.getMessage()));
                })
                .flatMap(client -> {
                            Account account = new Account();
                            account.setAccountNumber(request.getAccountNumber());
                            account.setAccountType(request.getAccountType());
                            account.setInitialBalance(request.getInitialBalance());
                            account.setStatus(request.getStatus());
                            account.setIdClient(client.getId());

                            return accountRepository.save(account)
                                    .doOnError(error -> {
                                        log.error("Error create account. Detail = {}", error.getMessage());
                                        Mono.error(new NttdataException("Error al crear la cuenta"));
                                    })
                                    .map(accountResponse -> {
                                        AccountResponseVO accountResponseVO = new AccountResponseVO();
                                        accountResponseVO.setAccountNumber(accountResponse.getAccountNumber());
                                        accountResponseVO.setAccountType(accountResponse.getAccountType());
                                        accountResponseVO.setInitialBalance(accountResponse.getInitialBalance());
                                        accountResponseVO.setStatus(accountResponse.getStatus());
                                        accountResponseVO.setName(client.getName());
                                        return accountResponseVO;
                                    });
                        }
                );
    }

    @Override
    public Flux<AccountResponseVO> allAccount() {
        return accountRepository.findAll()
                .flatMap(account -> clientRepository.findById(account.getIdClient())
                        .map(client -> {
                            AccountResponseVO accountResponseVO = new AccountResponseVO();
                            accountResponseVO.setAccountNumber(account.getAccountNumber());
                            accountResponseVO.setAccountType(account.getAccountType());
                            accountResponseVO.setInitialBalance(account.getInitialBalance());
                            accountResponseVO.setStatus(account.getStatus());
                            accountResponseVO.setName(client.getName());
                            return accountResponseVO;
                        }));
    }

    @Override
    @Transactional
    public Mono<Void> update(Long idAccount, AccountRequestVO request) {
        return Mono.just(request)
                .flatMap(accountRequestVO -> {
                    Account account = new Account();
                    account.setId(accountRequestVO.getId());
                    account.setAccountNumber(accountRequestVO.getAccountNumber());
                    account.setAccountType(accountRequestVO.getAccountType());
                    account.setInitialBalance(accountRequestVO.getInitialBalance());
                    account.setStatus(accountRequestVO.getStatus());
                    return accountRepository.save(account)
                            .flatMap(response -> Mono.empty());
                });
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long idAccount) {
        return accountRepository.deleteById(idAccount);
    }
}
