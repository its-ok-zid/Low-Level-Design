package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.factory;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.apple.FairPlayDrmSessionManager;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.apple.HlsTransport;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.apple.VideoToolboxDecoder;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.DrmSessionManager;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;

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
