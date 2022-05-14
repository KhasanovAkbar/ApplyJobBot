package univ.tuit.applyjobbot.cache;

import java.util.List;

public interface Cache<T> {

    T add(T t);

    void remove(T t);

    T update(T t);

    T findBy(Long id, Integer sequence);

    List<T> getAll();

    List<T> findByJobId(String s);

}
