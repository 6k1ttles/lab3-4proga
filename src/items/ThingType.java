package items;

import java.util.Random;

/**
 * Перечисление типов предметов с диапазонами характеристик.
 */
public enum ThingType {
    KNIFE("Ножичек", 10.0, 20.0, 2.0, 8.0, true),
    BONE("Кость", 100.0, 180.0, 30.0, 50.0, true),
    STICK("Палка", 20.0, 60.0, 10.0, 30.0, true),
    STONE("Булыжник", 400.0, 600.0, 5.0, 15.0, false);

    private final String baseName;
    private final double minStrength;
    private final double maxStrength;
    private final double minAction;
    private final double maxAction;
    private final boolean isTool;

    private static final Random random = new Random();

    ThingType(String baseName, double minStrength, double maxStrength,
              double minAction, double maxAction, boolean isTool) {
        this.baseName = baseName;
        this.minStrength = minStrength;
        this.maxStrength = maxStrength;
        this.minAction = minAction;
        this.maxAction = maxAction;
        this.isTool = isTool;
    }

    public String getBaseName() {
        return baseName;
    }

    /**
     * Создает предмет с уникальным именем и рандомными статами в пределах диапазона
     * @param customName Уникальное имя
     * @return Экземпляр Thing
     */
    public Thing create(String customName) {
        double rndStrength = minStrength + (maxStrength - minStrength) * random.nextDouble();
        double rndAction = minAction + (maxAction - minAction) * random.nextDouble();

        rndStrength = Math.round(rndStrength * 10.0) / 10.0;
        rndAction = Math.round(rndAction * 10.0) / 10.0;

        if (isTool) {
            return new ToolItem(customName, rndStrength, rndAction);
        } else {
            return new HammerItem(customName, rndStrength, rndAction);
        }
    }
}