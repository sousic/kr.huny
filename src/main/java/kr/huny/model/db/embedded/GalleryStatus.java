package kr.huny.model.db.embedded;

public enum GalleryStatus {
    QUEUE("QUEUE"),
    STORED("STORED");

    private String type;

    GalleryStatus(String type) { this.type = type; }

    public String getType() { return this.type;}
}
