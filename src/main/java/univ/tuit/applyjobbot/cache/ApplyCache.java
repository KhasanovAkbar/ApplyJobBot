package univ.tuit.applyjobbot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobbot.store.jpo.ApplyJpo;
import univ.tuit.applyjobbot.store.jpo.JobsJpo;
import univ.tuit.applyjobbot.store.repo.ApplyRepository;

import java.util.List;

@Component
@Repository
public class ApplyCache implements Cache<ApplyJpo>{

    @Autowired
    private ApplyRepository applyRepository;

    @Override
    public ApplyJpo add(ApplyJpo apply) {
        return null;
    }

    @Override
    public void update(ApplyJpo apply) {
    }

    @Override
    public ApplyJpo findBy(Long id, Integer sequence) {
        //this functions works for Jobs
        return null;
    }

    @Override
    public List<ApplyJpo> getAll() {
        return applyRepository.findAll();
    }

    @Override
    public List<ApplyJpo> findByJobId(String s) {
        return applyRepository.findByJobId(s);
    }

    @Override
    public List<ApplyJpo> findByUserId(Long id) {
        //this functions works for Jobs
        return null;
    }

    @Override
    public List<ApplyJpo> findByJob(JobsJpo jobsJpo) {
        //this functions works for Requirements
        return null;
    }
}
