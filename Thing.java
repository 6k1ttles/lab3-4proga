import java.util.UUID;

/**
 * Абстрактный базовый класс для всех физических объектов
 * Содержит свойства имя, прочность и механику разрушения
 */
public abstract class Thing {
    protected final String name;
    protected double strength;
    protected boolean isBroken = false;
    private final UUID id = UUID.randomUUID();
    /**
     * Создает новый предмет
     * @param name Название предмета
     * @param strength Начальная прочность
     */
    public Thing(String name, double strength) {
        this.name = name;
        this.strength = strength;
    }

    public String getName() {
        return name;
    }

    public double getStrength() {
        return strength;
    }
    /**
     * @return true, если предмет сломан и не может быть использован
     */
    public boolean isBroken() {
        return isBroken;
    }
    /**
     * Абстрактный метод для получения типа предмета
     * @return Строковое представление типа
     */
    public abstract String getTypeName();
    /**
     * Формирует строку с хар-ками предмета
     * @return Строка вида "[Тип] Имя (Прочность: X)"
     */
    public String getStatsString() {
        return String.format("%s %s (Прочность: %.1f)",
                getTypeName(), name, strength);
    }
    /**
     * Применяет нагрузку к предмету -> уменьшая его прочность
     * Если нагрузка превышает прочность -> предмет ломается
     *
     * @param load Величина нагрузки
     * @throws ItemBrokenException Если предмет ломается
     */
    public void endureLoad(double load) throws BrokenItemException {
        if (isBroken) return;
        // Уменьшение прочности предмета
        this.strength -= (load * 0.1);
        this.strength = Math.round(this.strength * 10.0) / 10.0;
         // Проверка на поломку
        // или если прочность упала до нуля.
        if (load > (this.strength + 0.1) || this.strength <= 0) {
            this.isBroken = true;
            this.strength = 0;
            throw new BrokenItemException("Предмет '" + name + "' сломался от нагрузки!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Thing)) return false;
        Thing thing = (Thing) o;
        return id.equals(thing.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

}
