import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Класс-рассказчик:
 * Инициализирует мир, персонажей и управляет ходом истории.
 */
public class Plot {
    private final Person niels;
    private final Bottle bottle;
    private final List<Thing> environment;

    public Plot() {
        this.niels = new Person("Нильс", 45.0, 15.0);
        this.bottle = new Bottle("Кувшин", 35.0, 95.0);
        this.environment = new ArrayList<>();
        // Генерация предметов из Enum
        for (ThingType type : ThingType.values()) {
            environment.add(type.create());
        }
    }
    /**
     * Ищет предмет определенного типа в окружении
     * @param type Тип искомого предмета
     * @return Найденный предмет или null
     */
    public Thing findThing(ThingType type) {
        for (Thing thing : environment) {
            if (thing.getName().equals(type.get_name())) {
                return thing;
            }
        }
        return null;
    }
    /**
     * Основной цикл повествования
     */
    public void tellStory() {
        System.out.println(niels.getName() + " с облегчением вздохнул и стал осматриваться кругом...");

        System.out.println("\nПространство вокруг кувшина (" + bottle.getStatsString() + ", Необходимо для открытия: " + bottle.getValueToCrash() + "):");
        // Камень, используется как молоток
        Thing stone = findThing(ThingType.STONE);

        for (Thing t : environment) {
            System.out.println("- " + t.getStatsString());
        }
         // Список всех возможных типов предметов для перебора
        List<ThingType> possibleTypes = new ArrayList<>(Arrays.asList(ThingType.values()));
        Collections.shuffle(possibleTypes);
        // Цикл попыток
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
    /**
     * Выбор предмета: вывод текста и действия персонажа
     * @param chosenItem Выбранный предмет
     * @param stoneHelper Вспомогательный предмет
     */
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
            // Обработка логической ошибки
            System.out.println("Ошибка: " + e.getMessage());
            System.out.println(niels.getName() + " отбрасывает этот предмет.");
        }
    }

}
