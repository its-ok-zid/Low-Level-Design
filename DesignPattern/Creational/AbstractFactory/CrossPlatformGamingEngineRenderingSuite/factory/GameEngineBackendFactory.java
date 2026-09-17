package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.factory;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.AudioDevice;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PhysicsEngine;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PipelineCompiler;

public interface GameEngineBackendFactory {
    PhysicsEngine createPhysicsEngine();
    AudioDevice createAudioDevice();
    PipelineCompiler createPipelineCompiler();
}