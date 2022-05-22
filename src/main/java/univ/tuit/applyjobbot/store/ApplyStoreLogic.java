package univ.tuit.applyjobbot.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobbot.domain.Apply;
import univ.tuit.applyjobbot.store.jpo.ApplyJpo;
import univ.tuit.applyjobbot.store.jpo.JobsJpo;
import univ.tuit.applyjobbot.store.repo.ApplyRepository;

import java.util.List;

@Repository
public class ApplyStoreLogic implements ApplyStore<Apply> {

    @Autowired
    ApplyRepository applyRepository;

    @Override
    public Apply add(Apply apply) {
        return null;
    }

    @Override
    public void update(Apply apply) {

    }

    @Override
    public Apply findBy(Long id, Integer sequence) {
        return null;
    }

    @Override
    public List<Apply> getAll() {
        return null;
    }

    @Override
    public List<Apply> findByJobId(String s) {
        List<ApplyJpo> byJobId = applyRepository.findByJobId(s);
        return ApplyJpo.toDomain(byJobId);
    }

    @Override
    public List<Apply> findByUserId(Long id) {
        return null;
    }

    @Override
    public List<Apply> findByJob(JobsJpo jobsJpo) {
        return null;
    }
}
