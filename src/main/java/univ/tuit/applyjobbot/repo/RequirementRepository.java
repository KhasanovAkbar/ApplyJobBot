package univ.tuit.applyjobbot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import univ.tuit.applyjobbot.domain.Requirement;

import java.util.List;

public interface RequirementRepository extends JpaRepository<Requirement, Integer> {

    List<Requirement> findByJobId(String jobId);
}
