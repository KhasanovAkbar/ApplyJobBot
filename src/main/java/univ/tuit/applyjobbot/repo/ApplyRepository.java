package univ.tuit.applyjobbot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import univ.tuit.applyjobbot.domain.Apply;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    List<Apply> findByJobId(String jobId);
}
