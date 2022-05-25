package univ.tuit.applyjobbot.services;

import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.Requirement;
import univ.tuit.applyjobbot.domain.response.RequirementResponse;

import java.util.List;

public interface RequirementService {

    RequirementResponse add(Requirement t);

    List<Requirement> findByJob(Jobs job);

}
