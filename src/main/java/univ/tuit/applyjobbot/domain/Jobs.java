package univ.tuit.applyjobbot.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "jobs")
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sequence")
    private Integer id;

    private Long userId;

    private String username;

    @Column(name = "jobId")
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

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Requirement> requirements = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "Job_Apply",
            joinColumns = @JoinColumn(name = "jobId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private Set<Apply> applies = new HashSet<>();

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
