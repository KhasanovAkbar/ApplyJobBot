package univ.tuit.applyjobbot.cache;

import java.util.List;

public interface Cache<T> {

    T add(T t);

    void remove(T t);

    T update(T t);

    T findBy(Long id, Integer sequence);

    T findBy(Long id);

    T findByCompanyNameAndTechnologyAndTerritory(String comp, String tech, String ter);

    List<T> getAll();

}
