package net.team.project;

import com.google.common.reflect.ClassPath;
import lombok.SneakyThrows;
import org.bukkit.event.Listener;

import java.util.ArrayList;
public class EventRegistrator {

    Project project;

    private final String eventsPackage = "net.team.project.events";

    private ArrayList<String> loadedEvents;
    public EventRegistrator(Project project) {
        this.project = project;
        project.logger.info("Initialized event registrator");
    }

    @SneakyThrows
    public void initializeEvents() {
        loadedEvents = new ArrayList<>();

        ClassPath.from(project.getClass().getClassLoader()).getAllClasses().stream()
                .filter(info -> info.getPackageName().startsWith(eventsPackage))
                .forEach(info -> {
                    Class listenerClass = info.load();
                    try {
                        project.getServer().getPluginManager().registerEvents(
                                (Listener) listenerClass.newInstance(),
                                project);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    loadedEvents.add(info.getName());
                });

        project.logger.info("Initialized "+loadedEvents.size()+" events");
    }
}
