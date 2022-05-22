package univ.tuit.applyjobbot.services;

import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.Requirement;

import java.util.List;

public interface RequirementService {

    Requirement add(Requirement t);

    List<Requirement> findByJob(Jobs job);

}
