package commands.impl.admin;

import commands.util.HttpAction;
import commands.ICommand;
import commands.util.PathUtils;
import model.Request;
import service.RequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListRequestsCommand implements ICommand {

    private static final String REQUESTS_LIST_PAGE_JSP = "admin/request/listall.jsp";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, HttpAction action) throws IOException, ServletException {
        String result = null;

        if (HttpAction.GET == action) {
            result = doGet(request, response);

        }
        return result;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) {
        PathUtils.saveCurrentPath(request,response);

        int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        int offset = (page-1)*recordsPerPage;
        List<Request> list = RequestService.getInstance().getAllRequestsPaged(recordsPerPage, offset);
        int numOfRequests = RequestService.getInstance().getNumRequests();
        int noOfPages = (int) Math.ceil(numOfRequests * 1.0 / recordsPerPage);

        request.setAttribute("requests", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        return REQUESTS_LIST_PAGE_JSP;
    }
}
