package org.acme.future_callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.acme.future_callable.model.Chest;
import org.acme.future_callable.model.Loot;
import org.acme.future_callable.model.Player;

public class App {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Player hero = new Player("Herói");
		Player villain = new Player("Vilão");

		List<CompletableFuture<Loot>> lootAttempts = new ArrayList();
		lootAttempts.addAll(lootChests(hero));
		lootAttempts.addAll(lootChests(villain));

		CompletableFuture<Void> allLooting = CompletableFuture
				.allOf(lootAttempts.toArray(new CompletableFuture[lootAttempts.size()]));

		allLooting.thenCompose(v -> villain.attemptPickpocket(hero));
				
		allLooting.join();
		
		System.out.println("Checkando inventário do herói: " + hero.getInventory().toString());
		System.out.println("Checkando inventário do vilão: " + villain.getInventory().toString());

	}

	private static List<CompletableFuture<Loot>> lootChests(Player player) {
		List<CompletableFuture<Loot>> lootAttempts = new ArrayList<>();

		double maxAttempts = Math.random() * 3;

		for (double i = 0; i < maxAttempts; i++) {
			Chest chest = new Chest();
			lootAttempts.add(player.openChest(chest));
		}
		return lootAttempts;
	}
}