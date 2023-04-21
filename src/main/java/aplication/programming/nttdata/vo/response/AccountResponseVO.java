package aplication.programming.nttdata.vo.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AccountResponseVO {

    private String accountNumber;

    private String accountType;

    private Double initialBalance;

    private Boolean status;

    private String name;
}
