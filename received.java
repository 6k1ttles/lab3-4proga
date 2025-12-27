package model;

import interfaces.Locatable;
import model.Point;
import exceptions.ItemBrokenException;
import exceptions.BottleSmashedException;


public class Bottle implements Locatable {
    
    private final Body body;
    private final Cork cork;
    private boolean isDestroyed = false;
    private Point position;

    public Bottle(int initialBodyDurability, int initialCorkDurability, int tightness, int requiredForceToOpen) {
        this.body = new Body(initialBodyDurability);
        this.cork = new Cork(initialCorkDurability, tightness, requiredForceToOpen);
    }

    @Override
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    private void checkIntegrity() throws ItemBrokenException {
        if (isDestroyed || !body.isIntact() || !cork.isIntact()) {
            throw new ItemBrokenException("Bottle");
        }
    }

    public void hitCork(int force) throws ItemBrokenException {
        try {
            checkIntegrity();
            cork.hit(force);
        } catch (ItemBrokenException e) {
            destroy();
            throw e;
        }
    }

    public boolean pullCork(int appliedForce) throws ItemBrokenException {
        try {
            checkIntegrity();
            return cork.pull(appliedForce);
        } catch (ItemBrokenException | BottleSmashedException e) {
            destroy();
            throw e;
        }
    }

    public void smashBody(int damage) throws ItemBrokenException {
        try {
            checkIntegrity();
            body.takeDamage(damage);
        } catch (ItemBrokenException e) {
            destroy();
            throw e;
        }
    }

    private void destroy() {
        isDestroyed = true;
    }


    static class Body {
        private int durability;
        
        Body(int initialDurability) {
            this.durability = initialDurability;
        }
        
        void takeDamage(int damage) throws ItemBrokenException {
            durability -= damage;
            if (durability <= 0) {
                throw new ItemBrokenException("Bottle Body");
            }
        }
        
        boolean isIntact() {
            return durability > 0;
        }
    }

    // Внутренний класс представляющий пробку
    static class Cork {
        private int durability;
        private int tightness;
        private final int requiredForceToOpen;
        
        Cork(int initialDurability, int initialTightness, int requiredForceToOpen) {
            this.durability = initialDurability;
            this.tightness = initialTightness;
            this.requiredForceToOpen = requiredForceToOpen;
        }
        
        void hit(int force) throws ItemBrokenException {
            tightness -= force;
            durability -= force / 2;
            
            if (durability <= 0) {
                throw new ItemBrokenException("Bottle Cork");
            }
        }
        
        boolean pull(int appliedForce) throws ItemBrokenException, BottleSmashedException {
            if (appliedForce >= requiredForceToOpen && tightness <= 0) {
                return true;
            } else if (appliedForce > requiredForceToOpen * 2) {
                throw new BottleSmashedException();
            }
            return false;
        }
        
        boolean isIntact() {
            return durability > 0;
        }
    }
}
