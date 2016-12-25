package pl.tomihome.ciscowebpowermeter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Servlet implementation class CiscoPowerMeter
 */
public class CiscoPowerMeter extends HttpServlet {

	Logger logger = Logger.getLogger(CiscoPowerMeter.class.getName());

	/**
	 * Default constructor.
	 */
	public CiscoPowerMeter() {
		ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("app-ctx.xml");
		appCtx.getBean("dbConfig", DbConfig.class);
		appCtx.getBean("sshConfig", SshConfig.class);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// response.getWriter().append("test");
		logger.debug("Get request send by " + request.getRemoteAddr());

		// ajax response
		response.setContentType("text/plain"); // Set content type of the
												// response so that jQuery knows
												// what it can expect.
		response.setCharacterEncoding("UTF-8"); // You want world domination,
												// huh?

		String sshResult = "";
		try {
			sshResult = SshPowerGetter.getValueBySsh();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		logger.debug("ssh result: " + sshResult);
		response.getWriter().write("Current power consumption: " + sshResult); // Write
																				// response
																				// body
		try {
			DbInserter.insertIntoDb(sshResult);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
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
}
