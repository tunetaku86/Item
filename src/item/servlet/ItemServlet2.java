package item.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import item.DAO.DAOException;
import item.DAO.ItemDAO2;
import item.bean.ItemBean;

/**
 * Servlet implementation class ItemServlet2
 */
@WebServlet("/ItemServlet2")
public class ItemServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			request.setCharacterEncoding("UTF-8");
			//パラメータの解析
			String action = request.getParameter("action");
			//モデルのDAOを生成
			ItemDAO2 dao = new ItemDAO2();
			//パラメータ無しの場合は全レコード表示
			if (action == null||action.length() == 0) {
				List<ItemBean> list = dao.findAll();
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
			//addは追加
			}else if (action.equals("add")) {
				String name = request.getParameter("name");
				int price = Integer.parseInt(request.getParameter("price"));
				dao.additem(name, price);
				//追加後、全レコード表示
				List<ItemBean> list = dao.findAll();
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
			//sortはソート
			}else if (action.equals("sort")) {
				String key = request.getParameter("key");
				List<ItemBean> list;
				if(key.equals("price_asc")) {
						list = dao.sortPrice(true);
				}else {
						list = dao.sortPrice(false);
				}
				//Listをリクエストスコープに入れてJSPへフォワードする
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
			}else if (action.equals("search")) {
				int price = Integer.parseInt(request.getParameter("price"));
				List<ItemBean> list = dao.findByPrice(price);
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");


			}
				//deleteは削除
			else if(action.equals("delete")) {
				int code = Integer.parseInt(request.getParameter("code"));
				dao.deleteByPrimaryKey(code);
				//削除後、全レコード表示
				List<ItemBean> list = dao.findAll();
				//Listをリクエストスコープに入れてJSPへフォワードする
				request.setAttribute("items", list);
				gotoPage(request, response, "/showItem2.jsp");
 			}else {
 				request.setAttribute("message", "正しく操作してください");
 				gotoPage(request, response, "/errInternal.jsp");
 			}
		} catch (DAOException e) {
			e.printStackTrace();
			request.setAttribute("message", "内部エラーが発生しました");
			gotoPage(request,response,"/errInternal.jsp");
		}
	}
	private void gotoPage(HttpServletRequest request,HttpServletResponse response,String page)
			throws ServletException,IOException{
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
