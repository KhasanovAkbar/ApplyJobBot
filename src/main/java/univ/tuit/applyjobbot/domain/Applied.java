package univ.tuit.applyjobbot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Applied {

    private Integer sequence;
    private String jobId;
    private Long candidateId;
    private String status;
    private String registrationTime;
    private int requirementCount;

}
