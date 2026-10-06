package DesignPattern.Creational.bonus.DistributedGameWorldMultiLayeredPrototypeBuffers;

import java.util.List;
import java.util.Set;

public class GameEntityBuilder {
    String entityId;
    String entityType;
    MeshGeometry meshGeometry;
    Transform transform;
    double health;
    List<Item> inventory;
    Set<String> activeEffects;

    public GameEntityBuilder entityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public GameEntityBuilder entityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public GameEntityBuilder meshGeometry(MeshGeometry meshGeometry) {
        this.meshGeometry = meshGeometry;
        return this;
    }

    public GameEntityBuilder transform(Transform transform) {
        this.transform = transform;
        return this;
    }

    public GameEntityBuilder health(double health) {
        this.health = health;
        return this;
    }

    public GameEntityBuilder inventory(List<Item> inventory) {
        this.inventory = inventory;
        return this;
    }

    public GameEntityBuilder activeEffects(Set<String> activeEffects) {
        this.activeEffects = activeEffects;
        return this;
    }

    public GameEntity build() {
        if (entityId == null || entityType == null || meshGeometry == null || transform == null || inventory == null || activeEffects == null) {
            throw new IllegalStateException("All fields must be set before building a GameEntity");
        }
        return new GameEntity(this);
    }
}
