public class ToolItem extends Thing implements Tool {
    private final double leverageForce;

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