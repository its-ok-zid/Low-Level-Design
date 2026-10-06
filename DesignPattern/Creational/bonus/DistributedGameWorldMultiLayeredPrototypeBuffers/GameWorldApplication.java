package DesignPattern.Creational.bonus.DistributedGameWorldMultiLayeredPrototypeBuffers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameWorldApplication {
    public static void main(String[] args) {
        System.out.println("=== 1. Constructing Golden Master Dungeon Chunk ===");

        // Flyweight: 50MB simulated terrain polygon buffer
        MeshGeometry bossMesh = new MeshGeometry("mesh-boss-dragon-lod0", 150000, new byte[]{0x1F, 0x2A, 0x4B});

        Transform bossTransform = Transform.builder()
                .xCoordinate(100.0).yCoordinate(0.0).zCoordinate(500.0)
                .pitch(0.0).yaw(90.0).roll(0.0)
                .build();

        Item legendarySword = new Item("item-sword-01", 100, Map.of("attackPower", 250.0));

        GameEntity bossEntity = GameEntity.builder()
                .entityId("boss-01")
                .entityType("ANCIENT_DRAGON")
                .meshGeometry(bossMesh)
                .transform(bossTransform)
                .health(50000.0)
                .inventory(List.of(legendarySword))
                .activeEffects(Set.of("FIRE_AURA"))
                .build();

        Map<String, GameEntity> chunkEntities = new HashMap<>();
        chunkEntities.put(bossEntity.getEntityId(), bossEntity);

        Map<String, Float> envParams = new HashMap<>();
        envParams.put("GRAVITY", 9.8f);
        envParams.put("LAVA_HEAT", 85.0f);

        WorldChunk dungeonMaster = new WorldChunk("chunk-dungeon-101", "LAVA_CAVERN", chunkEntities, envParams);

        // Register in cache
        WorldChunkRegistry registry = new WorldChunkRegistry();
        registry.registerChunk("LAVA_DUNGEON_CHAMBER", dungeonMaster);

        System.out.println("=== 2. Spawning Isolated Dungeon Instances for Player A and Player B ===");
        WorldChunk instancePlayerA = registry.getChunk("LAVA_DUNGEON_CHAMBER");
        WorldChunk instancePlayerB = registry.getChunk("LAVA_DUNGEON_CHAMBER");

        GameEntity dragonA = instancePlayerA.getEntities().get("boss-01");
        GameEntity dragonB = instancePlayerB.getEntities().get("boss-01");

        // Player A damages the boss, breaks sword durability, and changes coordinates
        dragonA.setHealth(32000.0);
        dragonA.getTransform().setXCoordinate(250.0);
        dragonA.getInventory().get(0).setDurability(40);
        dragonA.addEffect("FROZEN");

        System.out.println("=== 3. Verifying Memory Isolation vs Shared Flyweights ===");

        // Flyweight Check: Geometry buffer MUST be the exact same instance in memory (RAM conservation)
        boolean isGeometryShared = (dragonA.getMeshGeometry() == dragonB.getMeshGeometry());
        System.out.println("Is Large MeshGeometry shared (Flyweight optimization)? -> " + isGeometryShared);

        // Mutable State Isolation Checks:
        System.out.println("Dragon A Health: " + dragonA.getHealth() + " | Dragon B Health: " + dragonB.getHealth());
        System.out.println("Dragon A X-Coord: " + dragonA.getTransform().getXCoordinate() + " | Dragon B X-Coord: " + dragonB.getTransform().getXCoordinate());
        System.out.println("Dragon A Sword Durability: " + dragonA.getInventory().get(0).getDurability() + " | Dragon B Sword Durability: " + dragonB.getInventory().get(0).getDurability());
        System.out.println("Dragon A Effects: " + dragonA.getActiveEffects() + " | Dragon B Effects: " + dragonB.getActiveEffects());

        boolean isTransformIsolated = (dragonA.getTransform() != dragonB.getTransform());
        boolean isInventoryIsolated = (dragonA.getInventory().get(0) != dragonB.getInventory().get(0));
        System.out.println("Are Transforms isolated in memory? -> " + isTransformIsolated);
        System.out.println("Are Item Inventories isolated in memory? -> " + isInventoryIsolated);
    }
}