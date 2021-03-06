package univ.tuit.applyjobbot.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.response.AllResponseJobs;
import univ.tuit.applyjobbot.domain.response.FindJobResponse;
import univ.tuit.applyjobbot.helper.APIResponse;
import univ.tuit.applyjobbot.services.JobService;

import java.util.List;


@Service
public class JobLogic implements JobService<Jobs> {

    @Autowired
    RestTemplate restTemplate;

    @Value("${rest.template.baseUrl}")
    private String baseUrl;

    @Override
    public void add(Jobs jobs) {
        if (jobs.getUserId() != null) {
            HttpEntity<Jobs> jobsHttpEntity = new HttpEntity<>(jobs);
            restTemplate.exchange(baseUrl + "/create", HttpMethod.POST, jobsHttpEntity, APIResponse.class);
        } else {
            throw new IllegalArgumentException("No id");
        }
    }

    @Override
    public void update(Jobs jobs) {
        if (jobs.getUserId() != null) {
            HttpEntity<Jobs> jobsHttpEntity = new HttpEntity<>(jobs);
            restTemplate.exchange(baseUrl + "/update", HttpMethod.POST, jobsHttpEntity, APIResponse.class);
        } else throw new IllegalArgumentException("No id");

    }

    @Override
    public Jobs findByUserIdAndId(Long id, Integer sequence) {
        Jobs findById = null;
        FindJobResponse forObject = restTemplate.getForObject(baseUrl + "/" + id + "/" + sequence, FindJobResponse.class, id, sequence);
        if (forObject != null) {
            List<Jobs> entities = forObject.getEntities();
            findById = entities.get(0);
        }
        return findById;
    }

    @Override
    public List<Jobs> getAll() {
        List<Jobs> jobs = null;
        HttpEntity<String> jobsHttpEntity = new HttpEntity<>("jobs");

        ResponseEntity<AllResponseJobs> exchange = restTemplate.exchange(baseUrl + "/all", HttpMethod.GET, jobsHttpEntity, AllResponseJobs.class);
        AllResponseJobs body = exchange.getBody();

        if (body != null) {
            jobs = body.getEntities().get(0);
        }
        return jobs;
    }

    @Override
    public List<Jobs> findByUserId(Long id) {
        List<Jobs> jobs = null;
        HttpEntity<Long> longHttpEntity = new HttpEntity<>(id);

        ResponseEntity<AllResponseJobs> exchange = restTemplate.exchange( baseUrl+ "/" + id, HttpMethod.GET, longHttpEntity, AllResponseJobs.class);
        if (exchange.getBody() != null) {
            jobs = exchange.getBody().getEntities().get(0);
        }
        return jobs;
    }
}
