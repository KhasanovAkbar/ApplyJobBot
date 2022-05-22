package univ.tuit.applyjobbot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobbot.store.JobStore;
import univ.tuit.applyjobbot.store.RequirementStore;
import univ.tuit.applyjobbot.store.jpo.JobsJpo;
import univ.tuit.applyjobbot.store.repo.JobRepository;
import univ.tuit.applyjobbot.store.repo.RequirementRepository;

import java.util.List;

@Component
@Repository
public class BotUserCache implements Cache<JobsJpo> {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    RequirementRepository requirementRepository;

    @Override
    public JobsJpo add(JobsJpo jobsJpo) {
        JobsJpo save;
        if (jobsJpo.getUserId() != null)
            save = jobRepository.save(jobsJpo);
        else throw new NullPointerException("No id");
        return save;
    }

    @Override
    public void update(JobsJpo jobsJpo) {
        if (jobsJpo.getUserId() != null) {
            JobsJpo byUserId = jobRepository.findByUserIdAndId(jobsJpo.getUserId(), jobsJpo.getId());
            if (byUserId != null && jobsJpo.getId().equals(byUserId.getId())) {
                byUserId.setCompanyName(jobsJpo.getCompanyName());
                byUserId.setIsCompanyName(jobsJpo.isCompanyName());
                byUserId.setTechnology(jobsJpo.getTechnology());
                byUserId.setIsTechnology(jobsJpo.isTechnology());
                byUserId.setTerritory(jobsJpo.getTerritory());
                byUserId.setIsTerritory(jobsJpo.isTerritory());
                byUserId.setJobId(jobsJpo.getJobId());
                byUserId.setState(jobsJpo.getState());
                byUserId.setIsRequirements(jobsJpo.isRequirements());
                jobRepository.save(byUserId);
            } else jobRepository.save(jobsJpo);
        } else throw new NullPointerException("No id");
    }

    @Override
    public JobsJpo findBy(Long id, Integer sequence) {
        return jobRepository.findByUserIdAndId(id, sequence);
    }

    @Override
    public List<JobsJpo> getAll() {
        return jobRepository.getAll();
    }

    @Override
    public List<JobsJpo> findByJobId(String s) {
        //this function works for requirements
        return null;
    }

    @Override
    public List<JobsJpo> findByUserId(Long id) {
        return jobRepository.findByUserId(id);
    }

    @Override
    public List<JobsJpo> findByJob(JobsJpo jobsJpo) {
        //this functions works for Requirements
        return null;
    }
}
