package univ.tuit.applyjobbot.domain.response;

import lombok.Getter;
import lombok.Setter;
import univ.tuit.applyjobbot.domain.Apply;

import java.util.List;

@Getter
@Setter
public class AllResponseApply {
    private List<List<Apply>> entities;
    private FailureMessage failureMessage;
    private boolean requestFailed;

}
