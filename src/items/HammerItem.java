package items;

import interfaces.Hammer;

/**
 * Класс для ударных инструментов
 * Реализует интерфейс {@link Hammer}
 */
public class HammerItem extends Thing implements Hammer {
    private final double impactDamage;
    /**
     * Создает ударный предмет
     * @param name Название
     * @param strength Прочность
     * @param impactDamage Урон
     */
    public HammerItem(String name, double strength, double impactDamage) {
        super(name, strength);
        this.impactDamage = impactDamage;
    }

    @Override
    public String getTypeName() {
        return "[Молот]";
    }

    @Override
    public double getImpactDamage() {
        return impactDamage;
    }

}
