package univ.tuit.applyjobbot.services;

import univ.tuit.applyjobbot.domain.Jobs;

import java.io.IOException;
import java.util.List;

public interface ApplyService<T> {

    void apply(T t);

    void applyJob(T t, Integer id);

    void sendCV(T t, Integer id) throws IOException;

    T add(T t);

    T findBy(Long id, Integer sequence);

    List<T> getAll();

    List<T> findByJobId(String s);

    List<T> findByUserId(Long id);

    List<T> findByJob(Jobs jobsJpo);
}
