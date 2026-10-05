package DesignPattern.Creational.prototype.NetworkSecurityRuleMatrixFirewallSessionCloner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirewallSessionPolicy implements Prototype<FirewallSessionPolicy> {
    private String sessionId;
    private String policyTier;
    private List<SecurityRule> rules;
    private ThreatSignatureEnvelope threatSignatureEnvelope;
    private Map<String, Integer> rateLimits;
    private boolean active;

    public FirewallSessionPolicy(String sessionId, String policyTier, List<SecurityRule> rules,
                                 ThreatSignatureEnvelope threatSignatureEnvelope,
                                 Map<String, Integer> rateLimits, boolean active) {
        this.sessionId = sessionId;
        this.policyTier = policyTier;
        this.rules = (rules != null) ? new ArrayList<>(rules) : new ArrayList<>();
        this.threatSignatureEnvelope = threatSignatureEnvelope;
        this.rateLimits = (rateLimits != null) ? new HashMap<>(rateLimits) : new HashMap<>();
        this.active = active;
    }

    public FirewallSessionPolicy(FirewallSessionPolicy source, boolean deepCopy) {
        this.sessionId = source.sessionId;
        this.policyTier = source.policyTier;
        this.active = source.active;
        if (deepCopy) {
            // Deep copy each individual SecurityRule into a new ArrayList
            this.rules = (source.rules != null)
                    ? new ArrayList<>(source.rules.stream().map(SecurityRule::deepCopy).toList())
                    : new ArrayList<>();
            this.threatSignatureEnvelope = (source.threatSignatureEnvelope != null)
                    ? source.threatSignatureEnvelope.deepCopy()
                    : null;
            this.rateLimits = (source.rateLimits != null)
                    ? new HashMap<>(source.rateLimits)
                    : new HashMap<>();
        } else {
            this.rules = source.rules;
            this.threatSignatureEnvelope = source.threatSignatureEnvelope;
            this.rateLimits = source.rateLimits;
        }
    }

    @Override
    public FirewallSessionPolicy shallowCopy() {
        return new FirewallSessionPolicy(this, false);
    }

    @Override
    public FirewallSessionPolicy deepCopy() {
        return new FirewallSessionPolicy(this, true);
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public String getPolicyTier() { return policyTier; }
    public void setPolicyTier(String policyTier) { this.policyTier = policyTier; }
    public List<SecurityRule> getRules() { return rules; }
    public ThreatSignatureEnvelope getThreatSignatureEnvelope() { return threatSignatureEnvelope; }
    public Map<String, Integer> getRateLimits() { return rateLimits; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
