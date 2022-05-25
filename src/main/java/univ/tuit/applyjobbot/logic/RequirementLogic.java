package univ.tuit.applyjobbot.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.Requirement;
import univ.tuit.applyjobbot.domain.response.AllResponseRequirement;
import univ.tuit.applyjobbot.domain.response.RequirementResponse;
import univ.tuit.applyjobbot.services.RequirementService;

import java.util.List;

@Service
public class RequirementLogic implements RequirementService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public RequirementResponse add(Requirement requirement) {
        ResponseEntity<RequirementResponse> exchange;
        if (requirement.getJob().getUserId() != null) {
            HttpEntity<Requirement> jobsHttpEntity = new HttpEntity<>(requirement);
            exchange = restTemplate.exchange("http://localhost:8081/api/v1/requirement/create", HttpMethod.POST, jobsHttpEntity, RequirementResponse.class);
        } else throw new IllegalArgumentException("No id");
        return exchange.getBody();
    }

    @Override
    public List<Requirement> findByJob(Jobs job) {
        List<Requirement> requirementList = null;
        if (job.getUserId() != null) {
            HttpEntity<Jobs> jobsHttpEntity = new HttpEntity<>(job);
            ResponseEntity<AllResponseRequirement> exchange = restTemplate.exchange("http://localhost:8081/api/v1/requirement/byJob", HttpMethod.POST, jobsHttpEntity, AllResponseRequirement.class);
            if (exchange.getBody() != null) {
                requirementList = exchange.getBody().getEntities().get(0);
            }

        } else throw new IllegalArgumentException("No id");
        return requirementList;
    }
}
