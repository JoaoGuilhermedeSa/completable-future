package org.acme.future_callable.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class Chest {
	
    private static final double FAILURE_RATE = 0.3; // 30% de chance de falha
    private static final Map<Rarity, Double> LOOT_PROBABILITIES = Map.of(
        Rarity.LEGENDARY, 0.05,
        Rarity.EPIC, 0.15,
        Rarity.RARE, 0.3,
        Rarity.COMMON, 0.5
    );
    private static final List<Loot> LOOT_TABLE = List.of(
        new Loot("Espada Enferrujada", Rarity.COMMON),
        new Loot("Armadura de Couro", Rarity.COMMON),
        new Loot("Anel Mágico", Rarity.RARE),
        new Loot("Elmo de Ferro", Rarity.RARE),
        new Loot("Espada Flamejante", Rarity.EPIC),
        new Loot("Escudo Dourado", Rarity.EPIC),
        new Loot("Martelo do Titã", Rarity.LEGENDARY)
    );

    public CompletableFuture<Loot> open() {
        return CompletableFuture.supplyAsync(() -> {
            if (ThreadLocalRandom.current().nextDouble() < FAILURE_RATE) {
                return null; // Falha ao abrir o baú
            }
            return getRandomLoot();
        });
    }

    private Loot getRandomLoot() {
        double roll = ThreadLocalRandom.current().nextDouble();
        double cumulativeProbability = 0.0;
        for (Rarity rarity : Rarity.values()) {
            cumulativeProbability += LOOT_PROBABILITIES.get(rarity);
            if (roll < cumulativeProbability) {
                return LOOT_TABLE.stream()
                        .filter(loot -> loot.getRarity() == rarity)
                        .findAny()
                        .orElse(null);
            }
        }
        return null;
    }
}
