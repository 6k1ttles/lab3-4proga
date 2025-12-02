public class Bottle extends Thing {
    private boolean isOpeen = false;
    private final double openingValue;

    public Bottle(String name, double strength, double openingValue) {
        super(name, strength);
        this.openingValue = openingValue;
    }

    @Override
    public String getTypeName() {
        return "[Цель]";
    }

    public boolean isOpen() {
        return isOpeen;
    }

    public double getValueToCrash() {
        return openingValue;
    }

    public void applyForce(double force) {
        if (isBroken || isOpeen) return;

        if (force >= openingValue) {
            isOpeen = true;
            System.out.println("Пробка вылетела");
        } else {
            System.out.println("Пробка не поддается");
            try {
                this.endureLoad(force * 0.5);
            } catch (BrokenItemException e) {
                System.out.println(e.getMessage());
                System.out.println("Кувшин разлетелся");
            }
        }
    }
}