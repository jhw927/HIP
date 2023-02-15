package borad.HIP.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class UserEntity {

    @Id
    private Long id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;



}
