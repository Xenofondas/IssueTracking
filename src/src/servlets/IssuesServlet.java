package src.servlets;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

/**
 * Servlet implementation class IssuesServlet
 */
@WebServlet("/IssuesServlet")
public class IssuesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   @Inject
   private IssueService issueService; 
	
    public IssuesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());	
		String username = "";
		HttpSession session = request.getSession();
		
		if (session.getAttribute("username") != null ) {
			username = session.getAttribute("username").toString();
		}else {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
		}
				
		WebContext context = new WebContext(request, response, request.getServletContext());
        
		context.setVariable("username",username);
		
		
		if(request.getParameter("status")!=null) {
			try {
				List<Issue> issues = issueService.filterByStatus(request.getParameter("status"));
				context.setVariable("issues",issues);
				context.setVariable("statusFilter",request.getParameter("status"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {		
		
		try {
			List<Issue> issues = issueService.getAllIssues();
			context.setVariable("issues",issues);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
        engine.process("issues.html", context, response.getWriter());
        
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
       // WebContext context = new WebContext(request, response, request.getServletContext());
		HttpSession session = request.getSession();
		
		
		if(request.getParameter("action")!=null) {
        
		if ("delete".equals(request.getParameter("action"))) {
			
			Long id = Long.valueOf(request.getParameter("id")).longValue();
			
			try {
				issueService.deleteIssue(id);
				response.sendRedirect(request.getContextPath() + "/IssuesServlet");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}else if("filter".equals(request.getParameter("action"))) {
				String status = request.getParameter("statusFilter");		
				
				if(status.equals("without")) {
					response.sendRedirect(request.getContextPath() + "/IssuesServlet");
				}else {
					response.sendRedirect(request.getContextPath() + "/IssuesServlet?status="+status);
				}		
			}
		}else {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			
			Issue issue = new Issue(null, request.getParameter("title"), request.getParameter("description"), request.getParameter("category"),
					dtf.format(now), dtf.format(now), session.getAttribute("username").toString(), request.getParameter("status"),
					 request.getParameter("assignedTo"), request.getParameter("email"));
			
			
			try {
				issueService.createIssue(issue);
				response.sendRedirect(request.getContextPath() + "/IssuesServlet");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}
		
		// TODO Auto-generated method stub
		//doGet(request, response);
	}
	
}
