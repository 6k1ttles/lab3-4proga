/**
 * Перечисление типов предметов
 * Для создания экземпляров этих предметов
 */
public enum ThingType {
    KNIFE("Ножичек", 15.0, 5.0, true),
    BONE("Кость", 150.0, 40.0, true),
    STICK("Палка", 40.0, 20.0, true),
    STONE("Булыжник", 500.0, 10.0, false);

    private final String name;
    private final double strength;
    private final double actionValue;
    private final boolean isTool;
    /**
     * Конструктор элемента перечисления
     * @param name Название предмета
     * @param strength Начальная прочность предмета
     * @param actionValue Сила рычага или урон удара
     * @param isTool Является ли предмет рычагом
     */
    ThingType(String name, double strength, double actionValue, boolean isTool) {
        this.name = name;
        this.strength = strength;
        this.actionValue = actionValue;
        this.isTool = isTool;
    }
    /**
     * @return Название типа предмета
     */
    public String get_name() {
        return name;
    }
     /**
     * Метод для создания объекта предмета
     * @return Экземпляр {@link PryToolItem} или {@link HammerItem} (зависит от флага isTool)
     */
    public Thing create() {
        if (isTool) {
            return new ToolItem(name, strength, actionValue);
        } else {
            return new HammerItem(name, strength, actionValue);
        }
    }

}
