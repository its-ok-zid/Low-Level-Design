package DesignPattern.Singleton.DistributedHardwareLicenseKeyLeaseTokenAllocator;

public class LicenseToken {
    private final String tokenId;
    private final String allocatedTo;
    private final long issuedAt;
    private final long durationMillis;

    public LicenseToken(String tokenId, String allocatedTo, long issuedAt, long durationMillis) {
        this.tokenId = tokenId;
        this.allocatedTo = allocatedTo;
        this.issuedAt = issuedAt;
        this.durationMillis = durationMillis;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getAllocatedTo() {
        return allocatedTo;
    }

    public boolean isValid() {
        return System.currentTimeMillis() < (issuedAt + durationMillis);
    }

    @Override
    public String toString() {
        return "LicenseToken{" +
                "tokenId='" + tokenId + '\'' +
                ", allocatedTo='" + allocatedTo + '\'' +
                ", issuedAt=" + issuedAt +
                ", durationMillis=" + durationMillis +
                '}';
    }
}