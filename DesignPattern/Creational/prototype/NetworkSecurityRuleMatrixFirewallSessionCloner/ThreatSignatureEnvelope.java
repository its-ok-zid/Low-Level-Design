package DesignPattern.Creational.prototype.NetworkSecurityRuleMatrixFirewallSessionCloner;

import java.util.HashSet;
import java.util.Set;

public class ThreatSignatureEnvelope implements Prototype<ThreatSignatureEnvelope> {
    private final Set<String> signatures;
    private final int anomalyThreshold;

    public ThreatSignatureEnvelope(Set<String> signatures, int anomalyThreshold) {
        this.signatures = (signatures != null) ? new HashSet<>(signatures) : new HashSet<>();
        this.anomalyThreshold = anomalyThreshold;
    }

    public ThreatSignatureEnvelope(ThreatSignatureEnvelope source, boolean deepCopy) {
        this.anomalyThreshold = source.anomalyThreshold;
        if (deepCopy) {
            this.signatures = (source.signatures != null) ? new HashSet<>(source.signatures) : new HashSet<>();
        } else {
            this.signatures = source.signatures;
        }
    }

    @Override
    public ThreatSignatureEnvelope shallowCopy() {
        return new ThreatSignatureEnvelope(this, false);
    }

    @Override
    public ThreatSignatureEnvelope deepCopy() {
        return new ThreatSignatureEnvelope(this, true);
    }

    public void addSignature(String signature) {
        if (signature != null && !signature.isBlank()) {
            this.signatures.add(signature.trim());
        }
    }

    public Set<String> getSignatures() {
        return signatures;
    }

    public int getAnomalyThreshold() {
        return anomalyThreshold;
    }
}