package univ.tuit.applyjobbot.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.services.JobService;
import univ.tuit.applyjobbot.store.JobStore;
import univ.tuit.applyjobbot.store.RequirementStore;

import java.util.List;

@Service
public class JobLogic implements JobService<Jobs> {

    @Autowired
    JobStore<Jobs> jobStore;

    @Autowired
    RequirementStore requirementStore;

    @Override
    public Jobs add(Jobs jobs) {
        Jobs save;
        if (jobs.getUserId() != null)
            save = jobStore.create(jobs);
        else throw new NullPointerException("No id");
        return save;
    }

    @Override
    public void update(Jobs jobs) {
        if (jobs.getUserId() != null) {
            Jobs byUserId = jobStore.findByUserIdAndId(jobs.getUserId(), jobs.getId());
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
                jobStore.create(byUserId);
            } else jobStore.create(jobs);
        } else throw new NullPointerException("No id");

    }

    @Override
    public Jobs findBy(Long id, Integer sequence) {
        return jobStore.findBy(id, sequence);
    }

    @Override
    public List<Jobs> getAll() {
        return jobStore.getAll();
    }

    @Override
    public List<Jobs> findByUserId(Long id) {
        return jobStore.findByUserId(id);
    }
}
