package model;

import interfaces.Lever;

public class LeverItem extends Thing implements Lever {
    private final double leverageForce;

    public LeverItem(String name, double durability, Point position, double leverageForce) {
        super(name, durability, position);
        this.leverageForce = leverageForce;
    }

    @Override
    public double getLeverageForce() { return leverageForce; }

    @Override
    public String toString() {
        return super.toString() + " [Рычаг: " + String.format("%.1f", leverageForce) + "]";
    }


    /**
     * Создает случайный рычаг (выбирает случайный тип из Enum).
     * @param position Позиция
     * @param id Уникальный номер для имени
     * @return Новый LeverItem
     */
    public static LeverItem createRandom(Point position, int id) {
        LeverType type = LeverType.getRandom();
        return createFromType(type, position, id);
    }

    /**
     * Создает рычаг конкретного типа.
     */
    public static LeverItem createFromType(LeverType type, Point position, int id) {
        String fullName = String.format("%s-%d", type.getName(), id);
        double durability = type.getRandomDurability();
        double force = type.getRandomForce();

        return new LeverItem(fullName, durability, position, force);
    }
}