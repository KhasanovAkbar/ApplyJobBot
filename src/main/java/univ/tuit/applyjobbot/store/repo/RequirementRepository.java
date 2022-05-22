package univ.tuit.applyjobbot.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import univ.tuit.applyjobbot.store.jpo.JobsJpo;
import univ.tuit.applyjobbot.store.jpo.RequirementJpo;

import java.util.List;

public interface RequirementRepository extends JpaRepository<RequirementJpo, Integer> {

    List<RequirementJpo> findByJob(JobsJpo jobsJpo);
}
