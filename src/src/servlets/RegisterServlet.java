package src.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import src.config.TemplateEngineUtil;
import src.models.User;
import src.services.UserService;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject UserService userService;
       
    
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable("message",request.getParameter("error"));
        engine.process("register.html", context, response.getWriter());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User newUser = new User(request.getParameter("email"), request.getParameter("password") );
        
      System.out.println("User to be added is: "+newUser.toString());
     
		
		  try { 
			  userService.SaveUser(newUser); 
			  response.sendRedirect(request.getContextPath() + "/LoginServlet");
			  
		  } catch (Exception e) { // TODO
			  String errorMessage = e.getMessage();
			  response.sendRedirect(request.getContextPath() + "/RegisterServlet?error="+errorMessage);
		  }  
		  
		  
		 
		 
	}

}
