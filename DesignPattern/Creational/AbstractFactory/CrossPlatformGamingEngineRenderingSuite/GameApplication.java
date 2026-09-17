package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.factory.DirectXBackendFactory;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.factory.MetalBackendFactory;
import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.factory.VulkanBackendFactory;

public class GameApplication {
    public static void main(String[] args) {
        // Create a factory for the DirectX backend

        GameWorld gameWorld = new GameWorld(new DirectXBackendFactory());
        System.out.println("Game application initialized with DirectX backend.");
        gameWorld.loadScene("SampleScene", 5.7f);
        gameWorld.simulateEngineStep();
        gameWorld.playDeviceSpatialSound();
        gameWorld.getPipelineBinaryFormat();


        //Create a factory for the Vulkan backend
        GameWorld vulkanGameWorld = new GameWorld(new VulkanBackendFactory());
        System.out.println("Game application initialized with Vulkan backend.");
        vulkanGameWorld.loadScene("VulkanScene", 9.8f);
        vulkanGameWorld.simulateEngineStep();
        vulkanGameWorld.playDeviceSpatialSound();
        vulkanGameWorld.getPipelineBinaryFormat();

        // Create a factory for the Meta backend
        GameWorld metaGameWorld = new GameWorld(new MetalBackendFactory());
        System.out.println("Game application initialized with Metal backend.");
        metaGameWorld.loadScene("MetalScene", 9.8f);
        metaGameWorld.simulateEngineStep();
        metaGameWorld.playDeviceSpatialSound();
    }
}
