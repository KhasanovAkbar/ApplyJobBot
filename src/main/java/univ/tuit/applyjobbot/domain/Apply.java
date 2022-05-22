package univ.tuit.applyjobbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apply {

    private Long userId;
    private String jobId;
    private boolean isJobId;
    private String username;
    private String name;
    private boolean isName;
    private String age;
    private boolean isAge;
    private String phoneNumber;
    private boolean isPhone;
    private String state;
    private String filePath;
    private boolean isFilePath;
    private String token;
    private String registrationTime;
}
