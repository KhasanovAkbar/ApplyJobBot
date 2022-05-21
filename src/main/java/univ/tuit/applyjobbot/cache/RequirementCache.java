package univ.tuit.applyjobbot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.Requirement;
import univ.tuit.applyjobbot.repo.RequirementRepository;

import java.util.List;

@Component
@Repository
public class RequirementCache implements Cache<Requirement> {

    @Autowired
    RequirementRepository requirementRepository;

    @Override
    public Requirement add(Requirement requirement) {
        return requirementRepository.save(requirement);
    }

    @Override
    public void update(Requirement requirement) {
        //with function works in BotUserCache
    }

    @Override
    public Requirement findBy(Long id, Integer sequence) {
        //with function works in BotUserCache
        return null;
    }

    @Override
    public List<Requirement> getAll() {
        //with function works in BotUserCache
        return null;
    }

    @Override
    public List<Requirement> findByJobId(String s) {
        return null;
    }

    @Override
    public List<Requirement> findByUserId(Long id) {
        //this function works for BotUser
        return null;
    }

    @Override
    public List<Requirement> findByJob(Jobs jobs) {
        return requirementRepository.findByJob(jobs);
    }
}
