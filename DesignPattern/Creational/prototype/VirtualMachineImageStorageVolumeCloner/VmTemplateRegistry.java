package DesignPattern.Creational.prototype.VirtualMachineImageStorageVolumeCloner;

import java.util.HashMap;
import java.util.Map;

public class VmTemplateRegistry {
    private final Map<String, VirtualMachine> registry = new HashMap<>();

    public void registerTemplate(String key, VirtualMachine value) {
        // Defensive copy: Store an independent deep copy so caller cannot mutate the golden master
        registry.put(key, value.deepCopy());
    }

    public VirtualMachine getTemplate(String key) {
        return getTemplate(key, true); // Safe default: deep copy
    }

    public VirtualMachine getTemplate(String key, boolean deep) {
        VirtualMachine prototype = registry.get(key);
        if (prototype == null) {
            throw new IllegalArgumentException("Template not found: " + key);
        }
        return deep ? prototype.deepCopy() : prototype.shallowCopy();
    }
}