package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.factory;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.metal.MetalAudioDevice;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.metal.MetalPhysicsEngine;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.metal.MetalPipelineCompiler;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.AudioDevice;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PhysicsEngine;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PipelineCompiler;

public class MetalBackendFactory implements GameEngineBackendFactory {
    @Override
    public PhysicsEngine createPhysicsEngine() {
        return new MetalPhysicsEngine();
    }

    @Override
    public AudioDevice createAudioDevice() {
        return new MetalAudioDevice();
    }

    @Override
    public PipelineCompiler createPipelineCompiler() {
        return new MetalPipelineCompiler();
    }
}
