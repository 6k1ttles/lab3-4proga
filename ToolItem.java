/**
 * Класс для предметов-инструментов
 * Реализует интерфейс {@link IPryTool}
 */
public class ToolItem extends Thing implements Tool {
    private final double leverageForce;
    /**
     * Создает инструмент
     * @param name Название
     * @param strength Прочность
     * @param leverageForce Сила рычага (доп к весу персонажа)
     */
    public ToolItem(String name, double strength, double leverageForce) {
        super(name, strength);
        this.leverageForce = leverageForce;
    }

    @Override
    public String getTypeName() {
        return "[Инструмент]";
    }

    @Override
    public double getBaseOpeningForce() {
        return leverageForce;
    }

    @Override
    public String getStatsString() {
        return super.getStatsString() + ", Сила отткрытия: " + leverageForce;
    }

}
