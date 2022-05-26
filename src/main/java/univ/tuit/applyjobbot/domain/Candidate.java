package univ.tuit.applyjobbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {

    private Integer applyId;
    private Long userId;
    private String jobId;
    private int isJobId;
    private String username;
    private String name;
    private int isName;
    private String age;
    private int isAge;
    private String phoneNumber;
    private int isPhone;
    private String state;
    private String filePath;
    private int isFilePath;
    private String token;
    private String registrationTime;
}
