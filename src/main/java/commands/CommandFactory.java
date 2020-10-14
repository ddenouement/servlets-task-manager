package commands;

import commands.impl.LanguageCommand;
import commands.impl.common.ListActivitiesCommand;
import commands.impl.common.ViewActivityCommand;
import commands.impl.WelcomeCommand;
import commands.impl.admin.*;
import commands.impl.auth.*;
import commands.impl.user.CreateRequestCommand;
import commands.impl.user.FinishMyTaskCommand;
import commands.impl.user.ListUserTasksCommand;
import commands.impl.user.ViewMyProfileCommand;

import java.util.HashMap;
import java.util.Map;
/**
 * Contains all possible commands
 * @Author Yuliia Aleksandrova
 */
public class CommandFactory {
    private static Map<String, ICommand> commands = new HashMap<>();

    static {
        //common
        commands.put("welcome", new WelcomeCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());
        commands.put("change_language", new LanguageCommand());
        commands.put("viewActivity", new ViewActivityCommand());
        commands.put("activities", new ListActivitiesCommand());
        commands.put("tasks", new ListUserTasksCommand());

        //admin
        commands.put("addCategory", new CreateCategoryCommand());
        commands.put("addActivity", new CreateActivityCommand());
        commands.put("editActivity", new EditActivityCommand());
        commands.put("deleteActivity", new DeleteActivityCommand());
        commands.put("stats", new StatsCommand());
        commands.put("users", new ListUsersCommand());
        commands.put("viewUser", new ViewUserCommand());
        commands.put("requests", new ListRequestsCommand());
        commands.put("acceptRequest", new AcceptRequestCommand());
        commands.put("dismissRequest", new DismissRequestCommand());

        //user
        commands.put("createRequest", new CreateRequestCommand());
        commands.put("me", new ViewMyProfileCommand());
        commands.put("finishTask", new FinishMyTaskCommand());

    }

    public static ICommand getCommandByName(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("welcome");
        }
        return commands.get(commandName);
    }
}
