package pl.tomihome.ciscowebpowermeter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Servlet implementation class CiscoPowerMeter
 */
public class CiscoPowerMeter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static ClassPathXmlApplicationContext appCtx;
	private static DbConfig dbConfig;
	
	/**
	 * ssh config
	 */
	
	JSch jsch;
	Session session;
	 String privateKeyPath;

	Logger logger = Logger.getLogger(CiscoPowerMeter.class.getName());
	
	/**
	 * Default constructor.
	 */
	public CiscoPowerMeter() {
		// TODO Auto-generated constructor stub

		appCtx = new ClassPathXmlApplicationContext("app-ctx.xml");
		dbConfig = appCtx.getBean("dbConfig", DbConfig.class);
		
		jsch = new JSch();
	    session = null;
	    privateKeyPath = "/home/tomi/.ssh/id_rsa";
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//response.getWriter().append("test");
		logger.info("Get request send by " + request.getRemoteAddr());
		
		//ajax response
	    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
	   
	    String result = getValueSsh();
	   
	    response.getWriter().write("Current power consumption: " + result);       // Write response body
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String getValueSsh() {
	    try {
	        jsch.addIdentity(privateKeyPath);        
	        session = jsch.getSession("tomi", "192.168.0.6", 22);
	        session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
	        java.util.Properties config = new java.util.Properties(); 
	        config.put("StrictHostKeyChecking", "no");
	        session.setConfig(config);
	    } catch (JSchException e) {
	        throw new RuntimeException("Failed to create Jsch Session object.", e);
	    }
	    
	    String command = "show inventory";
	    try {
	        session.connect();
	        Channel channel = session.openChannel("exec");
	        ((ChannelExec) channel).setCommand(command);
	        ((ChannelExec) channel).setPty(false);
	        channel.connect();
	        channel.disconnect();
	        session.disconnect();
	    } catch (JSchException e) {
	        throw new RuntimeException("Error durring SSH command execution. Command: " + command);
	    }
		return "";
	}
}
