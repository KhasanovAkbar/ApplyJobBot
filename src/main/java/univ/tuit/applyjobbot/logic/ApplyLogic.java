package univ.tuit.applyjobbot.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import univ.tuit.applyjobbot.domain.Apply;
import univ.tuit.applyjobbot.domain.response.AllResponseApply;
import univ.tuit.applyjobbot.services.ApplyService;

import java.util.List;

@Service
public class ApplyLogic implements ApplyService<Apply> {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Apply> findByJobId(String jobId) {
        List<Apply> applyList = null;
        AllResponseApply forObject = restTemplate.getForObject("http://localhost:8081/api/v1/apply/" + jobId, AllResponseApply.class, jobId);
        if (forObject != null) {
            applyList = forObject.getEntities().get(0);
        }
        return applyList;
    }
}
