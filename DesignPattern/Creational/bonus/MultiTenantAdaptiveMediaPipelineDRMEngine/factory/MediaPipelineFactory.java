package DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.factory;

import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.DrmSessionManager;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;

public interface MediaPipelineFactory {
    DrmSessionManager createDrmSessionManager();

    VideoDecoder createVideoDecoder();

    NetworkTransport createNetworkTransport();
}