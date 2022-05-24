package univ.tuit.applyjobbot.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.Requirement;
import univ.tuit.applyjobbot.services.RequirementService;

import java.util.List;

@Service
public class RequirementLogic implements RequirementService {


    @Override
    public Requirement add(Requirement t) {
        return null;
    }

    @Override
    public List<Requirement> findByJob(Jobs job) {
        return null;
    }
}
