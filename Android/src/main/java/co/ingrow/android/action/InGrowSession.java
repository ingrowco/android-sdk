package co.ingrow.android.action;

public class InGrowSession {
    private final String userId;

    public InGrowSession(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("Anonymous Id should not be empty.");
        }

        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
