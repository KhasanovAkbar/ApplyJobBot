package univ.tuit.applyjobbot.store.jpo;

import lombok.*;
import org.springframework.beans.BeanUtils;
import univ.tuit.applyjobbot.domain.Jobs;

import javax.persistence.*;
import java.util.ArrayList;
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

    public static JobsJpo toDomain(Jobs jobs) {
        JobsJpo jobsJpo = new JobsJpo();

        jobsJpo.setId(jobs.getId());
        jobsJpo.setUsername(jobs.getUsername());
        jobsJpo.setJobId(jobs.getJobId());
        jobsJpo.setUserId(jobs.getUserId());

        jobsJpo.setCompanyName(jobs.getCompanyName());
        jobsJpo.setIsCompanyName(jobs.isCompanyName());

        jobsJpo.setTechnology(jobs.getTechnology());
        jobsJpo.setIsTechnology(jobs.isTechnology());

        jobsJpo.setTerritory(jobs.getTerritory());
        jobsJpo.setIsTerritory(jobs.isTerritory());

        jobsJpo.setState(jobs.getState());

        jobsJpo.setIsRequirements(jobs.isRequirements());

        jobsJpo.setRegistrationTime(jobs.getRegistrationTime());
        return jobsJpo;
    }

    public static Jobs toDomain(JobsJpo jobs) {
        Jobs job = new Jobs();

        job.setId(jobs.getId());
        job.setUsername(jobs.getUsername());
        job.setJobId(jobs.getJobId());
        job.setUserId(jobs.getUserId());

        job.setCompanyName(jobs.getCompanyName());
        job.setIsCompanyName(jobs.isCompanyName());

        job.setTechnology(jobs.getTechnology());
        job.setIsTechnology(jobs.isTechnology());

        job.setTerritory(jobs.getTerritory());
        job.setIsTerritory(jobs.isTerritory());

        job.setState(jobs.getState());

        job.setIsRequirements(jobs.isRequirements());

        job.setRegistrationTime(jobs.getRegistrationTime());
        return job;
    }

    public static List<Jobs> toDomain(List<JobsJpo> jobsJpos) {
        List<Jobs> list = new ArrayList<>();
        for (JobsJpo job:jobsJpos){
            Jobs jobs = new Jobs();
            jobs.setId(job.getId());
            jobs.setUsername(job.getUsername());
            jobs.setJobId(job.getJobId());
            jobs.setUserId(job.getUserId());

            jobs.setCompanyName(job.getCompanyName());
            jobs.setIsCompanyName(job.isCompanyName());

            jobs.setTechnology(job.getTechnology());
            jobs.setIsTechnology(job.isTechnology());

            jobs.setTerritory(job.getTerritory());
            jobs.setIsTerritory(job.isTerritory());

            jobs.setState(job.getState());

            jobs.setIsRequirements(job.isRequirements());

            jobs.setRegistrationTime(job.getRegistrationTime());
            list.add(jobs);
        }
        return list;
    }
}
