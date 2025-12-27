package model;

import exceptions.BottleSmashedException;
import exceptions.ItemBrokenException;
import interfaces.Locatable;

public class Bottle implements Locatable {
    private final String name = "Старый глиняный кувшин";
    private final BottleBody body;
    private final BottleCork cork;
    private final Point position = Point.ZERO;

    /**
     * @param bodyDurability     Прочность тела кувшинаа
     * @param corkDurability     Прочность самой пробки (крышки)
     * @param insertionTightness Тугость (насколько глубоко сидит / сопротивление забиванию)
     * @param pullForceRequired  Сила вытягивания
     */
    public Bottle(double bodyDurability, double corkDurability, double insertionTightness, double pullForceRequired) {
        this.body = new BottleBody(bodyDurability);
        this.cork = new BottleCork(corkDurability, insertionTightness, pullForceRequired);
    }

    @Override
    public Point getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return cork.isRemoved();
    }

    public boolean isSmashed() {
        return body.isBroken();
    }

    public double getInsertionTightness() {
        return cork.Tightness;
    }

    public double getPullForceRequired() {
        return cork.pullForceRequired;
    }

    public double getBodyDurability() {
        return body.durability;
    }

    public double getCorkDurability() {
        return cork.durability;
    }

    public String getStatusString() {
        if (isSmashed()) return name + " (разбит)";
        if (isOpen()) return name + " (открыт)";
        return String.format("%s (Закрыт | Кувшин: %.1f | Крышка: %.1f)", name, body.durability, cork.durability);
    }

    public void hitCork(double impact) throws Exception {
        if (isSmashed()) throw new BottleSmashedException();

        cork.Tightness -= impact;
        if (cork.Tightness < 0) cork.Tightness = 0;
        cork.pullForceRequired -= (impact * 0.2);
        cork.reduceDurability(impact * 0.15);
        body.reduceDurability(impact * 0.1);
    }

    public void smashBody(double impact) {
        try {
            body.reduceDurability(impact);
        } catch (ItemBrokenException e) {
            throw new BottleSmashedException();
        }
    }

    /**
     * Попытка вытянуть пробку
     *
     * @param effectiveForce       Итоговая сила тяги
     * @param corkDamageMultiplier Множитель урона по крышке
     */
    public void pullCork(double effectiveForce, double corkDamageMultiplier) {
        if (isSmashed() || isOpen()) return;

        // Открытие силой
        if (effectiveForce >= cork.pullForceRequired) {
            cork.isPulledOut = true;
            return;
        }

        // Разрушение кувшина
        if (effectiveForce > body.durability) {
            body.durability = 0;
            throw new BottleSmashedException();
        }

        // Нанесение урона
        try {
            body.reduceDurability(effectiveForce * 0.5);
            cork.reduceDurability(effectiveForce * 0.05 * corkDamageMultiplier);

        } catch (ItemBrokenException e) {
            if (body.isBroken()) throw new BottleSmashedException();
        }
    }

    private static class BottleBody {
        private double durability;

        public BottleBody(double durability) {
            this.durability = durability;
        }

        public boolean isBroken() {
            return durability <= 0;
        }

        public void reduceDurability(double amount) throws ItemBrokenException {
            this.durability -= amount;
            if (this.durability <= 0) throw new ItemBrokenException("Стенка кувшина");
        }
    }

    private static class BottleCork {
        private double durability;
        private double Tightness;
        private double pullForceRequired;
        private boolean isPulledOut = false;

        public BottleCork(double durability, double Tightness, double pullForceRequired) {
            this.durability = durability;
            this.Tightness = Tightness;
            this.pullForceRequired = pullForceRequired;
        }

        public boolean isRemoved() {
            return isPulledOut || durability <= 0;
        }

        public void reduceDurability(double amount) {
            this.durability -= amount;
            if (this.durability < 0) this.durability = 0;
        }
    }
}