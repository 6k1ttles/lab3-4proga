package logic;

import exceptions.UsageException;
import interfaces.Hammer;
import items.Bottle;
import items.Thing;
import items.ThingType;

import java.util.*;

/**
 * Сюжетиный класс
 */
public class Plot {
    private Person niels;
    private Bottle bottle;
    private List<Thing> environment;
    private final Random random = new Random();

    public Plot() {
        createRandomPerson();
        createBottle();
        initializeEnvironment();
    }

    private void createRandomPerson() {
        double weight = 10.0 + random.nextDouble() * 120.0;
        double muscles = 1.0 + random.nextDouble() * 24.0;

        this.niels = new Person("Нильс", weight, muscles);
    }

    private void createBottle() {
        double bottleDifficulty = 80.0 + random.nextDouble() * 40.0;
        this.bottle = new Bottle("Кувшин", 35.0, bottleDifficulty);
    }

    /**
     * Генерирует предметы окружения
     */
    private void initializeEnvironment() {
        this.environment = new ArrayList<>();

        addUniqueItem(ThingType.STONE);

        int itemsCount = 1 + random.nextInt(5);

        boolean mixedTypes = random.nextBoolean();
        ThingType forcedType = getRandomPryType();

        for (int i = 0; i < itemsCount; i++) {
            ThingType typeToAdd;

            if (mixedTypes) {
                typeToAdd = getRandomPryType();
            } else {
                typeToAdd = forcedType;
            }

            addUniqueItem(typeToAdd);
        }
    }

    /**
     * Добавляет предмет, вычисляя его порядковый номер на основе уже добавленных
     */
    private void addUniqueItem(ThingType type) {
        int count = 0;
        String baseName = type.getBaseName();

        // Проходим по списку и считаем сколько предметов этого типа уже есть
        for (Thing item : environment) {
            if (item.getName().startsWith(baseName)) {
                count++;
            }
        }

        int nextNumber = count + 1;
        String name = baseName + " " + nextNumber;
        environment.add(type.create(name));
    }

    private ThingType getRandomPryType() {
        List<ThingType> pryTypes = new ArrayList<>();
        for (ThingType t : ThingType.values()) {
            if (t != ThingType.STONE) {
                pryTypes.add(t);
            }
        }
        return pryTypes.get(random.nextInt(pryTypes.size()));
    }

    /**
     * Точка входа в историю
     */
    public void tellStory() {
        printIntroduction();
        Thing stone = findStone();

        List<Thing> toolsToTry = getShuffledTools();

        runSimulationLoop(toolsToTry, stone);
    }

    private void printIntroduction() {
        System.out.printf("Персонаж: %s (Вес: %.1f, Мышцы: %.1f)%n",
                niels.getName(), niels.getBodyWeight(), niels.getMuscleBonus());
        System.out.println(niels.getName() + " с облегчением вздохнул и стал осматриваться кругом...");
        System.out.println("\nПространство вокруг кувшина (" + bottle.getStatsString() +
                ", Порог открытия: " + String.format("%.1f", bottle.getValueToCrash()) + "):");

        for (Thing t : environment) {
            System.out.println("- " + t.getStatsString());
        }
    }

    private Thing findStone() {
        for (Thing t : environment) {
            if (t instanceof Hammer) return t;
        }
        return null;
    }

    private List<Thing> getShuffledTools() {
        List<Thing> tools = new ArrayList<>();
        for (Thing t : environment) {
            if (!(t instanceof Hammer)) {
                tools.add(t);
            }
        }
        Collections.shuffle(tools);
        return tools;
    }

    private void runSimulationLoop(List<Thing> tools, Thing stone) {
        if (random.nextBoolean()) {
            tools.add(findStone());
            Collections.shuffle(tools);
        }

        for (Thing chosenItem : tools) {
            if (bottle.isOpen() || bottle.isBroken()) break;

            processItemInteraction(chosenItem, stone);
        }

        printConclusion();
    }

    private void processItemInteraction(Thing chosenItem, Thing stone) {
        System.out.println("\n" + niels.getName() + " подобрал \"" + chosenItem.getName() + "\"");

        try {
            TryResult result = niels.attemptOpen(bottle, chosenItem, stone);

            if (result.success()) {
                System.out.println("\n" + niels.getName() + " открыл кувшин.");
            } else if (bottle.isBroken()) {
                System.out.println("\nКувшин разбит.");
            } else {
                System.out.println(result.message());
            }

        } catch (UsageException e) {
            System.out.println("Ошибка логики: " + e.getMessage());
            System.out.println(niels.getName() + " отбрасывает этот предмет.");
        }
    }

    private void printConclusion() {
        if (bottle.isOpen()) System.out.println("Нильс справился с задачей");
        else if (bottle.isBroken()) System.out.println("Нильс не справился с задачей");
        else System.out.println("Кувшин остался закрыт");
    }
}