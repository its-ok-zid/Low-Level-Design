package DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.factory;

import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.google.DashQuicTransport;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.google.MediaCodecDecoder;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.google.WidevineDrmSessionManager;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.DrmSessionManager;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;

public class GoogleMediaPipelineFactory  implements MediaPipelineFactory{
    @Override
    public DrmSessionManager createDrmSessionManager() {
        return new WidevineDrmSessionManager();
    }

    @Override
    public VideoDecoder createVideoDecoder() {
        return new MediaCodecDecoder();
    }

    @Override
    public NetworkTransport createNetworkTransport() {
        return new DashQuicTransport();
    }
}
