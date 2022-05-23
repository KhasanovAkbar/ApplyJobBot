package univ.tuit.applyjobbot.store.jpo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.Requirement;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RequirementJpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobId")
    private JobsJpo job;

    public static RequirementJpo toDomain(Requirement requirement) {
        RequirementJpo requirementJpo = new RequirementJpo();
        requirementJpo.setName(requirement.getName());
        Jobs job = requirement.getJob();
        requirementJpo.setJob(JobsJpo.toDomain(job));
        return requirementJpo;
    }

    public static List<Requirement> toDomain(List<RequirementJpo> requirementJpos) {
        List<Requirement> list = new ArrayList<>();
        for (RequirementJpo requirementJpo : requirementJpos) {

            Requirement requirement = new Requirement();
            requirement.setId(requirementJpo.getId());
            requirement.setName(requirementJpo.getName());
            requirement.setJob(JobsJpo.toDomain(requirementJpo.getJob()));
            list.add(requirement);
        }
        return list;
    }

    public static Requirement toDomain(RequirementJpo requirementJpo) {

        Requirement requirement = new Requirement();
        requirement.setId(requirementJpo.getId());
        requirement.setName(requirementJpo.getName());
        requirement.setJob(JobsJpo.toDomain(requirementJpo.getJob()));
        return requirement;
    }
}
