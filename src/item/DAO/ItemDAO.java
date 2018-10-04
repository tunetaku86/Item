package item.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import item.bean.ItemBean;

public class ItemDAO {

	private Connection con;

	public ItemDAO() throws DAOException{
		getConnection();
	}

	public List<ItemBean>findAll() throws DAOException{
			if (con==null)
				getConnection();

			PreparedStatement st = null;
			ResultSet rs = null;
		try {
			//SQL文の作成
			String sql = "SELECT * FROM item";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//SQLの実行
			rs = st.executeQuery();
			//結果の取得および表示
			List<ItemBean> list = new ArrayList<ItemBean>();
			while (rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				ItemBean bean = new ItemBean(code, name, price);
				list.add(bean);
			}
			//商品一覧をListとして返す
			return list;
		} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗しました");
		} finally {
			try {
			//リソースの解放
				if (rs!= null)rs.close();
				if (st!= null)rs.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの解放に失敗しました");
			}
		}
	}
	private void getConnection() throws DAOException{

		try {
			//JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
			//URL,ユーザー名,パスワードの設定
			String url = "jdbc:postgresql:sample";
			String user = "student";
			String pass = "himitu";
			//データベースへの接続
			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			throw new DAOException("接続に失敗しました");
		}
	}
		private void close() throws SQLException{
			if (con!=null) {
				con.close();
				con=null;

		}
	}
}
