package interfaces;

/**
 * Интерфейс для ударных инструментов
 */
public interface Hammer extends Durable, Locatable {
    /**
     * @return Урон от удара
     */
    double getImpactDamage();
}