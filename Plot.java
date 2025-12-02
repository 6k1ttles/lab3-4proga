import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Plot {
    private final Person niels;
    private final Bottle bottle;
    private final List<Thing> environment;

    public Plot() {
        this.niels = new Person("Нильс", 45.0, 15.0);
        this.bottle = new Bottle("Кувшин", 35.0, 95.0);
        this.environment = new ArrayList<>();

        for (ThingType type : ThingType.values()) {
            environment.add(type.create());
        }
    }

    public Thing findThing(ThingType type) {
        for (Thing thing : environment) {
            if (thing.getName().equals(type.get_name())) {
                return thing;
            }
        }
        return null;
    }

    public void tellStory() {
        System.out.println(niels.getName() + " с облегчением вздохнул и стал осматриваться кругом...");

        System.out.println("\nПространство вокруг кувшина (" + bottle.getStatsString() + ", Необходимо для открытия: " + bottle.getValueToCrash() + "):");

        Thing stone = findThing(ThingType.STONE);

        for (Thing t : environment) {
            System.out.println("- " + t.getStatsString());
        }

        List<ThingType> possibleTypes = new ArrayList<>(Arrays.asList(ThingType.values()));
        Collections.shuffle(possibleTypes);

        for (ThingType type : possibleTypes) {
            if (type == ThingType.STONE) {
                Thing stoneItem = findThing(type);
                processItem(stoneItem, stone);
                continue;
            }

            Thing chosenItem = findThing(type);
            if (chosenItem == null) continue;

            processItem(chosenItem, stone);

            if (bottle.isOpen() || bottle.isBroken()) {
                break;
            }
        }
    }

    private void processItem(Thing chosenItem, Thing stone) {
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
            System.out.println("Ошибка: " + e.getMessage());
            System.out.println(niels.getName() + " отбрасывает этот предмет.");
        }
    }
}