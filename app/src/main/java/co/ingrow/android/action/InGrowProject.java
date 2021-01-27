package co.ingrow.android.action;


public class InGrowProject {

    private final String project;
    private final String stream;
    private final String apiKey;
    private final boolean isLoggingEnable;
    private String anonymousId;
    private String userId;

    public InGrowProject(String apiKey, String project, String stream, Boolean isLoggingEnable) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("Api key should not be empty.");
        }

        if (project == null || project.trim().isEmpty()) {
            throw new IllegalArgumentException("Project object should not be empty: " + project);
        }

        if ((stream == null || stream.trim().isEmpty())) {
            throw new IllegalArgumentException("Stream must be non-null and non-empty.");
        }

        this.project = project;
        this.stream = stream;
        this.apiKey = apiKey;
        this.isLoggingEnable = (isLoggingEnable == null ? false : isLoggingEnable);
    }

    public InGrowProject(String apiKey, String project, String stream, Boolean isLoggingEnable, String anonymousId, String userId) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("Api key should not be empty.");
        }

        if (project == null || project.trim().isEmpty()) {
            throw new IllegalArgumentException("Project object should not be empty: " + project);
        }

        if ((stream == null || stream.trim().isEmpty())) {
            throw new IllegalArgumentException("Stream must be non-null and non-empty.");
        }

        if (anonymousId == null || anonymousId.trim().isEmpty()) {
            throw new IllegalArgumentException("AnonymousId should not be empty.");
        }

        this.project = project;
        this.stream = stream;
        this.apiKey = apiKey;
        this.anonymousId = anonymousId;
        this.userId = userId;
        this.isLoggingEnable = (isLoggingEnable == null ? false : isLoggingEnable);
    }

    public String getAnonymousId() {
        return anonymousId;
    }

    public String getUserId() {
        return userId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getProject() {
        return project;
    }

    public String getStream() {
        return stream;
    }

    public boolean isLoggingEnable() {
        return isLoggingEnable;
    }
}
