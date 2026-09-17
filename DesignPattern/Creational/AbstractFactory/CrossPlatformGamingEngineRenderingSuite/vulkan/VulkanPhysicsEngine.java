package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.vulkan;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PhysicsEngine;

public class VulkanPhysicsEngine implements PhysicsEngine {
    @Override
    public void initializeSimulation(float gravity, int maxSubSteps) {
        System.out.println("Initializing Vulkan Physics Engine with gravity: " + gravity + " and max sub-steps: " + maxSubSteps);
    }

    @Override
    public void simulateStep(float deltaTime) {
        System.out.println("Simulating physics step with delta time: " + deltaTime + " seconds using Vulkan Physics Engine.");
    }
}
