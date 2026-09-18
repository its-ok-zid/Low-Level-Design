package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.apple;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;

public class VideoToolboxDecoder implements VideoDecoder {
    @Override
    public void initializeDecoder(int width, int height, String colorSpace) {
        System.out.println("[Apple VideoToolbox] Initialized hardware session (" + width + "x" + height + " | " + colorSpace + ")");
    }

    @Override
    public void decodeFrame(byte[] packetData) {
        System.out.println("[Apple VideoToolbox] Decoded frame payload (" + packetData.length + " bytes) to CVPixelBuffer.");
    }
}