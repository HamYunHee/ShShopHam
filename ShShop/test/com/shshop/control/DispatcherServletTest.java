package com.shshop.control;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.shshop.command.Command;
import com.shshop.control.CommandResult;
import com.shshop.control.DispatcherServlet;
import com.shshop.mapper.ProductMapper;
import com.shshop.util.MyBatisUtil;

public class DispatcherServletTest {
	
	SqlSession sqlSession = null; 
	
	@Mock  private DispatcherServlet servlet;
	@Mock  private HttpServletRequest request;
	@Mock  private HttpServletResponse response;
	@Mock  private HttpSession session;

    @Before
    public void setUp() {
    	sqlSession = MyBatisUtil.getSqlSessionFactory().openSession(); 
          servlet = new DispatcherServlet();
          request = mock(HttpServletRequest.class );      
          response = mock(HttpServletResponse.class );    
          session = mock(HttpSession.class);
   }
    
	@After
	public void tearDown() throws Exception {
		sqlSession.rollback();
		sqlSession.close();
	}

    @Test
    public void testLoginCommandWithUnregisterdMail() throws ServletException, IOException {

        when(request.getParameter( "email" )).thenReturn("aaaa@gmail.com" );
        when(request.getParameter( "password" )).thenReturn("tttt" );
        when(request.getPathInfo()).thenReturn("/login");
     
    	Command cmd = servlet.getCommand(request);
    	CommandResult cmdResult = cmd.execute(request, response);
    	assertTrue("testLoginCommand",cmdResult.getContent()=="<h3>There is no adapted person.</h3>");
    }
    
    @Test
    public void testLoginCommandWithInvalidEmail() throws ServletException, IOException {

        when(request.getParameter( "email" )).thenReturn("aaaa" );
        when(request.getParameter( "password" )).thenReturn("tttt" );
        when(request.getPathInfo()).thenReturn("/login");
     
        Command cmd = servlet.getCommand(request);
        CommandResult cmdResult = cmd.execute(request, response);
    	assertTrue("testLoginCommand",cmdResult.getContent()=="<h3>U need to put vaild email address.</h3>");
    }
    
    @Test
    public void testLoginCommandWithEmptyPassword() throws ServletException, IOException {
 
        when(request.getParameter( "email" )).thenReturn("aaaa@gmail.com" );
        when(request.getParameter( "password" )).thenReturn("");
        when(request.getPathInfo()).thenReturn("/login");
    	when(request.getSession()).thenReturn(session); 
    	
        Command cmd = servlet.getCommand(request);
        CommandResult cmdResult = cmd.execute(request, response);
    	assertTrue("testLoginCommand",cmdResult.getContent()=="<h3>U need to put vaild password.</h3>"); 
   }
    
    @Test
    public void testLoginCommandWithVaildInput() throws ServletException, IOException {
 
        when(request.getParameter( "email" )).thenReturn("name1@gmail.com" );
        when(request.getParameter( "password" )).thenReturn("1111" );
        when(request.getPathInfo()).thenReturn("/login");
        when(request.getSession(false)).thenReturn(session);
        
        Command cmd = servlet.getCommand(request);
        CommandResult cmdResult = cmd.execute(request, response);
    	assertTrue("testLoginCommand",cmdResult.getViewURI().equalsIgnoreCase("/WEB-INF/view/login.jsp")); 
   }
}

 