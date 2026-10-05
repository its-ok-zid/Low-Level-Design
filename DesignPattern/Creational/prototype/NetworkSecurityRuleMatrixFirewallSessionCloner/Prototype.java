package DesignPattern.Creational.prototype.NetworkSecurityRuleMatrixFirewallSessionCloner;

public interface Prototype<T> {
    T shallowCopy();

    T deepCopy();
}
