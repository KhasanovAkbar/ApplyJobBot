package univ.tuit.applyjobbot.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import univ.tuit.applyjobbot.store.jpo.ApplyJpo;

import java.util.List;

public interface ApplyRepository extends JpaRepository<ApplyJpo, Long> {

    List<ApplyJpo> findByJobId(String jobId);

    @Query(value = "select a from ApplyJpo a order by a.applyId desc")
    List<ApplyJpo> getAll();

    ApplyJpo findByUserIdAndApplyId(Long userId, Integer id);
}
