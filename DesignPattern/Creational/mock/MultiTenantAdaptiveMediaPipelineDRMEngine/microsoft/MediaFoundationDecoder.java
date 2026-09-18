package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.microsoft;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;

public class MediaFoundationDecoder implements VideoDecoder {
    @Override
    public void initializeDecoder(int width, int height, String colorSpace) {
        System.out.println("[Microsoft Media Foundation] Initialized IMFTransform decoder (" + width + "x" + height + " | " + colorSpace + ")");
    }

    @Override
    public void decodeFrame(byte[] packetData) {
        System.out.println("[Microsoft Media Foundation] Decoded video sample (" + packetData.length + " bytes) to Direct3D Surface.");
    }
}