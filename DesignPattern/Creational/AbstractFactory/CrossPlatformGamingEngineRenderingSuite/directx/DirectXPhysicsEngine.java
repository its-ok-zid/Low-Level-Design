package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.directx;

import DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product.PhysicsEngine;

public class DirectXPhysicsEngine implements PhysicsEngine {
    @Override
    public void initializeSimulation(float gravity, int maxSubSteps) {
        System.out.println("DirectXPhysicsEngine: Initializing physics simulation with gravity " + gravity + " and max sub-steps " + maxSubSteps + ".");
    }

    @Override
    public void simulateStep(float deltaTime) {
        System.out.println("DirectXPhysicsEngine: Simulating physics step with delta time " + deltaTime + " seconds.");
    }
}
