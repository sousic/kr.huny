package kr.huny.model.db.embedded;

public enum AttachmentStatus {
    QUEUE("QUEUE"),
    STORED("STORED");

    private String type;

    AttachmentStatus(String type) { this.type = type; }

    public String getType() { return this.type;}
}
