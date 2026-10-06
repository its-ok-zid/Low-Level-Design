package DesignPattern.Creational.bonus.DistributedGameWorldMultiLayeredPrototypeBuffers;

import java.util.HashMap;
import java.util.Map;

public class WorldChunkRegistry {
    private final Map<String, WorldChunk> chunkRegistry = new HashMap<>();

    public void registerChunk(String archetypeKey, WorldChunk chunk) {
        chunkRegistry.put(archetypeKey, chunk.deepCopy());
    }

    public WorldChunk getChunk(String archetypeKey) {
        WorldChunk chunk = chunkRegistry.get(archetypeKey);
        if (chunk != null) {
            return chunk.deepCopy();
        }
        throw new IllegalArgumentException("No chunk registered with the key: " + archetypeKey);
    }

    public WorldChunk getChunk(String archetypeKey, boolean deep) {
        WorldChunk chunk = chunkRegistry.get(archetypeKey);
        if (chunk != null) {
            return deep ? chunk.deepCopy() : chunk;
        }
        throw new IllegalArgumentException("No chunk registered with the key: " + archetypeKey);
    }
}
