package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.factory;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.google.DashQuicTransport;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.google.MediaCodecDecoder;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.google.WidevineDrmSessionManager;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.DrmSessionManager;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;

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
