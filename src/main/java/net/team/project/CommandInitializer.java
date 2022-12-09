package net.team.project;

import com.google.common.reflect.ClassPath;
import lombok.SneakyThrows;
import net.team.project.commandAPI.CommandHandler;

import java.util.ArrayList;
import java.util.logging.Level;

public class CommandInitializer {

    private ArrayList<String> loadedCommands;
    private final String commandsPackage = "net.team.project.commands";

    Project project;
    public CommandInitializer(Project project) {
        this.project = project;
    }

    @SneakyThrows
    public void initializeCommands() {
        loadedCommands = new ArrayList<>();
        CommandHandler.registerCommands(commandsPackage, project);
        ClassPath.from(this.getClass().getClassLoader()).getAllClasses().stream()
                .filter(info -> info.getPackageName().startsWith(commandsPackage))
                .forEach(info -> loadedCommands.add(info.load().getName()));
        project.logger.log(Level.INFO, "Initialized "+loadedCommands.size()+" commands");
    }
}
