package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.directx;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.AudioDevice;

public class DirectXAudioDevice implements AudioDevice {
    @Override
    public void initAudioStream(int sampleRate, int channels) {
        System.out.println("DirectXAudioDevice: Initializing audio stream with sample rate " + sampleRate + " Hz and " + channels + " channels.");
    }

    @Override
    public void playSpatialSound(String soundClip, float x, float y, float z) {
        System.out.println("DirectXAudioDevice: Playing spatial sound '" + soundClip + "' at position (" + x + ", " + y + ", " + z + ").");
    }
}
