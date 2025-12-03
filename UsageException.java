/**
 * Исключение, возникающее при попытке использовать предмет не по назначению.
 */
public class UsageException extends RuntimeException {
    public UsageException(String message) {
        super(message);
    }

}
