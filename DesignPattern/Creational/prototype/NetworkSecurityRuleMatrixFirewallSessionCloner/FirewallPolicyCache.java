package DesignPattern.Creational.prototype.NetworkSecurityRuleMatrixFirewallSessionCloner;

import java.util.HashMap;
import java.util.Map;

public class FirewallPolicyCache {
    private final Map<String, FirewallSessionPolicy> cache = new HashMap<>();

    public void registerPolicy(String profileKey, FirewallSessionPolicy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("Policy cannot be null.");
        }
        // Store an independent deep copy to protect the template
        cache.put(profileKey, policy.deepCopy());
    }

    public FirewallSessionPolicy getPolicy(String profileKey) {
        return getPolicy(profileKey, true); // Safe default: deep copy
    }

    public FirewallSessionPolicy getPolicy(String profileKey, boolean deepCopy) {
        FirewallSessionPolicy policy = cache.get(profileKey);
        if (policy == null) {
            throw new IllegalArgumentException("No policy found for profile key: " + profileKey);
        }
        return deepCopy ? policy.deepCopy() : policy.shallowCopy();
    }
}