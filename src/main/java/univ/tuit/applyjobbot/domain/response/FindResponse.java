package univ.tuit.applyjobbot.domain.response;

import lombok.Getter;
import lombok.Setter;
import univ.tuit.applyjobbot.domain.Jobs;

import java.util.List;

@Getter
@Setter
public class FindResponse {
    private List<Jobs> entities;
    private FailureMessage failureMessage;
    private boolean requestFailed;
}
