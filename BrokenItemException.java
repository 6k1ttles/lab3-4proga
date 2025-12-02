public class BrokenItemException extends Exception {
    public BrokenItemException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}