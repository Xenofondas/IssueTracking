package src.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;

import com.mysql.cj.protocol.Resultset;

import src.config.DbUtil;
import src.models.Issue;

@ManagedBean
public class IssueService {

	Connection connection = null;
	
	public void createIssue(Issue issue) throws SQLException {
		
		String query = "INSERT INTO ist_issues (title, description, category, time_modified, modified_by, created_by, status,"
				+ "assigned_to, time_created ) VALUES ('"+issue.getTitle()+"',"
						+ " '"+issue.getDescription()+"', '"+issue.getCategory()+"', '"+issue.getTimeModified()+"',"
								+ " '"+issue.getModifiedBy()+"', '"+issue.getCreatedBy()+"', '"+issue.getStatus()+"',"
										+ " '"+issue.getAssignedTo()+"', '"+issue.getTimeCreated()+"')";

		
		connection= DbUtil.getConnection();
	    PreparedStatement preparedStatement = connection.prepareStatement(query); 
	    int rs = preparedStatement.executeUpdate();
	    
	    System.out.println("Return of rs: "+rs);	
		
	}
	
	public List<Issue> getAllIssues() throws SQLException {
		
		List<Issue> issues = new ArrayList<Issue>();
		String query = "SELECT * FROM ist_issues";

		
		connection= DbUtil.getConnection();
	    PreparedStatement preparedStatement = connection.prepareStatement(query); 
	    ResultSet rs = preparedStatement.executeQuery();
	    
        while (rs.next()) {
        	issues.add(new Issue(rs.getLong("id"), rs.getString("title"),rs.getString("description"),rs.getString("category"),rs.getString("time_created"),
        			rs.getString("time_modified"),rs.getString("created_by"),rs.getString("status"),
        			rs.getString("assigned_to"),rs.getString("modified_by")));
        	
		
        // do something with the extracted data...
    }
	   
        
	    System.out.println("Length: "+issues);	
		return issues;
	}
	
	
	public void deleteIssue(Long id) throws SQLException {
		
		String query = "DELETE FROM ist_issues where id="+id+"";
		
		connection= DbUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query); 
	    int rs = preparedStatement.executeUpdate();
		
	}
	
	public Issue findIssueById(Long id) throws SQLException {
		
		String query = "SELECT * FROM ist_issues where id="+id;
		
		connection= DbUtil.getConnection();
	    PreparedStatement preparedStatement = connection.prepareStatement(query); 
	    ResultSet rs = preparedStatement.executeQuery();
	    
	    rs.next();
	    
	    Issue issue = new Issue(rs.getLong("id"), rs.getString("title"),rs.getString("description"),rs.getString("category"),rs.getString("time_created"),
    			rs.getString("time_modified"),rs.getString("created_by"),rs.getString("status"),
    			rs.getString("assigned_to"),rs.getString("modified_by"));
	    
		return issue;
	}
	
	public void updateIssue(Issue issue) throws SQLException {
		
		String query = "UPDATE ist_issues  SET title='"+issue.getTitle()+"', description='"+issue.getDescription()+"', category='"+issue.getCategory()+"',"
				+ "time_modified='"+issue.getTimeModified()+"', modified_by='"+issue.getModifiedBy()+"', status='"+issue.getStatus()+"',"+ 
				"assigned_to='"+issue.getAssignedTo()+"' WHERE id="+issue.getId();
		
		connection= DbUtil.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(query); 
	    int rs = preparedStatement.executeUpdate();	
		
	}
	
	
	
}
