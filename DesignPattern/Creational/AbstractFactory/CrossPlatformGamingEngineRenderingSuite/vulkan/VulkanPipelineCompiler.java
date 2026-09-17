package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.vulkan;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PipelineCompiler;

public class VulkanPipelineCompiler implements PipelineCompiler {
    @Override
    public void compileShaders(String vertexSrc, String fragmentSrc) {
        System.out.println("Compiling shaders for Vulkan Pipeline:");
        System.out.println("Vertex Shader Source: " + vertexSrc);
        System.out.println("Fragment Shader Source: " + fragmentSrc);
    }

    @Override
    public String getBinaryFormat() {
        return "Vulkan SPIR-V";
    }
}
