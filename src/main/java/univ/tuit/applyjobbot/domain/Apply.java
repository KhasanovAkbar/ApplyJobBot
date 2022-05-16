package univ.tuit.applyjobbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Apply")
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applyId")
    private Long applyId;

    private String jobId;

    private String username;

    private String name;

    private String age;

    private String phoneNumber;

}
