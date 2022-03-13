package src.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import src.config.TemplateEngineUtil;
import src.models.User;
import src.services.UserService;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserService userService;
    
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable("message",request.getParameter("error"));
        engine.process("login.html", context, response.getWriter());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
		WebContext context = new WebContext(request, response, request.getServletContext());
		RequestDispatcher rd =null;
		
		response.setContentType("text/html");      
		
             try {
            	 String username = userService.findUser(request.getParameter("email"), request.getParameter("password"));   
            	 
            	 HttpSession session = request.getSession();
            	 session.setAttribute("username", username);
            	 response.sendRedirect(request.getContextPath() + "/IssuesServlet");
								
				
			} catch (Exception e) {
				String errorMessage = e.getMessage();
				response.sendRedirect(request.getContextPath() + "/LoginServlet?error="+errorMessage);
			}
	}

}
