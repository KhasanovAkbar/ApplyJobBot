package univ.tuit.applyjobbot.store;

import java.util.List;

public interface JobStore<T> {

    T create(T t);

    void update(T t);

    T findBy(Long id, Integer sequence);

    List<T> getAll();

    List<T> findByUserId(Long id);

    T findByUserIdAndId(Long userId, Integer id);

}
