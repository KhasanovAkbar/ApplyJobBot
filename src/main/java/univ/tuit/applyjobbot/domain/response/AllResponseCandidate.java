package univ.tuit.applyjobbot.domain.response;

import lombok.Getter;
import lombok.Setter;
import univ.tuit.applyjobbot.domain.Candidate;

import java.util.List;

@Getter
@Setter
public class AllResponseCandidate {
    private List<List<Candidate>> entities;
    private FailureMessage failureMessage;
    private boolean requestFailed;

}
