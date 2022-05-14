package univ.tuit.applyjobbot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import univ.tuit.applyjobbot.domain.Jobs;

import java.util.List;

public interface JobRepository extends JpaRepository<Jobs, Integer>{

    Jobs findByUserIdAndId(Long userId, Integer id);

    @Query(value = "select j from Jobs j order by j.id desc")
    List<Jobs> getAll();

    Jobs findByCompanyNameAndTechnologyAndTerritory(String companyName, String technology, String territory);

    Jobs findByJobId(String jobId);

}
