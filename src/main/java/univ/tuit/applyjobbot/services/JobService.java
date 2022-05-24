package univ.tuit.applyjobbot.services;

import org.springframework.http.ResponseEntity;
import univ.tuit.applyjobbot.helper.APIResponse;

import java.util.List;


public interface JobService<T> {

    void add(T t);

    void update(T t);

    T findByUserIdAndId(Long id, Integer sequence);

    List<T> getAll();

    List<T> findByUserId(Long id);


}
