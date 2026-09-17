package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.metal;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PipelineCompiler;

public class MetalPipelineCompiler implements PipelineCompiler {
    @Override
    public void compileShaders(String vertexSrc, String fragmentSrc) {
        System.out.println("Compiling shaders for Metal Pipeline Compiler with vertex source: " + vertexSrc + " and fragment source: " + fragmentSrc);
    }

    @Override
    public String getBinaryFormat() {
        return "Metal Shader Binary Format: AIR";
    }
}
