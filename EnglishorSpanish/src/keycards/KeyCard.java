package keycards;

import permissions.Permission;

public abstract class KeyCard {
    protected String cardId;
    protected String owner;

    public KeyCard(String cardId, String owner) {
        this.cardId = cardId;
        this.owner = owner;
    }

    public void activate() {
        System.out.println("KeyCard " + cardId + " for " + owner + " is activated.");
    }

    public void deactivate() {
        System.out.println("KeyCard " + cardId + " for " + owner + " is deactivated.");
    }

    public abstract void use();
}
