package aplication.programming.nttdata.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.checkerframework.common.aliasing.qual.Unique;

import java.io.Serializable;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String gender;

    private Integer age;

    @Unique
    private String identification;

    private String address;

    private String phone;

}
