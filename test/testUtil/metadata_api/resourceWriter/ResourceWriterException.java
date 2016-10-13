package testUtil.metadata_api.resourceWriter;


public class ResourceWriterException extends Exception{

    public ResourceWriterException() {
    }

    public ResourceWriterException(String message) {
        super(message);
    }

    public ResourceWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
