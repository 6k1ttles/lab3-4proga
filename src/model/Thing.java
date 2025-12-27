package model;

import exceptions.ItemBrokenException;
import interfaces.Durable;
import interfaces.Locatable;

public abstract class Thing implements Durable, Locatable {
    protected final String name;
    protected double durability;
    protected Point position;

    public Thing(String name, double durability, Point position) {
        this.name = name;
        this.durability = durability;
        this.position = position;
    }

    @Override
    public Point getPosition() { return position; }

    @Override
    public String getName() { return name; }

    @Override
    public boolean isBroken() { return durability <= 0; }

    @Override
    public double getDurability() { return durability; }

    @Override
    public void reduceDurability(double amount) throws ItemBrokenException {
        if (isBroken()) throw new ItemBrokenException(name);
        this.durability -= amount;
        if (this.durability <= 0) {
            this.durability = 0;
            throw new ItemBrokenException(name);
        }
    }

    @Override
    public String toString() {
        return String.format("%s (Прочность: %.1f, Коорд: [%.1f, %.1f])",
                name, durability, position.x(), position.y());
    }
}