package logic;

import exceptions.BottleSmashedException;
import interfaces.Hammer;
import interfaces.Lever;
import model.Bottle;
import model.GripPosition;
import model.Niels;
import world.World;

import java.util.Random;

public class InteractionMethods {
    private final Random random = new Random();

    public void smashMethod(Niels niels, Bottle bottle, World world) {

        // Осмотреться
        niels.printIntroduction();
        niels.lookAround(world, bottle);

        // Выбор самого мощного рычага
        Lever weapon = ToolSelector.chooseBestLever(niels, niels.getKnownItems());

        if (weapon == null) {
            System.out.println("Нильс не нашел ничего, чем можно было бы ударить.");
            return;
        }

        System.out.println("\nНильс выбирает оружие: \"" + weapon.getName() + "\".");

        // Перемещение
        niels.moveTo(weapon.getPosition());
        niels.moveTo(bottle.getPosition());

        // Удар
        System.out.println("\nНильс размахивается предметом \"" + weapon.getName() + "\" и со всей силы бьет по телу кувшина!");

        try {
            double impactForce = niels.getCurrentMusclePower() + weapon.getLeverageForce();

            System.out.printf("   (Сила удара: %.1f [Мышцы %.1f + Оружие %.1f] | Прочность кувшина: %.1f)%n",
                    impactForce, niels.getCurrentMusclePower(), weapon.getLeverageForce(), bottle.getBodyDurability());


            weapon.reduceDurability(30.0);
            bottle.smashBody(impactForce);
            System.out.printf("Кувшин выдержал удар! Но на нем появилась огромная трещина. (Осталось прочности: %.1f)%n",
                    bottle.getBodyDurability());
            System.out.println("Нильс разочарованно опускает руки.");

        } catch (BottleSmashedException e) {
            System.out.println(e.getMessage());
            System.out.println("Вода разлилась, Нильс может напиться.");
        } catch (Exception e) {
            System.out.println("   " + e.getMessage());
            System.out.println("Оружие разлетелось в щепки при ударе! Кувшин цел.");
        }
    }

    public void openMethod(Niels niels, Bottle bottle, World world) {

        // Осмотреться
        niels.printIntroduction();
        niels.lookAround(world, bottle);

        // Выбор рычага
        Lever bestLever = ToolSelector.chooseBestLever(niels, niels.getKnownItems());

        if (bestLever == null) {
            System.out.println("Нильс не нашел подходящего рычага.");
            return;
        }
        System.out.println("\nНильс решил поднять \"" + bestLever.getName() + "\".");

        // Перемещение
        niels.moveTo(bestLever.getPosition());
        niels.moveTo(bottle.getPosition());

        // Забивание
        System.out.println("\nНильс принялся за дело. Он наставил предмет \"" + bestLever.getName() + "\", как долото, между крышкой и горлышком.");

        Hammer currentHammer = null;

        while (bottle.getInsertionTightness() > 0 && !bottle.isSmashed()) {
            try {
                if (currentHammer == null || currentHammer.isBroken()) {
                    if (currentHammer != null) System.out.println("   (Камень рассыпался!)");

                    currentHammer = ToolSelector.findNearestHammer(niels, niels.getKnownItems());

                    if (currentHammer == null) {
                        System.out.println("Нильс огляделся... Камней больше нет! Придется пробовать так.");
                        break;
                    }

                    System.out.println("Нильс идет за камнем: " + currentHammer.getName());
                    niels.moveTo(currentHammer.getPosition());
                    niels.moveTo(bottle.getPosition());
                }

                double damage = currentHammer.getImpactDamage();
                bottle.hitCork(damage);
                currentHammer.reduceDurability(5);
                bestLever.reduceDurability(2);

                System.out.printf("   (Удар: урон %.1f | Тугость: %.1f | Прочность крышки: %.1f | Прочность кувшина: %.1f)%n",
                        damage, bottle.getInsertionTightness(), bottle.getCorkDurability(), bottle.getBodyDurability());

            } catch (Exception e) {
                System.out.println("   " + e.getMessage());
                if (e instanceof BottleSmashedException) return;
            }
        }

        if (bottle.isSmashed()) return;

        if (bottle.isOpen()) {
            System.out.println("От ударов крышка раскрошилась и кувшин открылся!");
            return;
        }

        if (bottle.getInsertionTightness() <= 0) {
            System.out.println("Он бил до тех пор, пока \"" + bestLever.getName() + "\" не вошла почти наполовину.");
        } else {
            System.out.println("Рычаг вошел недостаточно глубоко, но выбора нет.");
        }
        //  Хватание
        GripPosition grip;
        if (random.nextBoolean()) {
            grip = GripPosition.EDGE;
        } else {
            grip = GripPosition.MIDDLE;
        }

        double forceEfficiency;
        double corkDamageMult;

        if (grip == GripPosition.EDGE) {
            forceEfficiency = 1.0;
            corkDamageMult = 1.0;
        } else {
            forceEfficiency = 0.7;
            corkDamageMult = 1.5;
        }

        System.out.println("\nНильс случайно хватается за рычаг " + grip.getDescription() + ".");
        if (grip == GripPosition.MIDDLE) {
            System.out.println("(Рычаг работает хуже, зато крышку перекашивает сильнее)");
        } else {
            System.out.println("(Максимальная сила рычага)");
        }

        System.out.println("Он повис на рычаге всем весом.");

        try {
            double rawForce = niels.getBodyWeight() + bestLever.getLeverageForce();
            double effectiveForce = rawForce * forceEfficiency;

            System.out.printf("   (Сила: %.1f * %.0f%% = %.1f кг | Требуется: %.1f кг)%n",
                    rawForce, forceEfficiency * 100, effectiveForce, bottle.getPullForceRequired());

            bestLever.reduceDurability(rawForce * 0.1);
            bottle.pullCork(effectiveForce, corkDamageMult);

            if (bottle.isOpen()) {
                if (bottle.getCorkDurability() <= 0) {
                    System.out.println("Крышка сломалась! Кувшин открыт.");
                } else {
                    System.out.println("Пробка вылетела!");
                }
                return;
            } else {
                System.out.printf("Пробка не поддается... (Прочность кувшина: %.1f, Крышки: %.1f)%n",
                        bottle.getBodyDurability(), bottle.getCorkDurability());
            }
        } catch (Exception e) {
            System.out.println("Неудача: " + e.getMessage());
            if (bottle.isSmashed() || bestLever.isBroken()) return;
        }

        // Напрягается
        System.out.println("\nОн весь натужился, напряг мускулы и даже поджал ноги к животу.");

        try {
            double rawForce = niels.getBodyWeight() + niels.getCurrentMusclePower() + bestLever.getLeverageForce();
            double effectiveForce = rawForce * forceEfficiency;

            System.out.printf("   (Максимальная сила: %.1f * %.0f%% = %.1f кг)%n",
                    rawForce, forceEfficiency * 100, effectiveForce);

            bestLever.reduceDurability(rawForce * 0.2);
            bottle.pullCork(effectiveForce, corkDamageMult);

            if (bottle.isOpen()) {
                if (bottle.getCorkDurability() <= 0) {
                    System.out.println("Крышка рассыпалась!");
                } else {
                    System.out.println("Пробка вылетела!");
                }
            } else {
                System.out.println("Ничего не вышло. Нильс в изнеможении сполз на землю.");
            }

        } catch (Exception e) {
            System.out.println("Финал: " + e.getMessage());
        }
    }
}