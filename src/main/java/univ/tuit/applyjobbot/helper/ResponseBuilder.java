package univ.tuit.applyjobbot.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseBuilder {

    public static ResponseEntity<APIResponse> buildOk(List<Object> entities) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.getEntities().addAll(entities);
        apiResponse.setRequestFailed(false);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    public static ResponseEntity<APIResponse> buildOK(Object entity, FailureMessage message, HttpStatus httpStatus) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.getEntities().add(entity);
        apiResponse.setFailureMessage(message);
        apiResponse.setRequestFailed(entity == null);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
