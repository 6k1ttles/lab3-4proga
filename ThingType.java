public enum ThingType {
    KNIFE("Ножичек", 15.0, 5.0, true),
    BONE("Кость", 150.0, 40.0, true),
    STICK("Палка", 40.0, 20.0, true),
    STONE("Булыжник", 500.0, 10.0, false);

    private final String name;
    private final double strength;
    private final double actionValue;
    private final boolean isTool;

    ThingType(String name, double strength, double actionValue, boolean isTool) {
        this.name = name;
        this.strength = strength;
        this.actionValue = actionValue;
        this.isTool = isTool;
    }

    public String get_name() {
        return name;
    }

    public Thing create() {
        if (isTool) {
            return new ToolItem(name, strength, actionValue);
        } else {
            return new HammerItem(name, strength, actionValue);
        }
    }
}