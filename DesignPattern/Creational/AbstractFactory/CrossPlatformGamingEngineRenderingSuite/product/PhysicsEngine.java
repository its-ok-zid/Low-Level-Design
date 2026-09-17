package DesignPattern.Creational.AbstractFactory.CrossPlatformGamingEngineRenderingSuite.product;

public interface PhysicsEngine {
    void initializeSimulation(float gravity, int maxSubSteps);
    void simulateStep(float deltaTime);
}
