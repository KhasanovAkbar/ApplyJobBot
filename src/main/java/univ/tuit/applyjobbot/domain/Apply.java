package univ.tuit.applyjobbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apply {

    private Integer applyId;
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

    public boolean isJobId() {
        return isJobId;
    }

    public void setIsJobId(boolean jobId) {
        isJobId = jobId;
    }

    public boolean isName() {
        return isName;
    }

    public void setIsName(boolean name) {
        isName = name;
    }

    public boolean isAge() {
        return isAge;
    }

    public void setIsAge(boolean age) {
        isAge = age;
    }

    public boolean isPhone() {
        return isPhone;
    }

    public void setIsPhone(boolean phone) {
        isPhone = phone;
    }

    public boolean isFilePath() {
        return isFilePath;
    }

    public void setIsFilePath(boolean filePath) {
        isFilePath = filePath;
    }
}
