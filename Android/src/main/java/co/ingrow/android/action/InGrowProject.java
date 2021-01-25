package co.ingrow.android.action;

import androidx.annotation.Nullable;

public class InGrowProject {

    private final String project;
    private final String stream;
    private final String apiKey;
    private String anonymousId;
    private String userId;

    public InGrowProject(String apiKey, String project, String stream) {
        if (null == project || project.trim().isEmpty()) {
            throw new IllegalArgumentException("Api key should not be empty.");
        }

        if (null == project || project.trim().isEmpty()) {
            throw new IllegalArgumentException("Project object should not be empty: " + project);
        }

        if ((null == stream || stream.trim().isEmpty())) {
            throw new IllegalArgumentException("Stream must be non-null and non-empty.");
        }

        this.project = project;
        this.stream = stream;
        this.apiKey = apiKey;
    }

    public InGrowProject(String apiKey, String project, String stream, String anonymousId, @Nullable String userId) {
        if (null == apiKey || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("Api key should not be empty.");
        }

        if (null == project || project.trim().isEmpty()) {
            throw new IllegalArgumentException("Project object should not be empty: " + project);
        }

        if ((null == stream || stream.trim().isEmpty())) {
            throw new IllegalArgumentException("Stream must be non-null and non-empty.");
        }

        if (null == anonymousId || anonymousId.trim().isEmpty()) {
            throw new IllegalArgumentException("AnonymousId should not be empty.");
        }


        this.project = project;
        this.stream = stream;
        this.apiKey = apiKey;
        this.anonymousId = anonymousId;
        if (!(null == userId || userId.trim().isEmpty())) {
            this.userId = userId;
        }
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
}
