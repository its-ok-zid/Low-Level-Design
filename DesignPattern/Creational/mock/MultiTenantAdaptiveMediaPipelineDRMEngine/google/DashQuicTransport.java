package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.google;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;

public class DashQuicTransport implements NetworkTransport {
    @Override
    public void openStream(String cdnEndpointUrl) {
        System.out.println("[MPEG-DASH / QUIC] Connected to MPD manifest via HTTP/3: " + cdnEndpointUrl);
    }

    @Override
    public byte[] pullChunk(int chunkSize) {
        System.out.println("[MPEG-DASH / QUIC] Streamed QUIC media chunk (" + chunkSize + " bytes)");
        return new byte[chunkSize];
    }
}