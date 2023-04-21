package aplication.programming.nttdata.services.impl;

import aplication.programming.nttdata.common.exception.NttdataException;
import aplication.programming.nttdata.model.Account;
import aplication.programming.nttdata.model.Movement;
import aplication.programming.nttdata.repository.AccountRepository;
import aplication.programming.nttdata.repository.ClientRepository;
import aplication.programming.nttdata.repository.MovementRepository;
import aplication.programming.nttdata.services.IMovementService;
import aplication.programming.nttdata.vo.request.BalanceValueVO;
import aplication.programming.nttdata.vo.request.MovementRequestVO;
import aplication.programming.nttdata.vo.response.MovementResponseVO;
import aplication.programming.nttdata.vo.response.StatementResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.sql.Date;

@Slf4j
@Service
public class MovementServiceImpl implements IMovementService {

    @Resource
    private MovementRepository movementRepository;

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public Mono<MovementResponseVO> create(MovementRequestVO request) {
        return accountRepository.findByAccountNumberByAccountType(request.getAccountNumber(), request.getAccountType())
                .flatMap(account -> Mono.zip(
                                        movementRepository.getBalance(account.getId(), "Deposito").switchIfEmpty(Mono.just(0.00)),
                                        movementRepository.getBalance(account.getId(), "Retiro").switchIfEmpty(Mono.just(0.00))
                                ).map(values -> account.getInitialBalance() + values.getT1() - values.getT2())
                                .flatMap(balance -> {
                                    if (request.getMovementType().equals("Retiro") && (balance - request.getValue()) < 0) {
                                        return Mono.error(new NttdataException("Saldo insuficiente para realizar la transacción"));
                                    }
                                    return Mono.just(balance);
                                })
                                .flatMap(balance -> {
                                    Movement movement = new Movement();
                                    movement.setDate(request.getDate().toLocalDate());
                                    movement.setMovementType(request.getMovementType());
                                    movement.setValue(request.getValue());
                                    movement.setIdAccount(account.getId());
                                    movement.setBalance(request.getMovementType().equals("Deposito") ?
                                            balance + request.getValue() : balance - request.getValue());
                                    return movementRepository.save(movement)
                                            .onErrorResume(error -> {
                                                log.error("Error {}", error.getMessage());
                                                return Mono.error(new NttdataException("Error al crear el movimiento"));
                                            });
                                }).map(movement -> {
                                    MovementResponseVO movementResponseVO = new MovementResponseVO();
                                    movementResponseVO.setAccountNumber(account.getAccountNumber());
                                    movementResponseVO.setAccountType(account.getAccountType());
                                    movementResponseVO.setDate(Date.valueOf(movement.getDate()));
                                    movementResponseVO.setMovementType(movement.getMovementType());
                                    movementResponseVO.setValue(movement.getValue());
                                    movementResponseVO.setBalance(movement.getBalance());
                                    movementResponseVO.setStatus(account.getStatus());
                                    return movementResponseVO;
                                })
                );
    }

    @Override
    public Flux<MovementResponseVO> allMovement() {
        return movementRepository.findAll()
                .flatMap(movement -> accountRepository.findById(movement.getId())
                        .map(account -> {
                            MovementResponseVO movementResponseVO = new MovementResponseVO();
                            movementResponseVO.setAccountNumber(account.getAccountNumber());
                            movementResponseVO.setAccountType(account.getAccountType());
                            movementResponseVO.setDate(Date.valueOf(movement.getDate()));
                            movementResponseVO.setMovementType(movement.getMovementType());
                            movementResponseVO.setValue(movement.getValue());
                            movementResponseVO.setBalance(movement.getBalance());
                            movementResponseVO.setStatus(account.getStatus());
                            return movementResponseVO;
                        }));
    }

    @Override
    @Transactional
    public Mono<Void> update(Long idMovement, MovementRequestVO request) {
        return accountRepository.findByAccountNumberByAccountType(request.getAccountNumber(), request.getAccountType())
                .flatMap(account -> Mono.zip(
                                movementRepository.getBalance(account.getId(), "Deposito").switchIfEmpty(Mono.just(0.00)),
                                movementRepository.getBalance(account.getId(), "Retiro").switchIfEmpty(Mono.just(0.00))
                        ).map(values -> account.getInitialBalance() + values.getT1() - values.getT2())
                        .flatMap(balance -> {
                            if (request.getMovementType().equals("Retiro") && (balance - request.getValue()) < 0) {
                                return Mono.error(new NttdataException("Saldo insuficiente para realizar la transacción"));
                            }
                            return Mono.just(balance);
                        })
                        .flatMap(balance -> {
                            Movement movement = new Movement();
                            movement.setId(idMovement);
                            movement.setDate(request.getDate().toLocalDate());
                            movement.setMovementType(request.getMovementType());
                            movement.setValue(request.getValue());
                            movement.setIdAccount(account.getId());
                            movement.setBalance(request.getMovementType().equals("Deposito") ?
                                    balance + request.getValue() : balance - request.getValue());

                            return movementRepository.save(movement)
                                    .then();
                        }));
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long idMovement) {
        return movementRepository.deleteById(idMovement);

    }

    @Override
    public Flux<StatementResponseVO> report(Date dateStart, Date dateEnd, String identification) {
        return clientRepository.findByIdentification(identification)
                .flatMapMany(client -> accountRepository.findAccountByIdClient(client.getId())
                        .onErrorResume(error -> {
                            log.error("Error {}", error.getMessage());
                            return Mono.error(new NttdataException("El usuario no asignado a la cuenta no existe."));
                        })
                        .flatMap(account -> processValues(account, dateStart, dateEnd)
                                .map(values -> {
                                    StatementResponseVO statementResponseVO = new StatementResponseVO();
                                    statementResponseVO.setClient(client.getName());
                                    statementResponseVO.setAccountNumber(account.getAccountNumber());
                                    statementResponseVO.setAccountType(account.getAccountType());
                                    statementResponseVO.setInitialBalance(values.getInitialBalance());
                                    statementResponseVO.setTotalCredits(values.getTotalCredits());
                                    statementResponseVO.setTotalDebits(values.getTotalDebits());
                                    statementResponseVO.setBalance(values.getBalance());
                                    statementResponseVO.setStatus(account.getStatus());
                                    return statementResponseVO;
                                })
                        )
                );
    }

    private Mono<BalanceValueVO> processValues(Account account, Date dateStart, Date dateEnd) {
        return getInitialBalance(account, dateStart)
                .flatMap(initialBalance -> Mono.zip(
                                getBalance(account, dateStart, dateEnd, initialBalance),
                                movementRepository.getBalanceByDate(account.getId(), "Deposito", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00)),
                                movementRepository.getBalanceByDate(account.getId(), "Retiro", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00))
                        )
                        .map(values -> {
                            BalanceValueVO balanceValueVO = new BalanceValueVO();
                            balanceValueVO.setInitialBalance(initialBalance);
                            balanceValueVO.setTotalCredits(values.getT2());
                            balanceValueVO.setTotalDebits(values.getT3());
                            balanceValueVO.setBalance(values.getT1());
                            return balanceValueVO;
                        }));
    }

    private Mono<Double> getInitialBalance(Account account, Date dateStart) {
        return Mono.zip(
                        movementRepository.getBalancePrevious(account.getId(), "Deposito", dateStart.toLocalDate())
                                .switchIfEmpty(Mono.just(0.00)),
                        movementRepository.getBalancePrevious(account.getId(), "Retiro", dateStart.toLocalDate())
                                .switchIfEmpty(Mono.just(0.00))
                )
                .map(values -> account.getInitialBalance() + values.getT1() - values.getT2())
                .switchIfEmpty(Mono.just(0.00));
    }

    private Mono<Double> getBalance(Account account, Date dateStart, Date dateEnd, Double initialBalance) {
        return Mono.zip(
                        movementRepository.getBalanceByDate(account.getId(), "Deposito", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00)),
                        movementRepository.getBalanceByDate(account.getId(), "Retiro", dateStart.toLocalDate(), dateEnd.toLocalDate()).switchIfEmpty(Mono.just(0.00))
                )
                .map(values -> initialBalance + values.getT1() - values.getT2())
                .switchIfEmpty(Mono.just(0.00));
    }
}
