package aplication.programming.nttdata;

import aplication.programming.nttdata.model.Account;
import aplication.programming.nttdata.model.Client;
import aplication.programming.nttdata.model.Movement;
import aplication.programming.nttdata.vo.request.AccountRequestVO;
import aplication.programming.nttdata.vo.request.MovementRequestVO;
import aplication.programming.nttdata.vo.response.AccountResponseVO;
import aplication.programming.nttdata.vo.response.MovementResponseVO;
import aplication.programming.nttdata.vo.response.StatementResponseVO;

import java.sql.Date;
import java.time.LocalDate;

public class MockUtils {

    public static AccountRequestVO buildAccountRequestVO(){
        return AccountRequestVO.builder()
                .id(Long.valueOf(1))
                .accountNumber("478758")
                .accountType("Ahorro")
                .initialBalance(200.00)
                .status(Boolean.TRUE)
                .identification("1234567890")
                .name("Jose Lema")
                .build();
    }

    public static AccountResponseVO buildAccountResponseVO(){
        return AccountResponseVO.builder()
                .accountNumber("478758")
                .accountType("Ahorro")
                .initialBalance(2000.00)
                .status(Boolean.TRUE)
                .name("Jose Lema")
                .build();
    }
    public static Client buildClient (){
        return Client.builder()
                .id(Long.valueOf(1))
                .identification("1234567890")
                .name("Jose Lema")
                .gender("Masculino")
                .age(20)
                .address("Otavalo sn y principal")
                .phone("098254785")
                .password("1234")
                .status(Boolean.TRUE)
                .build();
    }

    public static MovementRequestVO buildMovementRequestVO(){
        return MovementRequestVO.builder()
                .date(Date.valueOf(LocalDate.now()))
                .movementType("Retiro")
                .value(100.00)
                .accountNumber("12345")
                .accountType("Ahorro")
                .build();
    }

    public static MovementResponseVO buildMovementResponseVO(){
        return MovementResponseVO.builder()
                .accountNumber("12345")
                .accountType("Ahorro")
                .date(Date.valueOf(LocalDate.now()))
                .movementType("Retiro")
                .value(100.00)
                .balance(300.00)
                .status(true)
                .build();
    }

    public static StatementResponseVO buildStatementResponseVO(){
        return StatementResponseVO.builder()
                .client("Jon Doe")
                .accountNumber("12345")
                .accountType("Ahorro")
                .initialBalance(200.00)
                .totalCredits(500.00)
                .totalDebits(300.00)
                .balance(400.00)
                .status(true)
                .build();

    }

    public static Account buildAccount(){
        Account account = new Account();
        account.setIdClient(1L);
        account.setAccountNumber("852");
        account.setAccountType("Ahorro");
        account.setInitialBalance(200.00);
        account.setStatus(true);
        return account;
    }

    public static Account buildAccountFail(){
        Account account = new Account();
        account.setIdClient(1L);
        account.setAccountNumber("85332");
        account.setAccountType("Ahorro");
        account.setInitialBalance(0.00);
        account.setStatus(true);
        return account;
    }

    public static Movement buildMovement(){
        Movement movement = new Movement();
        movement.setId(1L);
        movement.setDate(LocalDate.now());
        movement.setMovementType("Ahorro");
        movement.setBalance(500.00);
        movement.setIdAccount(1L);
        return movement;
    }
}
