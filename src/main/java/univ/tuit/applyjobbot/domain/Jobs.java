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
    private boolean isCompanyName = false;
    private String technology = "Register";
    private boolean isTechnology = false;
    private String territory = "Register";
    private boolean isTerritory = false;
    private String state = "Register";
    private boolean isRequirements = false;
    private String registrationTime;

    public boolean isCompanyName() {
        return isCompanyName;
    }

    public void setIsCompanyName(boolean companyName) {
        isCompanyName = companyName;
    }

    public boolean isTechnology() {
        return isTechnology;
    }

    public void setIsTechnology(boolean technology) {
        isTechnology = technology;
    }

    public boolean isTerritory() {
        return isTerritory;
    }

    public void setIsTerritory(boolean territory) {
        isTerritory = territory;
    }

    public boolean isRequirements() {
        return isRequirements;
    }

    public void setIsRequirements(boolean requirements) {
        isRequirements = requirements;
    }

}
