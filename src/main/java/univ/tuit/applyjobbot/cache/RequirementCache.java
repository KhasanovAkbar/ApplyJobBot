package univ.tuit.applyjobbot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobbot.store.jpo.JobsJpo;
import univ.tuit.applyjobbot.store.jpo.RequirementJpo;
import univ.tuit.applyjobbot.store.repo.RequirementRepository;

import java.util.List;

@Component
@Repository
public class RequirementCache implements Cache<RequirementJpo> {

    @Autowired
    RequirementRepository requirementRepository;

    @Override
    public RequirementJpo add(RequirementJpo requirementJpo) {
        return requirementRepository.save(requirementJpo);
    }

    @Override
    public void update(RequirementJpo requirementJpo) {
        //with function works in BotUserCache
    }

    @Override
    public RequirementJpo findBy(Long id, Integer sequence) {
        //with function works in BotUserCache
        return null;
    }

    @Override
    public List<RequirementJpo> getAll() {
        //with function works in BotUserCache
        return null;
    }

    @Override
    public List<RequirementJpo> findByJobId(String s) {
        return null;
    }

    @Override
    public List<RequirementJpo> findByUserId(Long id) {
        //this function works for BotUser
        return null;
    }

    @Override
    public List<RequirementJpo> findByJob(JobsJpo jobsJpo) {
        return requirementRepository.findByJob(jobsJpo);
    }
}
