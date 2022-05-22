package univ.tuit.applyjobbot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.services.JobService;

@RestController
@RequestMapping("/api/v1/job")
public class JobController {

    @Autowired
    JobService<Jobs> jobService;

}
