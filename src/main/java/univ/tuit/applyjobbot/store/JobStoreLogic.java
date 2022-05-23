package univ.tuit.applyjobbot.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.store.jpo.JobsJpo;
import univ.tuit.applyjobbot.store.repo.JobRepository;
import univ.tuit.applyjobbot.store.repo.RequirementRepository;

import java.util.List;

@Repository
public class JobStoreLogic implements JobStore<Jobs> {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    RequirementRepository requirementRepository;

    @Override
    public Jobs create(Jobs jobs) {
        JobsJpo jobsJpo = JobsJpo.toDomain(jobs);
        JobsJpo save = jobRepository.save(jobsJpo);
        return JobsJpo.toDomain(save);
    }

    @Override
    public void update(Jobs jobs) {
        JobsJpo jobsJpo = JobsJpo.toDomain(jobs);
        jobRepository.save(jobsJpo);
    }

    @Override
    public Jobs findBy(Long id, Integer sequence) {
        JobsJpo byUserIdAndId = jobRepository.findByUserIdAndId(id, sequence);
        return JobsJpo.toDomain(byUserIdAndId);
    }

    @Override
    public List<Jobs> getAll() {
        List<JobsJpo> all = jobRepository.getAll();
        return JobsJpo.toDomain(all);
    }


    @Override
    public List<Jobs> findByUserId(Long id) {
        List<JobsJpo> byUserId = jobRepository.findByUserId(id);
        return JobsJpo.toDomain(byUserId);
    }

    @Override
    public Jobs findByUserIdAndId(Long userId, Integer id) {
        JobsJpo byUserIdAndId = jobRepository.findByUserIdAndId(userId, id);
        return JobsJpo.toDomain(byUserIdAndId);
    }
}
