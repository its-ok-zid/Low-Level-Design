package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.google;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;

public class MediaCodecDecoder implements VideoDecoder {
    @Override
    public void initializeDecoder(int width, int height, String colorSpace) {
        System.out.println("[Android MediaCodec] Configured AMediaCodec format (" + width + "x" + height + " | " + colorSpace + ")");
    }

    @Override
    public void decodeFrame(byte[] packetData) {
        System.out.println("[Android MediaCodec] Decoded frame buffer (" + packetData.length + " bytes) to Surface.");
    }
}