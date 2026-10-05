package DesignPattern.Creational.prototype.NetworkSecurityRuleMatrixFirewallSessionCloner;

public class SecurityRule implements Prototype<SecurityRule> {
    private final String ruleId;
    private final String protocol;
    private final String portRange;
    private final RuleAction action;
    private long matchedPacketCount;

    public SecurityRule(String ruleId, String protocol, String portRange, RuleAction action, long matchedPacketCount) {
        this.ruleId = ruleId;
        this.protocol = protocol;
        this.portRange = portRange;
        this.action = action;
        this.matchedPacketCount = matchedPacketCount;
    }

    public SecurityRule(SecurityRule source) {
        this.ruleId = source.ruleId;
        this.protocol = source.protocol;
        this.portRange = source.portRange;
        this.action = source.action;
        this.matchedPacketCount = source.matchedPacketCount;
    }


    @Override
    public SecurityRule shallowCopy() {
        return new SecurityRule(this);
    }

    @Override
    public SecurityRule deepCopy() {
        return new SecurityRule(this);
    }

    public void incrementPacketCount() {
        this.matchedPacketCount++;
    }

    public String getRuleId() {
        return ruleId;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getPortRange() {
        return portRange;
    }

    public RuleAction getAction() {
        return action;
    }

    public long getMatchedPacketCount() {
        return matchedPacketCount;
    }

    public void setMatchedPacketCount(long matchedPacketCount) {
        this.matchedPacketCount = matchedPacketCount;
    }
}
