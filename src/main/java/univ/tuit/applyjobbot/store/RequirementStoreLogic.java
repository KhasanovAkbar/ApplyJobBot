package univ.tuit.applyjobbot.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.Requirement;
import univ.tuit.applyjobbot.store.jpo.JobsJpo;
import univ.tuit.applyjobbot.store.jpo.RequirementJpo;
import univ.tuit.applyjobbot.store.repo.RequirementRepository;

import java.util.List;

@Repository
public class RequirementStoreLogic implements RequirementStore {

    @Autowired
    RequirementRepository requirementRepository;

    @Override
    public Requirement add(Requirement t) {
        RequirementJpo requirementJpo = RequirementJpo.toDomain(t);
        RequirementJpo save = requirementRepository.save(requirementJpo);
        return RequirementJpo.toDomain(save);
    }

    @Override
    public List<Requirement> findByJob(Jobs job) {
        JobsJpo jobsJpo = JobsJpo.toDomain(job);
        List<RequirementJpo> byJob = requirementRepository.findByJob(jobsJpo);
        return RequirementJpo.toDomain(byJob);
    }
}
