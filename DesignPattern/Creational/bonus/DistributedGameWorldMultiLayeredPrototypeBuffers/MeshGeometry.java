package DesignPattern.Creational.bonus.DistributedGameWorldMultiLayeredPrototypeBuffers;

public class MeshGeometry {
    private final String meshId;
    private final int vertexCount;
    private final byte[] polygonData;

    public MeshGeometry(String meshId, int vertexCount, byte[] polygonData) {
        this.meshId = meshId;
        this.vertexCount = vertexCount;
        this.polygonData = polygonData;
    }

    public String getMeshId() {
        return meshId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public byte[] getPolygonData() {
        return polygonData;
    }
}
