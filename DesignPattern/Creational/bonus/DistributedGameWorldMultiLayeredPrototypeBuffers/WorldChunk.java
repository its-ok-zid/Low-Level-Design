package DesignPattern.Creational.bonus.DistributedGameWorldMultiLayeredPrototypeBuffers;

import java.util.HashMap;
import java.util.Map;

public class WorldChunk implements Prototype<WorldChunk> {
    private final String chunkId;
    private final String biomeType;
    private Map<String, GameEntity> entities;
    private Map<String, Float> environmentalParameters;

    public WorldChunk(String chunkId, String biomeType,
                      Map<String, GameEntity> entities,
                      Map<String, Float> environmentalParameters) {
        this.chunkId = chunkId;
        this.biomeType = biomeType;
        this.entities = (entities != null) ? new HashMap<>(entities) : new HashMap<>();
        this.environmentalParameters = (environmentalParameters != null) ? new HashMap<>(environmentalParameters) : new HashMap<>();
    }

    public WorldChunk(WorldChunk source, boolean deepCopy) {
        this.chunkId = source.chunkId;
        this.biomeType = source.biomeType;
        if (deepCopy) {
            this.entities = new HashMap<>();
            if (source.entities != null) {
                for (Map.Entry<String, GameEntity> entry : source.entities.entrySet()) {
                    this.entities.put(entry.getKey(), entry.getValue().deepCopy());
                }
            }
            this.environmentalParameters = (source.environmentalParameters != null)
                    ? new HashMap<>(source.environmentalParameters)
                    : new HashMap<>();
        } else {
            this.entities = source.entities;
            this.environmentalParameters = source.environmentalParameters;
        }
    }

    public void addEntity(GameEntity entity) {
        entities.put(entity.getEntityId(), entity);
    }

    public String getChunkId() { return chunkId; }
    public String getBiomeType() { return biomeType; }
    public Map<String, GameEntity> getEntities() { return entities; }
    public Map<String, Float> getEnvironmentalParameters() { return environmentalParameters; }

    @Override
    public WorldChunk shallowCopy() {
        return new WorldChunk(this, false);
    }

    @Override
    public WorldChunk deepCopy() {
        return new WorldChunk(this, true);
    }
}