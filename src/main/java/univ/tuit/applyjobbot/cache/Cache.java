package univ.tuit.applyjobbot.cache;

import univ.tuit.applyjobbot.store.jpo.JobsJpo;

import java.util.List;

public interface Cache<T> {

    T add(T t);

    void update(T t);

    T findBy(Long id, Integer sequence);

    List<T> getAll();

    List<T> findByJobId(String s);

    List<T> findByUserId(Long id);

    List<T> findByJob(JobsJpo jobsJpo);

}
