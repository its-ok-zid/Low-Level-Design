package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.metal;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.AudioDevice;

public class MetalAudioDevice implements AudioDevice {
    @Override
    public void initAudioStream(int sampleRate, int channels) {
        System.out.println("MetalAudioDevice: Initializing audio stream with sample rate " + sampleRate + " Hz and " + channels + " channels.");
    }

    @Override
    public void playSpatialSound(String soundClip, float x, float y, float z) {
        System.out.println("MetalAudioDevice: Playing spatial sound '" + soundClip + "' at position (" + x + ", " + y + ", " + z + ").");
    }
}
