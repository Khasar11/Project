package net.team.project.utils;

import com.google.common.reflect.ClassPath;
import lombok.SneakyThrows;
import net.team.project.Project;
import net.team.project.api.commandAPI.CommandHandler;

import java.util.ArrayList;

public class CommandRegistrator {

    private ArrayList<String> loadedCommands;
    private final String commandsPackage = "net.team.project.commands";

    Project project;
    public CommandRegistrator(Project project) {
        this.project = project;
    }

    @SneakyThrows
    public void initializeCommands() {
        loadedCommands = new ArrayList<>();
        CommandHandler.registerCommands(commandsPackage, project);
        ClassPath.from(project.getClass().getClassLoader()).getAllClasses().stream()
                .filter(info -> info.getPackageName().startsWith(commandsPackage))
                .forEach(info -> loadedCommands.add(info.load().getName()));
        project.logger.info("Initialized "+loadedCommands.size()+" commands");
    }
}
