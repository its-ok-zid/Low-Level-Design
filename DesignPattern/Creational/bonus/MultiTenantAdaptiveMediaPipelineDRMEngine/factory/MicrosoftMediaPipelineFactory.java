package DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.factory;

import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.microsoft.MediaFoundationDecoder;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.microsoft.PlayReadyDrmSessionManager;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.microsoft.SmoothStreamingTransport;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.DrmSessionManager;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;

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
