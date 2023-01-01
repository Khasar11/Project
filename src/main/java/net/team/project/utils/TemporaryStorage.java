package net.team.project.utils;

import net.team.project.Project;

import java.util.List;

public class TemporaryStorage {

    Project project;
    public TemporaryStorage(Project project) {
        this.project = project;
    }

    public List<TeleportRequest> teleportationRequestCollection;

}
