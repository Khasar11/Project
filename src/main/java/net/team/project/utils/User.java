package net.team.project.utils;

import net.team.project.Project;

import java.io.File;
import java.util.UUID;


public class User extends ConfigurationFile {

    public User(Project project, UUID uuid) {
        super(project, project.getDataFolder() + File.separator + "userdata", String.valueOf(uuid), true, false);
    }
}