package univ.tuit.applyjobbot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.repo.JobRepository;
import univ.tuit.applyjobbot.repo.RequirementRepository;

import java.util.List;

@Component
@Repository
public class BotUserCache implements Cache<Jobs> {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    RequirementRepository requirementRepository;

    @Override
    public Jobs add(Jobs jobs) {
        Jobs save;
        if (jobs.getUserId() != null)
            save = jobRepository.save(jobs);
        else throw new NullPointerException("No id");
        return save;
    }

    @Override
    public void update(Jobs jobs) {
        if (jobs.getUserId() != null) {
            Jobs byUserId = jobRepository.findByUserIdAndId(jobs.getUserId(), jobs.getId());
            if (byUserId != null && jobs.getId().equals(byUserId.getId())) {
                byUserId.setCompanyName(jobs.getCompanyName());
                byUserId.setIsCompanyName(jobs.isCompanyName());
                byUserId.setTechnology(jobs.getTechnology());
                byUserId.setIsTechnology(jobs.isTechnology());
                byUserId.setTerritory(jobs.getTerritory());
                byUserId.setIsTerritory(jobs.isTerritory());
                byUserId.setJobId(jobs.getJobId());
                byUserId.setState(jobs.getState());
                byUserId.setIsRequirements(jobs.isRequirements());
                jobRepository.save(byUserId);
            } else jobRepository.save(jobs);
        } else throw new NullPointerException("No id");
    }

    @Override
    public Jobs findBy(Long id, Integer sequence) {
        return jobRepository.findByUserIdAndId(id, sequence);
    }

    @Override
    public List<Jobs> getAll() {
        return jobRepository.getAll();
    }

    @Override
    public List<Jobs> findByJobId(String s) {
        //this function works for requirements
        return null;
    }

    @Override
    public List<Jobs> findByUserId(Long id) {
        return jobRepository.findByUserId(id);
    }

    @Override
    public List<Jobs> findByJob(Jobs jobs) {
        //this functions works for Requirements
        return null;
    }
}
