/**
 * Класс Нильса
 * Содержит основную логику использования предметов
 */
public class Person {
    private final String name;
    private final double bodyWeight;
    private final double muscleBonus;
    /**
     * Создает персонажа
     * @param name Имя
     * @param bodyWeight Вес тела (для давления без усилий)
     * @param muscleBonus Дополнительная сила (при усилии)
     */
    public Person(String name, double bodyWeight, double muscleBonus) {
        this.name = name;
        this.bodyWeight = bodyWeight;
        this.muscleBonus = muscleBonus;
    }

    public String getName() {
        return name;
    }
    /**
     * попытка открыть бутылку выбранным предметом
     * три фазы: удары, повисание и напряжение
     *
     * @param bottle Целевой объект
     * @param tool Выбранный инструмент
     * @param hammer Предмет для ударов
     * @return Объект {@link TryResult} с итогом попытки
     * @throws InappropriateUsageException Если tool не является рычагом
     */
    public TryResult attemptOpen(Bottle bottle, Thing tool, Thing hammer) {
        // Нельзя открывать молотком или самой бутылкой
        if (tool instanceof Bottle || (tool instanceof IHammer && !(tool instanceof Tool))) {
            throw new UsageException("Нельзя использовать " + tool.getName() + " как долото!");
        }

        if (!(tool instanceof Tool)) {
            return new TryResult(false, "Это вообще не инструмент.");
        }

        Tool pryTool = (Tool) tool;

        try {
            // 1) Удары по крышке кувшина
            if (hammer != null && hammer instanceof IHammer) {
                System.out.println("Он наставил предмет \"" + tool.getName() + "\", как долото, между крышкой и горлышком и начал бить по ней камнем.");

                double damage = ((IHammer) hammer).getImpactDamage();
                // Нанесение урона интсрументам и кувшину
                hammer.endureLoad(damage / 2);
                tool.endureLoad(damage);
                bottle.endureLoad(5.0);

                System.out.println("Он бил до тех пор, пока " + tool.getName() + " не вошел почти наполовину.");
                System.out.println("   [Состояние]: " + bottle.getStatsString() + " | " + tool.getStatsString());
            }
            // Кувшин разбился при ударе
            if (bottle.isBroken()) return new TryResult(false, "Кувшин разбит во время подготовки.");
            // 2) Повисание
            double passiveForce = pryTool.getBaseOpeningForce() + bodyWeight;

            System.out.println("Тогда " + name + " обеими руками ухватился за конец, торчавший снаружи, и повис на нем вместо груза.");
            System.out.printf("   (Текущая нагрузка на предмет: %.1f кг | Итоговая сила открытия: %.1f кг)\n", passiveForce, passiveForce);

            tool.endureLoad(passiveForce);
            bottle.applyForce(passiveForce);

            if (bottle.isOpen()) return new TryResult(true, "Открыто весом.");
            if (bottle.isBroken()) return new TryResult(false, "Сломано весом.");
            // 3) Напряжение мышц
            System.out.println("Он весь натужился, напряг мускулы и даже поджал ноги к животу, чтобы стать потяжелее.");

            double activeForce = passiveForce + muscleBonus;
            System.out.printf("   (Обновленная нагрузка: %.1f кг | Максимальная сила: %.1f кг)\n", activeForce, activeForce);

            tool.endureLoad(activeForce);
            bottle.applyForce(activeForce);

            if (bottle.isOpen()) return new TryResult(true, "Открыто с усилием.");
            else return new TryResult(false, "Сил не хватило.");

        } catch (BrokenItemException e) {
            // Поломка инстурмента
            System.out.println(e.getMessage());
            return new TryResult(false, "Инструмент сломался.");
        }
    }

}
