package controllers.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.SQLServerConnUtils_SQLJDBC;
import models.docDB;

@WebServlet(urlPatterns = { "/deleteProduct" })
public class DeleteProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteProductServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = SQLServerConnUtils_SQLJDBC.getJDBCConnection();
		String id = (String) request.getParameter("id");
		String errorString = null;
		try {
			docDB.deleteProduct(conn, id);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
// Náº¿u cĂ³ lá»—i, forward (chuyá»ƒn tiáº¿p) sang trang thĂ´ng bĂ¡o lá»—i.
		if (errorString != null) {
// LÆ°u thĂ´ng tin vĂ o request attribute trÆ°á»›c khi forward sang views.
			request.setAttribute("errorString", errorString);
//
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/deleteProductErrorView.jsp");
			dispatcher.forward(request, response);
		}
// Náº¿u má»�i thá»© tá»‘t Ä‘áº¹p.
// Redirect (chuyá»ƒn hÆ°á»›ng) sang trang danh sĂ¡ch sáº£n pháº©m.
		else {
			response.sendRedirect(request.getContextPath() + "/admin/productList");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}