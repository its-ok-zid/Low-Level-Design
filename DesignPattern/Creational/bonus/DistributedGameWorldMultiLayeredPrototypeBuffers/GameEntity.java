package DesignPattern.Creational.bonus.DistributedGameWorldMultiLayeredPrototypeBuffers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameEntity implements Prototype<GameEntity> {
    private String entityId;
    private final String entityType;
    private MeshGeometry meshGeometry; // Flyweight: Shared across all clones
    private Transform transform;       // Mutable Physics: Deeply copied
    private double health;
    private List<Item> inventory;      // Mutable Inventory: Deeply copied
    private Set<String> activeEffects; // Mutable Effects: Deeply copied

    public GameEntity(GameEntityBuilder builder) {
        this.entityId = builder.entityId;
        this.entityType = builder.entityType;
        this.meshGeometry = builder.meshGeometry;
        this.transform = builder.transform;
        this.health = builder.health;
        this.inventory = (builder.inventory != null) ? new ArrayList<>(builder.inventory) : new ArrayList<>();
        this.activeEffects = (builder.activeEffects != null) ? new HashSet<>(builder.activeEffects) : new HashSet<>();
    }

    public GameEntity(GameEntity source, boolean deepCopy) {
        this.entityId = source.entityId;
        this.entityType = source.entityType;
        this.meshGeometry = source.meshGeometry; // Shared reference
        this.health = source.health;

        if (deepCopy) {
            this.transform = (source.transform != null) ? source.transform.deepCopy() : null;
            this.inventory = (source.inventory != null)
                    ? source.inventory.stream().map(Item::deepCopy).collect(Collectors.toCollection(ArrayList::new))
                    : new ArrayList<>();
            this.activeEffects = (source.activeEffects != null)
                    ? new HashSet<>(source.activeEffects)
                    : new HashSet<>();
        } else {
            this.transform = source.transform;
            this.inventory = source.inventory;
            this.activeEffects = source.activeEffects;
        }
    }

    public static GameEntityBuilder builder() {
        return new GameEntityBuilder();
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public void addEffect(String effect) {
        this.activeEffects.add(effect);
    }

    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }
    public String getEntityType() { return entityType; }
    public MeshGeometry getMeshGeometry() { return meshGeometry; }
    public Transform getTransform() { return transform; }
    public void setTransform(Transform transform) { this.transform = transform; }
    public double getHealth() { return health; }
    public void setHealth(double health) { this.health = health; }
    public List<Item> getInventory() { return inventory; }
    public Set<String> getActiveEffects() { return activeEffects; }

    @Override
    public GameEntity shallowCopy() {
        return new GameEntity(this, false);
    }

    @Override
    public GameEntity deepCopy() {
        return new GameEntity(this, true);
    }
}