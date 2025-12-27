package model;

public enum GripPosition {
    EDGE("за самый край"),
    MIDDLE("за середину");

    private final String description;

    GripPosition(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}