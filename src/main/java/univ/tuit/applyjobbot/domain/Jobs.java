package univ.tuit.applyjobbot.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jobs {

    private Integer id;
    private Long userId;
    private String username;
    private String jobId;
    private String companyName = "Register";
    private int isCompanyName = 0 ;
    private String technology = "Register";
    private int isTechnology = 0;
    private String territory = "Register";
    private int isTerritory = 0;
    private String state = "Register";
    private int isRequirements = 0;
    private String registrationTime;
}
