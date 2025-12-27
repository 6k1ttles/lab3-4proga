package exceptions;

/**
 * Исключение, выбрасываемое при уничтожении кувшина
 */
public class BottleSmashedException extends RuntimeException {
    public BottleSmashedException() {
        super("Кувшин разбился.");
    }
}