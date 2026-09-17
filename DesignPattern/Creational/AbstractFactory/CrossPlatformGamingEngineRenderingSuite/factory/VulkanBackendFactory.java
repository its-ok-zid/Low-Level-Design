package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.factory;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.AudioDevice;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PhysicsEngine;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PipelineCompiler;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.vulkan.VulkanAudioDevice;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.vulkan.VulkanPhysicsEngine;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.vulkan.VulkanPipelineCompiler;

public class VulkanBackendFactory implements GameEngineBackendFactory {
    @Override
    public PhysicsEngine createPhysicsEngine() {
        return new VulkanPhysicsEngine();
    }

    @Override
    public AudioDevice createAudioDevice() {
        return new VulkanAudioDevice();
    }

    @Override
    public PipelineCompiler createPipelineCompiler() {
        return new VulkanPipelineCompiler();
    }
}
