package items;

import exceptions.BrokenItemException;

/**
 * Класс целевого предмета — Кувшина
 * Имеет состояние открыто/закрыто и порог силы для открытия
 */
public class Bottle extends Thing {
    private boolean isOpeen = false;
    private final double openingValue;
     /**
     * Создает бутылку
     * @param name Название
     * @param strength Прочность
     * @param openingValue Сила в кг, необходимая для открытия
     */
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
    /**
     * Попытка применить силу к пробке
     * Если силы достаточно -> кувшин открывается
     * Если нет -> сила уменьшает прочность кувшина
     * @param force Приложенная сила в кг
     */
    public void applyForce(double force) {
        if (isBroken || isOpeen) return;

        if (force >= openingValue) {
            isOpeen = true;
            System.out.println("Пробка вылетела");
        } else {
            System.out.println("Пробка не поддается");
            try {
                // Если не открылась -> 50% силы уходит в урон
                this.endureLoad(force * 0.5);
            } catch (BrokenItemException e) {
                System.out.println(e.getMessage());
                System.out.println("Кувшин разлетелся");
            }
        }
    }

}
