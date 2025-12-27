package interfaces;
import model.Point;

/**
 * Интерфейс для объектов, имеющих координаты в пространстве
 */
public interface Locatable {
    /**
     * @return Координаты объекта
     */
    Point getPosition();
}