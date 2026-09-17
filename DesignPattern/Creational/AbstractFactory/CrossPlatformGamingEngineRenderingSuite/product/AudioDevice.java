package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product;

public interface AudioDevice {
    void initAudioStream(int sampleRate, int channels);
    void playSpatialSound(String soundClip, float x, float y, float z);
}
