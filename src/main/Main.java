package main;

import logic.InteractionMethods;
import model.*;
import world.World;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        World world = new World();

        //окружение
        world.addItem(new StoneItem("Камень-1", 20, randomPoint(5), 10));
        world.addItem(LeverItem.createFromType(LeverType.BONE, randomPoint(8), 1));

        //Случайное наполнение
        int randomItemsCount = 2 + random.nextInt(5);
        int stoneCounter = 2;
        int leverCounter = 2;
        for (int i = 0; i < randomItemsCount; i++) {
            if (random.nextBoolean()) {
                String name = "Камень-" + stoneCounter++;
                double dur = 10 + random.nextDouble() * 20;
                double dmg = (5 + random.nextDouble() * 10);
                world.addItem(new StoneItem(name, dur, randomPoint(20), dmg));
            } else {
                world.addItem(LeverItem.createRandom(randomPoint(20), leverCounter++));
            }
        }

        // Кувшин
        Bottle bottle = new Bottle(50+ random.nextDouble() * 70,
                40 + random.nextDouble() * 20,
                50 + random.nextDouble() * 30,
                130 + random.nextDouble() * 30);

        // Нильс
        double nielsWeight = 45 + random.nextDouble() * 15;
        double nielsMuscle = 5 + random.nextDouble() * 10;
        Niels niels = new Niels(new Point(0 + random.nextDouble() * 10, 0 + random.nextDouble() * 10), 100.0, nielsWeight, nielsMuscle);

        // Пуск
        InteractionMethods strategies = new InteractionMethods();
        strategies.openMethod(niels, bottle, world);
    }

    private static Point randomPoint(double radius) {
        double x = (Math.random() - 0.5) * 2 * radius;
        double y = (Math.random() - 0.5) * 2 * radius;
        x = Math.round(x * 10.0) / 10.0;
        y = Math.round(y * 10.0) / 10.0;
        return new Point(x, y);
    }
}