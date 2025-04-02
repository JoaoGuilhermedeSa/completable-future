package org.acme.future_callable.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Inventory {
	private final List<Loot> items = new ArrayList<>();
	private final String owner;

	public Inventory(String owner) {
		this.owner = owner;
	}

	public List<Loot> getItems() {
		return items;
	}

	public CompletableFuture<Void> addItem(Loot loot) {
		return CompletableFuture.runAsync(() -> {
			items.add(loot);
			System.out.println(owner + " adicionou ao inventário: " + loot);
		});
	}

	public CompletableFuture<Void> removeItem(Loot loot) {
		return CompletableFuture.runAsync(() -> {
			items.remove(loot);
			System.out.println(owner + " removeu do inventário: " + loot);
		});
	}

	public CompletableFuture<Void> showInventory() {
		return CompletableFuture.runAsync(() -> {
			if (items.isEmpty()) {
				System.out.println("Inventário de " + owner + " está vazio.");
			} else {
				System.out.println("Inventário de " + owner + ": " + items);
			}
		});
	}

	public String toString() {

		if (items.isEmpty()) {
			return "Inventário de " + owner + " está vazio.";
		} else {
			return "Inventário de " + owner + ": " + items;
		}

	}
}
