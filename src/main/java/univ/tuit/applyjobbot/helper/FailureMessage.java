package univ.tuit.applyjobbot.helper;

public class FailureMessage {
    private String exceptionName;
    private String exceptionMessage;

    public FailureMessage(String exceptionName, String exceptionMessage) {
        this.exceptionName = exceptionName;
        this.exceptionMessage = exceptionMessage;
    }

    public FailureMessage() {
    }

    private static FailureMessage build(String name, String exceptionMessage) {
        return new FailureMessage(name, exceptionMessage);
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
