package univ.tuit.applyjobbot.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.tuit.applyjobbot.domain.Apply;
import univ.tuit.applyjobbot.services.ApplyService;
import univ.tuit.applyjobbot.store.ApplyStore;
import univ.tuit.applyjobbot.store.jpo.JobsJpo;

import java.io.IOException;
import java.util.List;

@Service
public class ApplyLogic implements ApplyService<Apply> {

    @Autowired
    ApplyStore<Apply> applyStore;

    @Override
    public void apply(Apply apply) {

    }

    @Override
    public void applyJob(Apply apply, Integer id) {

    }

    @Override
    public void sendCV(Apply apply, Integer id) throws IOException {

    }

    @Override
    public Apply add(Apply apply) {
        return null;
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
        return null;
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