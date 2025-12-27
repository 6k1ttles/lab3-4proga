package model;

import java.util.Random;

/**
 * Перечисление типов рычагов с диапазонами характеристик.
 */
public enum LeverType {
    STICK("Трухлявая палка", 10, 30, 5, 15),
    BONE("Крепкая кость", 50, 120, 20, 45),
    CLUB("Тяжелая дубинка", 80, 150, 35, 60),
    IRON_BAR("Ржавый прут", 150, 300, 60, 100);

    private final String name;
    private final double minDurability;
    private final double maxDurability;
    private final double minForce;
    private final double maxForce;
    private static final Random random = new Random();

    LeverType(String name, double minDurability, double maxDurability, double minForce, double maxForce) {
        this.name = name;
        this.minDurability = minDurability;
        this.maxDurability = maxDurability;
        this.minForce = minForce;
        this.maxForce = maxForce;
    }

    /**
     * Генерирует прочность в диапазоне типа.
     */
    public double getRandomDurability() {
        return minDurability + (maxDurability - minDurability) * random.nextDouble();
    }

    /**
     * Генерирует силу рычага в диапазоне типа.
     */
    public double getRandomForce() {
        return minForce + (maxForce - minForce) * random.nextDouble();
    }

    public String getName() {
        return name;
    }

    /**
     * Возвращает случайный тип из списка
     */
    public static LeverType getRandom() {
        return values()[random.nextInt(values().length)];
    }
}