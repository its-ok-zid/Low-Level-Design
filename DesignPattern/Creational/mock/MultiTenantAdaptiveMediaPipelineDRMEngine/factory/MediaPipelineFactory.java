package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.factory;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.DrmSessionManager;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;

public interface MediaPipelineFactory {
    DrmSessionManager createDrmSessionManager();

    VideoDecoder createVideoDecoder();

    NetworkTransport createNetworkTransport();
}