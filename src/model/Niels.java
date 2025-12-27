package model;

import interfaces.Locatable;
import world.World;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Класс персонажа.
 */
public class Niels implements Locatable {
    private Point position;
    private double stamina;
    private final double bodyWeight;
    private final double musclePower;
    private final List<Thing> knownItems = new ArrayList<>();

    public Niels(Point startPos, double stamina, double bodyWeight, double musclePower) {
        this.position = startPos;
        this.stamina = stamina;
        this.bodyWeight = bodyWeight;
        this.musclePower = musclePower;
    }

    @Override
    public Point getPosition() { return position; }
    public double getBodyWeight() { return bodyWeight; }

    public void printIntroduction() {
        System.out.printf("Нильс [Вес: %.1f кг, Мышцы: %.1f кг, Выносливость: %.1f%%]%n",
                bodyWeight, musclePower, stamina);
        System.out.printf("Текущая позиция: [%.1f, %.1f]%n", position.x(), position.y());
    }

    public void lookAround(World world, Bottle bottle) {
        System.out.println("Нильс с облегчением вздохнул и стал осматриваться кругом: нет ли поблизости чего-нибудь покрепче да побольше?");

        System.out.printf("   [Цель] %s%n", bottle.getStatusString());

        this.knownItems.clear();
        this.knownItems.addAll(world.getItems());

        knownItems.sort(Comparator.comparingDouble(item -> this.position.distanceTo(item.getPosition())));

        System.out.println("   [Окружение (" + knownItems.size() + " предметов)]:");
        for (Thing item : knownItems) {
            double dist = this.position.distanceTo(item.getPosition());
            System.out.printf("   - %s | Дистанция: %.1f м%n", item.toString(), dist);
        }
    }

    public List<Thing> getKnownItems() { return knownItems; }

    public void moveTo(Point target) {
        double distance = this.position.distanceTo(target);
        if (distance > 0.1) {
            double fatigue = distance * 0.5;
            this.stamina -= fatigue;
            if (this.stamina < 0) this.stamina = 0;

            System.out.printf("Нильс идет к [%.1f, %.1f]. (Пройдено: %.1f м, Усталость: -%.1f%%, Остаток сил: %.1f%%)%n",
                    target.x(), target.y(), distance, fatigue, stamina);
            this.position = target;
        }
    }

    public double getCurrentMusclePower() {
        double fatigueFactor = stamina / 100.0;
        return musclePower * fatigueFactor;
    }
}