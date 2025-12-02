public class HammerItem extends Thing implements IHammer {
    private final double impactDamage;

    public HammerItem(String name, double strength, double impactDamage) {
        super(name, strength);
        this.impactDamage = impactDamage;
    }

    @Override
    public String getTypeName() {
        return "[Молот]";
    }

    @Override
    public double getImpactDamage() {
        return impactDamage;
    }
}