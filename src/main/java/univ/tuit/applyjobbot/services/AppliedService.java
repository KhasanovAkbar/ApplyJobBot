package univ.tuit.applyjobbot.services;

import java.util.List;

public interface AppliedService<T> {

    List<T> findByJobIdAndCandidateId(String jobId, Long candidateId);

}
