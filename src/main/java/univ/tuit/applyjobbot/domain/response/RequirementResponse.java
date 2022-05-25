package univ.tuit.applyjobbot.domain.response;

import lombok.Getter;
import lombok.Setter;
import univ.tuit.applyjobbot.domain.Requirement;

import java.util.List;

@Getter
@Setter
public class RequirementResponse {
    private List<Requirement> entities;
    private FailureMessage failureMessage;
    private boolean requestFailed;
}
