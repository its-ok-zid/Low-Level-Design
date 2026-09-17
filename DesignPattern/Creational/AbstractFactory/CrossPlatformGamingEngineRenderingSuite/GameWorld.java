package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.factory.GameEngineBackendFactory;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.AudioDevice;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PhysicsEngine;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PipelineCompiler;

import java.util.Objects;

public class GameWorld {

    private final PhysicsEngine physicsEngine;
    private final AudioDevice audioDevice;
    private final PipelineCompiler pipelineCompiler;


    public GameWorld(GameEngineBackendFactory factory) {
        Objects.requireNonNull(factory, "GameEngineBackendFactory cannot be null");
        this.physicsEngine = factory.createPhysicsEngine();
        this.audioDevice = factory.createAudioDevice();
        this.pipelineCompiler = factory.createPipelineCompiler();
    }

    public void loadScene(String sceneName, float gravity) {

        System.out.println("Loading scene: " + sceneName + " with gravity: " + gravity);
        physicsEngine.initializeSimulation(gravity, 30);
        audioDevice.initAudioStream(4, 44100);
        pipelineCompiler.compileShaders("default_shader_pipeline", "vertex_shader.glsl");
    }

    public void simulateEngineStep() {
        System.out.println("Simulating engine step...");
        physicsEngine.simulateStep(0.016f); // Simulate a frame at ~60 FPS
    }

    public void playDeviceSpatialSound() {
        System.out.println("Playing spatial sound...");
        audioDevice.playSpatialSound("explosion.wav", 10.0f, 5.0f, 2.0f);
    }

    public void getPipelineBinaryFormat() {
        System.out.println("Getting pipeline binary format...");
        String binaryFormat = pipelineCompiler.getBinaryFormat();
        System.out.println("Pipeline Binary Format: " + binaryFormat);
    }
}
