package logic;

import interfaces.Hammer;
import interfaces.Lever;
import model.Niels;
import model.Thing;
import java.util.List;

public class ToolSelector {

    public static Lever chooseBestLever(Niels niels, List<Thing> items) {
        Lever bestLever = null;
        double maxScore = -Double.MAX_VALUE;

        for (Thing item : items) {
            if (item instanceof Lever) {
                Lever currentLever = (Lever) item;
                double currentScore = calculateLeverScore(niels, currentLever);

                if (bestLever == null || currentScore > maxScore) {
                    maxScore = currentScore;
                    bestLever = currentLever;
                }
            }
        }
        return bestLever;
    }

    public static Hammer findNearestHammer(Niels niels, List<Thing> items) {
        Hammer bestHammer = null;
        double minDist = Double.MAX_VALUE;

        for (Thing item : items) {
            if (item instanceof Hammer && !item.isBroken()) {
                double dist = niels.getPosition().distanceTo(item.getPosition());
                if (dist < minDist) {
                    minDist = dist;
                    bestHammer = (Hammer) item;
                }
            }
        }
        return bestHammer;
    }

    private static double calculateLeverScore(Niels niels, Lever lever) {
        double power = lever.getLeverageForce();
        double distance = niels.getPosition().distanceTo(lever.getPosition());
        return power - (distance * 1.5);
    }
}