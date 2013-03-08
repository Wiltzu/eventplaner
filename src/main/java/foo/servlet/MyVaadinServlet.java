package foo.servlet;

import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.VaadinServlet;

/**
 * <p>
 * This class extends VaadinServlet's functionality. It adds a creation of H2
 * database to its initialization.
 * </p>
 * 
 * @author Ville Ahti
 * 
 */
public class MyVaadinServlet extends VaadinServlet {

	private Logger logger = LoggerFactory.getLogger(MyVaadinServlet.class);

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		logger.info("before database server creation");
		Server dbServer = null;
		try {
			dbServer = Server.createTcpServer(null);
			dbServer.start();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger.info("after database server creation. " + "db address: "
				+ dbServer.getURL());
	}

}
