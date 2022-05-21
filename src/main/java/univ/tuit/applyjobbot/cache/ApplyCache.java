package univ.tuit.applyjobbot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobbot.domain.Apply;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.repo.ApplyRepository;

import java.util.List;

@Component
@Repository
public class ApplyCache implements Cache<Apply>{

    @Autowired
    private ApplyRepository applyRepository;

    @Override
    public Apply add(Apply apply) {
        return null;
    }

    @Override
    public void update(Apply apply) {
    }

    @Override
    public Apply findBy(Long id, Integer sequence) {
        //this functions works for Jobs
        return null;
    }

    @Override
    public List<Apply> getAll() {
        return applyRepository.findAll();
    }

    @Override
    public List<Apply> findByJobId(String s) {
        return applyRepository.findByJobId(s);
    }

    @Override
    public List<Apply> findByUserId(Long id) {
        //this functions works for Jobs
        return null;
    }

    @Override
    public List<Apply> findByJob(Jobs jobs) {
        //this functions works for Requirements
        return null;
    }
}
