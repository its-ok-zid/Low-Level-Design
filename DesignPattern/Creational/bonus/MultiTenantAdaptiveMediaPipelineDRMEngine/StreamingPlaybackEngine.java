package DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine;

import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.factory.MediaPipelineFactory;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.DrmSessionManager;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.NetworkTransport;
import DesignPattern.Creational.bonus.MultiTenantAdaptiveMediaPipelineDRMEngine.product.VideoDecoder;
import java.util.Objects;

public class StreamingPlaybackEngine {
    private final DrmSessionManager drmSessionManager;
    private final VideoDecoder videoDecoder;
    private final NetworkTransport networkTransport;

    public StreamingPlaybackEngine(MediaPipelineFactory factory) {
        Objects.requireNonNull(factory, "Factory cannot be null");
        this.drmSessionManager = factory.createDrmSessionManager();
        this.networkTransport = factory.createNetworkTransport();
        this.videoDecoder = factory.createVideoDecoder();
    }

    public void preparePlayback(String contentId, String keyServer, String cdnUrl) {
        System.out.println("--- Preparing Media Pipeline ---");
        networkTransport.openStream(cdnUrl);
        drmSessionManager.acquireLicense(contentId, keyServer);
        videoDecoder.initializeDecoder(3840, 2160, "HDR10");
    }

    public void playSegment(int segmentSize) {
        byte[] encryptedChunk = networkTransport.pullChunk(segmentSize);
        byte[] decryptedChunk = drmSessionManager.decryptPayload(encryptedChunk);
        videoDecoder.decodeFrame(decryptedChunk);
    }
}