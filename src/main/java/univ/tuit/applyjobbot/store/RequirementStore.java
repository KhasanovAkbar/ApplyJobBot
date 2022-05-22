package univ.tuit.applyjobbot.store;

import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.Requirement;

import java.util.List;

public interface RequirementStore {

    Requirement add(Requirement t);

    List<Requirement> findByJob(Jobs job);
}
