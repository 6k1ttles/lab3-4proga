package logic;

import exceptions.BrokenItemException;
import exceptions.UsageException;
import interfaces.Hammer;
import interfaces.Tool;
import items.Bottle;
import items.Thing;

/**
 * Класс Нильса
 */
public class Person {
    private final String name;
    private final double bodyWeight;
    private final double muscleBonus;

    public Person(String name, double bodyWeight, double muscleBonus) {
        this.name = name;
        this.bodyWeight = bodyWeight;
        this.muscleBonus = muscleBonus;
    }

    public String getName() { return name; }
    public double getBodyWeight() { return bodyWeight; }
    public double getMuscleBonus() { return muscleBonus; }

    /**
     * Попытка открыть бутылку
     * @param bottle Целевой объект
     * @param tool Выбранный инструмент
     * @param hammer Предмет для ударов
     */
    public TryResult attemptOpen(Bottle bottle, Thing tool, Thing hammer) {
        // Нельзя открывать молотком или самой бутылкой
        if (tool instanceof Bottle || (tool instanceof Hammer && !(tool instanceof Tool))) {
            throw new UsageException("Нельзя использовать " + tool.getName() + " как долото!");
        }

        if (!(tool instanceof Tool)) {
            return new TryResult(false, "Это вообще не инструмент.");
        }

        Tool pryTool = (Tool) tool;

        try {
            // 1) Удары по крышке кувшина
            performHammeringPhase(bottle, tool, hammer);

            if (bottle.isBroken()) return new TryResult(false, "Кувшин разбит во время подготовки.");

            // 2) Повисание
            if (performHangingPhase(bottle, tool, pryTool)) {
                return new TryResult(true, "Открыто весом.");
            }

            if (bottle.isBroken()) return new TryResult(false, "Кувшин раздавлен весом.");
            if (tool.isBroken()) return new TryResult(false, "Инструмент сломался под весом.");

            // 3) Напряжение мышц
            if (performStrainingPhase(bottle, tool, pryTool, hammer)) {
                return new TryResult(true, "Открыто с усилием.");
            }

            return new TryResult(false, "Сил не хватило.");

        } catch (BrokenItemException e) {
            System.out.println("   " + e.getMessage());
            return new TryResult(false, "Инструмент сломался в процессе.");
        }
    }


    private void performHammeringPhase(Bottle bottle, Thing tool, Thing hammer) throws BrokenItemException {
        if (hammer != null && hammer instanceof Hammer) {
            System.out.println("Он наставил предмет \"" + tool.getName() + "\", как долото, между крышкой и горлышком и начал бить по ней камнем.");

            double damage = ((Hammer) hammer).getImpactDamage();

            // Нанесение урона
            hammer.endureLoad(damage / 2);
            tool.endureLoad(damage);
            bottle.endureLoad(5.0);

            System.out.println("Он бил до тех пор, пока " + tool.getName() + " не вошел почти наполовину.");
            System.out.println("   [Состояние]: " + bottle.getStatsString() + " | " + tool.getStatsString());
        }
    }

    private boolean performHangingPhase(Bottle bottle, Thing tool, Tool pryTool) throws BrokenItemException {
        double passiveForce = pryTool.getBaseOpeningForce() + bodyWeight;

        System.out.println("Тогда " + name + " обеими руками ухватился за конец, торчавший снаружи, и повис на нем вместо груза.");
        System.out.printf("   (Текущая нагрузка: %.1f кг)%n", passiveForce);

        tool.endureLoad(passiveForce);
        bottle.applyForce(passiveForce);

        return bottle.isOpen();
    }

    private boolean performStrainingPhase(Bottle bottle, Thing tool, Tool pryTool, Thing hammer) throws BrokenItemException {
        double passiveForce = pryTool.getBaseOpeningForce() + bodyWeight;
        System.out.println("Он весь натужился, напряг мускулы и даже поджал ноги к животу, чтобы стать потяжелее.");
        double activeForce = passiveForce + muscleBonus;
        System.out.printf("   (Максимальная сила: %.1f кг)%n", activeForce);
        tool.endureLoad(activeForce);
        bottle.applyForce(activeForce);
        return bottle.isOpen();
    }
}