package univ.tuit.applyjobbot.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import univ.tuit.applyjobbot.store.jpo.JobsJpo;

import java.util.List;

public interface JobRepository extends JpaRepository<JobsJpo, Integer>{

    JobsJpo findByUserIdAndId(Long userId, Integer id);

    @Query(value = "select j from JobsJpo j order by j.id desc")
    List<JobsJpo> getAll();

    List<JobsJpo> findByUserId(Long userId);

    JobsJpo findByJobId(String id);

}
