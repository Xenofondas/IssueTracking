package src.servlets;



import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.SQLException;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
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


@WebServlet("/home")
public class IndexServlet extends HttpServlet {
	
	public IndexServlet() {
		// TODO Auto-generated constructor stub
	}
	
	
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        engine.process("home.html", context, response.getWriter());
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {       	
       
        
        doGet(request, response);
    }
}