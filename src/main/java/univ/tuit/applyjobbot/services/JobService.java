package univ.tuit.applyjobbot.services;

import java.util.List;

public interface JobService<T> {

    T add(T t);

    void update(T t);

    T findBy(Long id, Integer sequence);

    List<T> getAll();

    List<T> findByUserId(Long id);


}
