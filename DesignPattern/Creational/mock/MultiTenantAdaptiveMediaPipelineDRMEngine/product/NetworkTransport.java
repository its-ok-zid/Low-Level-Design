package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product;

public interface NetworkTransport {
    void openStream(String cdnEndpointUrl);
    byte[] pullChunk(int chunkSize);
}
