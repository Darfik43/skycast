package com.darfik.skycast.usersession;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class TaskScheduleListener implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();

        UserSessionService userSessionService = UserSessionService.getInstance();

        scheduler.scheduleAtFixedRate(
                userSessionService::deleteExpiredSessions, 0, 1, TimeUnit.MINUTES
                );
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdown();
    }
}
