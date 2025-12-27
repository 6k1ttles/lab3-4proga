package model;
import interfaces.Hammer;

/**
 * Предмет, используемый как молоток.
 */
public class StoneItem extends Thing implements Hammer {
    private final double impactDamage;

    /**
     * @param name Название
     * @param durability Прочность
     * @param position Координаты
     * @param impactDamage Урон удара
     */
    public StoneItem(String name, double durability, Point position, double impactDamage) {
        super(name, durability, position);
        this.impactDamage = impactDamage;
    }

    @Override
    public double getImpactDamage() { return impactDamage; }

    @Override
    public String toString() {
        return super.toString() + " [Урон: " + impactDamage + "]";
    }
}