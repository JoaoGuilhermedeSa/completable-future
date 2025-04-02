package org.acme.future_callable.model;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
	private String name;
	private Inventory inventory;

	public Player(String name) {
		this.name = name;
		this.inventory = new Inventory(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}


    public CompletableFuture<Loot> openChest(Chest chest) {
        return chest.open().thenCompose(this::processLoot);
    }

    private CompletableFuture<Loot> processLoot(Loot loot) {
        if (loot != null) {
            System.out.println(name + " encontrou: " + loot);
            return inventory.addItem(loot)
                .thenCompose(v -> inventory.showInventory())
                .thenApply(v -> loot);
        } else {
            System.out.println(name + " falhou ao abrir o baú...");
            return CompletableFuture.completedFuture(null);
        }
    }
    
    public CompletableFuture<Void> attemptPickpocket(Player target) {
        return CompletableFuture.runAsync(() -> {
            List<Loot> targetItems = target.getInventory().getItems();
            if (!targetItems.isEmpty()) {
                Loot stolenItem = targetItems.get(ThreadLocalRandom.current().nextInt(targetItems.size()));
                System.out.println(name + " roubou " + stolenItem + " de " + target.getName() + "!");
                target.getInventory().removeItem(stolenItem)
                    .thenCompose(v -> inventory.addItem(stolenItem))
                    .thenCompose(v -> inventory.showInventory())
                    .thenCompose(v -> target.getInventory().showInventory()).join();
            } else {
                System.out.println(name + " tentou roubar, mas " + target.getName() + " não tinha nada.");
            }
        });
    }

}