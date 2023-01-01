package net.team.project.utils;

import java.time.LocalDateTime;
import java.util.UUID;

public class TeleportRequest {

    public UUID from;
    public UUID to;
    public LocalDateTime timestamp;

    public TeleportRequest(UUID from, UUID to, LocalDateTime timestamp) {
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
    }
}
