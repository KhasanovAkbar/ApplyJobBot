package univ.tuit.applyjobbot.helper;


import univ.tuit.applyjobbot.ext.JsonSerializable;
import univ.tuit.applyjobbot.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class APIResponse implements JsonSerializable {

    private List<Object> entities;
    private boolean requestFailed;
    private FailureMessage failureMessage;

    public APIResponse() {
        entities = new ArrayList<>();
    }

    public static APIResponse fromJson(String json) {
        return (APIResponse) JsonUtil.fromJson(json, APIResponse.class);
    }

    public String toString() {
        return this.toJson();
    }

    public List<Object> getEntities() {
        return entities;
    }

    public void setEntities(List<Object> entities) {
        this.entities = entities;
    }

    public boolean isRequestFailed() {
        return requestFailed;
    }

    public void setRequestFailed(boolean requestFailed) {
        this.requestFailed = requestFailed;
    }

    public FailureMessage getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(FailureMessage failureMessage) {
        this.failureMessage = failureMessage;
    }
}
