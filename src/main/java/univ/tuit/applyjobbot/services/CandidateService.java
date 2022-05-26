package univ.tuit.applyjobbot.services;

import java.util.List;

public interface CandidateService<T> {

    List<T> findByJobId(String s);
}
