package command;

import command.impl.LanguageCommand;
import command.impl.ListActivitiesCommand;
import command.impl.ViewActivityCommand;
import command.impl.admin.*;
import command.impl.auth.*;
import command.impl.user.CreateRequestCommand;
import command.impl.user.FinishMyTaskCommand;
import command.impl.user.ListUserTasksCommand;
import command.impl.user.ViewMyProfileCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, ICommand> commands = new HashMap<String, ICommand>();

    static {
        //common

        commands.put("logout", new LogoutCommand());
        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());
        commands.put("change_language", new LanguageCommand());
        commands.put("viewActivity", new ViewActivityCommand());
        commands.put("activities", new ListActivitiesCommand());
        commands.put("tasks", new ListUserTasksCommand());
        //admin
        commands.put("users", new ListUsersCommand());
        commands.put("createActivity", new ListActivitiesCommand());
        commands.put("editActivity", new EditActivityCommand());
        commands.put("stats", new StatsCommand());
        commands.put("viewUser", new ViewUserCommand());
        commands.put("requests", new ListRequestsCommand());
        commands.put("acceptRequest", new AcceptRequestCommand());
        commands.put("dismissRequest", new DismissRequestCommand());

        //user;
        commands.put("createRequest", new CreateRequestCommand());
        commands.put("me", new ViewMyProfileCommand());
        commands.put("finishTask", new FinishMyTaskCommand());

    }

    public static ICommand getCommandByName(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return null;
        }
        return commands.get(commandName);
    }
}
