import java.util.Objects;

public class Policy {
    private String policyNumber;
    private String clientName;
    private double basePremium;
    private int riskLevel;
    private double vehicleValue;
    private boolean hasAlarm;
    private boolean claimFreeClient;

    private static int createdPolicyCount = 0;
    private static final double ADMINISTRATIVE_FEE = 50.0;

    public Policy(String policyNumber, String clientName, double basePremium, int riskLevel, double vehicleValue,
            boolean hasAlarm, boolean claimFreeClient) {
        this.policyNumber = policyNumber;
        this.clientName = clientName;
        this.basePremium = basePremium;
        this.riskLevel = riskLevel;
        this.vehicleValue = vehicleValue;
        this.hasAlarm = hasAlarm;
        this.claimFreeClient = claimFreeClient;
        createdPolicyCount++;

    }

    public double calculateFinalPremium() {
        double premium = basePremium + ADMINISTRATIVE_FEE;
        premium += riskLevel * 120.0;
        if (vehicleValue > 60000.0) {
            premium += 150.0;
        }
        if (hasAlarm) {
            premium -= 50.0;
        }
        if (claimFreeClient) {
            premium *= 0.90;
        }
        if (premium < basePremium) {
            premium = basePremium;
        }
        return Math.round(premium * 100.0) / 100.0;
    }

    public double calculateRenewalPremium() {
        double currentPremium = calculateFinalPremium();
        double renewal = currentPremium;

        if (riskLevel == 4) {
            renewal *= 1.10;
        } else if (riskLevel >= 5) {
            renewal *= 1.20;
        }
        if (vehicleValue > 60000.0) {
            renewal += 150.0;
        }
        if (claimFreeClient) {
            renewal *= 0.92;
        }
        if (hasAlarm) {
            renewal *= 0.95;
        }

        double minTreshold = currentPremium * 0.90;
        double maxTreshold = currentPremium * 1.25;

        if (renewal < minTreshold) {
            renewal = minTreshold;
        } else if (renewal > maxTreshold) {
            renewal = maxTreshold;
        }
        return Math.round(renewal * 100.0 / 100.0);
    }

    public String getRiskSummary() {
        return "Policy " + policyNumber + " has a risk level of " + riskLevel + "/5.";
    }

    public static int getCreatedPolicyCount() {
        return createdPolicyCount;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public int getRiskLevel() {
        return riskLevel;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Policy policy = (Policy) object;
        return Objects.equals(policyNumber, policy.policyNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyNumber);
    }

    @Override
    public String toString() {
        return "Policy{" +
                "policyNumber='" + policyNumber + '\'' +
                ", clientName='=" + clientName + '\'' +
                ", currentPremium=" + String.format("%.2f", calculateFinalPremium()) + '}';
    }

}