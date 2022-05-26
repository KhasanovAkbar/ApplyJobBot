package univ.tuit.applyjobbot.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import univ.tuit.applyjobbot.domain.Candidate;
import univ.tuit.applyjobbot.domain.response.AllResponseCandidate;
import univ.tuit.applyjobbot.services.CandidateService;

import java.util.List;

@Service
public class CandidateLogic implements CandidateService<Candidate> {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Candidate> findByJobId(String jobId) {
        List<Candidate> candidateList = null;
        AllResponseCandidate forObject = restTemplate.getForObject("http://localhost:8081/api/v1/apply/" + jobId, AllResponseCandidate.class, jobId);
        if (forObject != null) {
            candidateList = forObject.getEntities().get(0);
        }
        return candidateList;
    }
}
