package org.acme.future_callable.model;

public class Loot {
    private final String name;
    private final Rarity rarity;

    public Loot(String name, Rarity rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    @Override
    public String toString() {
        return name + " [" + rarity + "]";
    }
    
    public Rarity getRarity() {
    	return rarity;
    }
}