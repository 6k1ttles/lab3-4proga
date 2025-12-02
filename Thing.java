import java.util.UUID;

public abstract class Thing {
    protected final String name;
    protected double strength;
    protected boolean isBroken = false;
    private final UUID id = UUID.randomUUID();

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

    public boolean isBroken() {
        return isBroken;
    }

    public abstract String getTypeName();

    public String getStatsString() {
        return String.format("%s %s (Прочность: %.1f)",
                getTypeName(), name, strength);
    }

    public void endureLoad(double load) throws BrokenItemException {
        if (isBroken) return;

        this.strength -= (load * 0.1);
        this.strength = Math.round(this.strength * 10.0) / 10.0;

        if (load > (this.strength + 2.0) || this.strength <= 0) {
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