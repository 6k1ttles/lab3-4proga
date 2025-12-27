package exceptions;

/**
 * Исключение, выбрасываемое при поломке предмета от износа
 */
public class ItemBrokenException extends Exception {
    /**
     * @param name Название сломанного предмета
     */
    public ItemBrokenException(String name) {
        super("Предмет '" + name + "' сломался.");
    }
}