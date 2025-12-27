package world;

import model.Thing;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, хранящий состояние окружающего мира и предметов.
 */
public class World {
    private final List<Thing> items = new ArrayList<>();

    /**
     * Добавляет предмет на сцену.
     * @param item Предмет
     */
    public void addItem(Thing item) {
        items.add(item);
    }

    /**
     * @return Копия списка предметов на сцене
     */
    public List<Thing> getItems() {
        return new ArrayList<>(items);
    }
}