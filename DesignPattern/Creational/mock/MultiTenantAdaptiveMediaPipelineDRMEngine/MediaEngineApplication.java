package DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine;

import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.factory.AppleMediaPipelineFactory;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.factory.GoogleMediaPipelineFactory;
import DesignPattern.Creational.mock.MultiTenantAdaptiveMediaPipelineDRMEngine.factory.MicrosoftMediaPipelineFactory;

public class MediaEngineApplication {
    public static void main(String[] args) {
        System.out.println("=== Booting Apple tvOS Playback Engine ===");
        StreamingPlaybackEngine appleEngine = new StreamingPlaybackEngine(new AppleMediaPipelineFactory());
        appleEngine.preparePlayback("stranger-things-s05e01", "[https://fps.apple.com/license](https://fps.apple.com/license)", "[https://cdn.apple.com/master.m3u8](https://cdn.apple.com/master.m3u8)");
        appleEngine.playSegment(2048);

        System.out.println("\n=== Booting Google Android TV Playback Engine ===");
        StreamingPlaybackEngine googleEngine = new StreamingPlaybackEngine(new GoogleMediaPipelineFactory());
        googleEngine.preparePlayback("stranger-things-s05e01", "[https://widevine.google.com/license](https://widevine.google.com/license)", "[https://cdn.google.com/stream.mpd](https://cdn.google.com/stream.mpd)");
        googleEngine.playSegment(2048);

        System.out.println("\n=== Booting Microsoft Xbox Series X Playback Engine ===");
        StreamingPlaybackEngine microsoftEngine = new StreamingPlaybackEngine(new MicrosoftMediaPipelineFactory());
        microsoftEngine.preparePlayback("stranger-things-s05e01", "[https://playready.microsoft.com/rights](https://playready.microsoft.com/rights)", "[https://cdn.microsoft.com/stream.ism](https://cdn.microsoft.com/stream.ism)");
        microsoftEngine.playSegment(2048);
    }
}