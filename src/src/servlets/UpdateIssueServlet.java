package src.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import src.config.TemplateEngineUtil;
import src.models.Issue;
import src.services.IssueService;

@WebServlet("/UpdateIssueServlet")
public class UpdateIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	private IssueService issueService;

	public UpdateIssueServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
		WebContext context = new WebContext(request, response, request.getServletContext());

		String username = "";
		HttpSession session = request.getSession();

		if (session.getAttribute("username") != null) {
			username = session.getAttribute("username").toString();
		} else {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
		}

		context.setVariable("username", username);
		Long id = Long.valueOf(request.getParameter("id")).longValue();

		try {
			Issue issue = issueService.findIssueById(id);
			context.setVariable("issue", issue);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		engine.process("updateIssue.html", context, response.getWriter());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = "";
		HttpSession session = request.getSession();

		if (session.getAttribute("username") != null) {
			username = session.getAttribute("username").toString();
		} else {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		Issue issue = new Issue();

		issue.setId(Long.valueOf(request.getParameter("id")).longValue());
		issue.setTitle(request.getParameter("title"));
		issue.setDescription(request.getParameter("description"));
		issue.setAssignedTo(request.getParameter("assignedTo"));
		issue.setCategory(request.getParameter("category"));
		issue.setStatus(request.getParameter("status"));
		issue.setModifiedBy(username);
		issue.setTimeModified(dtf.format(now));

		try {
			issueService.updateIssue(issue);
			response.sendRedirect(request.getContextPath() + "/IssuesServlet");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
