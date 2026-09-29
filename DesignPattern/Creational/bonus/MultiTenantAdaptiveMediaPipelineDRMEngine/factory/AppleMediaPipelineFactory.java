package DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.factory;

import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.apple.FairPlayDrmSessionManager;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.apple.HlsTransport;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.apple.VideoToolboxDecoder;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.DrmSessionManager;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;

public class AppleMediaPipelineFactory implements MediaPipelineFactory {
    @Override
    public DrmSessionManager createDrmSessionManager() {
        return new FairPlayDrmSessionManager();
    }

    @Override
    public VideoDecoder createVideoDecoder() {
        return new VideoToolboxDecoder();
    }

    @Override
    public NetworkTransport createNetworkTransport() {
        return new HlsTransport();
    }
}
