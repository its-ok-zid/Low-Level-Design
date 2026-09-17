package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.directx;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PipelineCompiler;

public class DirectXPipelineCompiler implements PipelineCompiler {
    @Override
    public void compileShaders(String vertexSrc, String fragmentSrc) {
        System.out.println("DirectXPipelineCompiler: Compiling vertex shader and fragment shader for DirectX.");
    }

    @Override
    public String getBinaryFormat() {
        return "DirectX Shader Binary Format (DXBC)";
    }
}
