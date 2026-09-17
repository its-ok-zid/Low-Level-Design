package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.vulkan;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.AudioDevice;

public class VulkanAudioDevice implements AudioDevice {
    @Override
    public void initAudioStream(int sampleRate, int channels) {
        System.out.println("Initializing Vulkan Audio Stream with sample rate: " + sampleRate + " Hz and channels: " + channels);
    }

    @Override
    public void playSpatialSound(String soundClip, float x, float y, float z) {
        System.out.println("Playing spatial sound '" + soundClip + "' at position (" + x + ", " + y + ", " + z + ") using Vulkan Audio Device.");
    }
}
