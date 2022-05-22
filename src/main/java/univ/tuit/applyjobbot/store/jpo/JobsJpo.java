package univ.tuit.applyjobbot.store.jpo;

import lombok.*;
import org.springframework.beans.BeanUtils;
import univ.tuit.applyjobbot.domain.Jobs;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "jobs")
public class JobsJpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sequence")
    private Integer id;

    private Long userId;

    private String username;

    private String jobId;

    private String companyName;

    private boolean isCompanyName;

    private String technology;

    private boolean isTechnology;

    private String territory;

    private boolean isTerritory;

    private String state;

    private boolean isRequirements;

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

    public JobsJpo(Jobs jobs) {
        JobsJpo jobsJpo = new JobsJpo();
        jobsJpo.setUsername(jobs.getUsername());
        jobsJpo.setJobId(jobs.getJobId());
        jobs.setUserId(jobs.getUserId());

        jobsJpo.setCompanyName(jobs.getCompanyName());
        jobsJpo.setIsCompanyName(jobs.isCompanyName());

        jobsJpo.setTechnology(jobs.getTechnology());
        jobsJpo.setIsTechnology(jobs.isTechnology());

        jobsJpo.setTerritory(jobs.getTerritory());
        jobsJpo.setIsTerritory(jobs.isTerritory());

        jobsJpo.setState(jobs.getState());

        jobsJpo.setIsRequirements(jobs.isRequirements());

        jobsJpo.setRegistrationTime(jobs.getRegistrationTime());

    }

    public Jobs toDomain() {
        Jobs jobs = new Jobs();
        BeanUtils.copyProperties(this, jobs);
        return jobs;
    }

    public static List<Jobs> toDomain(List<JobsJpo> jobsJpos) {
        return jobsJpos.stream().map(JobsJpo::toDomain).collect(Collectors.toList());
    }
}
