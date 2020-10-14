package commands;

import commands.util.HttpAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Interface to use in Command pattern
 * @Author Yuliia Aleksandrova
 */
public interface ICommand {
    String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException;

}
