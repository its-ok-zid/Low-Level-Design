package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.microsoft;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;

public class SmoothStreamingTransport implements NetworkTransport {
    @Override
    public void openStream(String cdnEndpointUrl) {
        System.out.println("[IIS Smooth Streaming] Opened Manifest endpoint: " + cdnEndpointUrl);
    }

    @Override
    public byte[] pullChunk(int chunkSize) {
        System.out.println("[IIS Smooth Streaming] Downloaded ISMV fragment (" + chunkSize + " bytes)");
        return new byte[chunkSize];
    }
}