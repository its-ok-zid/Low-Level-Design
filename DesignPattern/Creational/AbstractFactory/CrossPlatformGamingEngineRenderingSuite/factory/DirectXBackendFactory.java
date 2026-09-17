package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.factory;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.directx.DirectXAudioDevice;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.directx.DirectXPhysicsEngine;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.directx.DirectXPipelineCompiler;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.AudioDevice;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PhysicsEngine;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PipelineCompiler;

public class DirectXBackendFactory implements GameEngineBackendFactory {
    @Override
    public PhysicsEngine createPhysicsEngine() {
        return new DirectXPhysicsEngine();
    }

    @Override
    public AudioDevice createAudioDevice() {
        return new DirectXAudioDevice();
    }

    @Override
    public PipelineCompiler createPipelineCompiler() {
        return new DirectXPipelineCompiler();
    }
}
