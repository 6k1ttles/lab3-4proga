package interfaces;

/**
 * Интерфейс для инструментов-рычагов
 */
public interface Lever extends Durable, Locatable {
    /**
     * @return Сила рычага
     */
    double getLeverageForce();
}