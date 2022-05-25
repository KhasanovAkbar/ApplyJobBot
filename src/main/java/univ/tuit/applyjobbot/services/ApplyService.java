package univ.tuit.applyjobbot.services;

import java.util.List;

public interface ApplyService<T> {

    List<T> findByJobId(String s);
}
