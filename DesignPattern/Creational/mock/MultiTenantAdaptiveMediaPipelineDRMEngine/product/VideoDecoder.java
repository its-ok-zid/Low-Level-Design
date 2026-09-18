package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product;

public interface VideoDecoder {
    void initializeDecoder(int width, int height, String colorSpace);

    void decodeFrame(byte[] packetData);
}
