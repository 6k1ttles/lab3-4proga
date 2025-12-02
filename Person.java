public class Person {
    private final String name;
    private final double bodyWeight;
    private final double muscleBonus;

    public Person(String name, double bodyWeight, double muscleBonus) {
        this.name = name;
        this.bodyWeight = bodyWeight;
        this.muscleBonus = muscleBonus;
    }

    public String getName() {
        return name;
    }

    public TryResult attemptOpen(Bottle bottle, Thing tool, Thing hammer) {
        if (tool instanceof Bottle || (tool instanceof IHammer && !(tool instanceof Tool))) {
            throw new UsageException("Нельзя использовать " + tool.getName() + " как долото!");
        }

        if (!(tool instanceof Tool)) {
            return new TryResult(false, "Это вообще не инструмент.");
        }

        Tool pryTool = (Tool) tool;

        try {
            if (hammer != null && hammer instanceof IHammer) {
                System.out.println("Он наставил предмет \"" + tool.getName() + "\", как долото, между крышкой и горлышком и начал бить по ней камнем.");

                double damage = ((IHammer) hammer).getImpactDamage();
                hammer.endureLoad(damage / 2);
                tool.endureLoad(damage);
                bottle.endureLoad(5.0);

                System.out.println("Он бил до тех пор, пока " + tool.getName() + " не вошел почти наполовину.");
                System.out.println("   [Состояние]: " + bottle.getStatsString() + " | " + tool.getStatsString());
            }

            if (bottle.isBroken()) return new TryResult(false, "Кувшин разбит во время подготовки.");

            double passiveForce = pryTool.getBaseOpeningForce() + bodyWeight;

            System.out.println("Тогда " + name + " обеими руками ухватился за конец, торчавший снаружи, и повис на нем вместо груза.");
            System.out.printf("   (Текущая нагрузка на предмет: %.1f кг | Итоговая сила открытия: %.1f кг)\n", passiveForce, passiveForce);

            tool.endureLoad(passiveForce);
            bottle.applyForce(passiveForce);

            if (bottle.isOpen()) return new TryResult(true, "Открыто весом.");
            if (bottle.isBroken()) return new TryResult(false, "Сломано весом.");

            System.out.println("Он весь натужился, напряг мускулы и даже поджал ноги к животу, чтобы стать потяжелее.");

            double activeForce = passiveForce + muscleBonus;
            System.out.printf("   (Обновленная нагрузка: %.1f кг | Максимальная сила: %.1f кг)\n", activeForce, activeForce);

            tool.endureLoad(activeForce);
            bottle.applyForce(activeForce);

            if (bottle.isOpen()) return new TryResult(true, "Открыто с усилием.");
            else return new TryResult(false, "Сил не хватило.");

        } catch (BrokenItemException e) {
            System.out.println(e.getMessage());
            return new TryResult(false, "Инструмент сломался.");
        }
    }
}