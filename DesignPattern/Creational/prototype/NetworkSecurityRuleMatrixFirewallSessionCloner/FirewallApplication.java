package DesignPattern.Creational.prototype.NetworkSecurityRuleMatrixFirewallSessionCloner;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FirewallApplication {
    public static void main(String[] args) {
        // Create a threat signature envelope
        ThreatSignatureEnvelope threatSignatureEnvelope = new ThreatSignatureEnvelope(Set.of("SQL Injection", "XSS"), 5);

        // Create security rules
        SecurityRule rule1 = new SecurityRule("rule1", "TCP", "80", RuleAction.ALLOW, 0);
        SecurityRule rule2 = new SecurityRule("rule2", "TCP", "443", RuleAction.DENY, 0);

        // Create a firewall session policy
        FirewallSessionPolicy policy = new FirewallSessionPolicy(
                "session1",
                "Tier1",
                List.of(rule1, rule2),
                threatSignatureEnvelope,
                Map.of("rateLimit", 100),
                true
        );

        // Register the policy in the cache
        FirewallPolicyCache cache = new FirewallPolicyCache();
        cache.registerPolicy("profile1", policy);

        // Retrieve a deep copy of the policy from the cache
        FirewallSessionPolicy copiedPolicy = cache.getPolicy("profile1", true);

        // Modify the copied policy to demonstrate that it's a deep copy
        copiedPolicy.getRules().get(0).incrementPacketCount();
        copiedPolicy.getThreatSignatureEnvelope().addSignature("New Threat");

        // Print out the original and copied policies to show they are independent
        System.out.println("Original Policy Packet Count: " + policy.getRules().get(0).getMatchedPacketCount());
        System.out.println("Copied Policy Packet Count: " + copiedPolicy.getRules().get(0).getMatchedPacketCount());

        System.out.println("Original Policy Signatures: " + policy.getThreatSignatureEnvelope().getSignatures());
        System.out.println("Copied Policy Signatures: " + copiedPolicy.getThreatSignatureEnvelope().getSignatures());
    }

}
