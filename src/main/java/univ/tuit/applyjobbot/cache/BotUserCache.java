package univ.tuit.applyjobbot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.repo.JobRepository;

import java.util.List;

@Component
@Repository
public class BotUserCache implements Cache<Jobs> {

    @Autowired
    JobRepository jobRepository;

    @Override
    public Jobs add(Jobs jobs) {
        Jobs save;
        if (jobs.getUserId() != null)
            save = jobRepository.save(jobs);
        else throw new NullPointerException("No id");
        return save;
    }

    @Override
    public void remove(Jobs jobs) {

    }

    @Override
    public Jobs update(Jobs jobs) {
        Jobs save;
        if (jobs.getUserId() != null) {
            Jobs byUserId = jobRepository.findByUserIdAndId(jobs.getUserId(), jobs.getId());
            if (byUserId != null && jobs.getId().equals(byUserId.getId()))
                jobRepository.delete(byUserId);
            save = jobRepository.save(jobs);
        } else throw new NullPointerException("No id");
        return save;
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
}
