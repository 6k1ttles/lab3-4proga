package interfaces;
import exceptions.ItemBrokenException;

/**
 * Интерфейс для предметов, обладающих прочностью
 */
public interface Durable {
    /**
     * Уменьшает прочность предмета
     * @param amount Величина урона
     * @throws ItemBrokenException Если прочность падает до нуля
     */
    void reduceDurability(double amount) throws ItemBrokenException;

    /**
     * @return true, если предмет сломан
     */
    boolean isBroken();

    /**
     * @return Название предмета
     */
    String getName();
    double getDurability();
}