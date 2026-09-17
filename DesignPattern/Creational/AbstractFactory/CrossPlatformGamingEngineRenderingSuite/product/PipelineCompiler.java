package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product;

public interface PipelineCompiler {
    void compileShaders(String vertexSrc, String fragmentSrc);
    String getBinaryFormat();
}
