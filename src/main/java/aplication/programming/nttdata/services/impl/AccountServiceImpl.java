package aplication.programming.nttdata.services.impl;

import aplication.programming.nttdata.common.exception.NttdataError;
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
        log.info("Start the account creation process");
        return clientRepository.findByIdentification(request.getIdentification())
                .onErrorResume(error -> {
                    log.error("An error occurred while searching for the client. Detail = {}", error.getMessage());
                    return Mono.error(NttdataError.NTT001);
                })
                .doOnSuccess(success -> log.info("Client successfully obtained"))
                .flatMap(client -> {
                            Account account = new Account();
                            account.setAccountNumber(request.getAccountNumber());
                            account.setAccountType(request.getAccountType());
                            account.setInitialBalance(request.getInitialBalance());
                            account.setStatus(request.getStatus());
                            account.setIdClient(client.getId());

                            return accountRepository.save(account)
                                    .onErrorResume(error -> {
                                        log.error("An error occurred while trying to save the customer account. Detail = {}", error.getMessage());
                                        return Mono.error(NttdataError.NTT002);
                                    })
                                    .doOnSuccess(success -> log.info("Customer account successfully saved"))
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
                )
                .doOnSuccess(success -> log.info("Client account creation process completed successfully"));
    }

    @Override
    public Flux<AccountResponseVO> allAccount() {
        log.info("Start of consultation of all accounts");
        return accountRepository.findAll()
                .onErrorResume(error -> {
                    log.error("An error occurred while consulting the accounts. Detail = {}", error.getMessage());
                    return Mono.error(NttdataError.NTT003);
                })
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
            log.info("Start account update process");
        return Mono.just(request)
                .flatMap(accountRequestVO -> {
                    Account account = new Account();
                    account.setId(accountRequestVO.getId());
                    account.setAccountNumber(accountRequestVO.getAccountNumber());
                    account.setAccountType(accountRequestVO.getAccountType());
                    account.setInitialBalance(accountRequestVO.getInitialBalance());
                    account.setStatus(accountRequestVO.getStatus());
                    return accountRepository.save(account)
                            .onErrorResume(error -> {
                                log.error("An error occurred while updating the customer account. Detail = {}", error.getMessage());
                                return Mono.error(NttdataError.NTT004);
                            })
                            .doOnSuccess(success -> log.info("Account update successful"))
                            .flatMap(response -> Mono.empty());
                });
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long idAccount) {
        log.info("Start account deletion process");
        return accountRepository.deleteById(idAccount)
                .onErrorResume(error -> {
                    log.error("An error occurred while deleting the customer account. Detail = {}", error.getMessage());
                    return Mono.error(NttdataError.NTT005);
                })
                .doOnSuccess(success -> log.info("Account deletion successful"));
    }
}
