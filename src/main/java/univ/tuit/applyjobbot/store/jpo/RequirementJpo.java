package univ.tuit.applyjobbot.store.jpo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.Requirement;

import javax.persistence.*;
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

    public RequirementJpo(Requirement requirement) {
        BeanUtils.copyProperties(requirement, this);
    }

    public static List<Requirement> toDomain(List<RequirementJpo> requirementJpos) {
        return requirementJpos.stream().map(RequirementJpo::toDomain).collect(Collectors.toList());
    }
    public Requirement toDomain() {
        Requirement requirement = new Requirement();
        BeanUtils.copyProperties(this, requirement);
        return requirement;
    }
}
