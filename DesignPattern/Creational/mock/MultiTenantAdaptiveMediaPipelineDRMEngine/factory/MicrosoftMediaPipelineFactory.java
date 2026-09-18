package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.factory;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.microsoft.MediaFoundationDecoder;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.microsoft.PlayReadyDrmSessionManager;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.microsoft.SmoothStreamingTransport;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.DrmSessionManager;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;

public class MicrosoftMediaPipelineFactory implements MediaPipelineFactory{
    @Override
    public DrmSessionManager createDrmSessionManager() {
        return new PlayReadyDrmSessionManager();
    }

    @Override
    public VideoDecoder createVideoDecoder() {
        return new MediaFoundationDecoder();
    }

    @Override
    public NetworkTransport createNetworkTransport() {
        return new SmoothStreamingTransport();
    }
}
