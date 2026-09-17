package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.metal;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PhysicsEngine;

public class MetalPhysicsEngine implements PhysicsEngine {
    @Override
    public void initializeSimulation(float gravity, int maxSubSteps) {
        System.out.println("Initializing Metal Physics Engine with gravity: " + gravity + " and maxSubSteps: " + maxSubSteps);
    }

    @Override
    public void simulateStep(float deltaTime) {
        System.out.println("Simulating physics step in Metal Physics Engine with deltaTime: " + deltaTime);
    }
}
