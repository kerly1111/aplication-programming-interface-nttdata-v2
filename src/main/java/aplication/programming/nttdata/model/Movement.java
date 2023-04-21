package aplication.programming.nttdata.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.sql.Date;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "movement")
public class Movement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id_movement")
    private Long id;

    private Date date;

    private String movementType;

    private Double value;

    private Double balance;

    @Column("fk_account")
    private Long idAccount;
}
