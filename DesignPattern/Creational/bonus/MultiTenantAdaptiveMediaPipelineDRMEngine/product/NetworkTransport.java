package DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product;

public interface NetworkTransport {
    void openStream(String cdnEndpointUrl);
    byte[] pullChunk(int chunkSize);
}
