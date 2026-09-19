package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.apple;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;

public class HlsTransport implements NetworkTransport {
    @Override
    public void openStream(String cdnEndpointUrl) {
        System.out.println("[Apple AVFoundation] Opened HLS Master Playlist: " + cdnEndpointUrl);
    }

    @Override
    public byte[] pullChunk(int chunkSize) {
        System.out.println("[Apple AVFoundation] Pulled TS/fMP4 segment of size: " + chunkSize + " bytes");
        return new byte[chunkSize];
    }
}
