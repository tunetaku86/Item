package item.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import item.bean.ItemBean;

public class ItemDAO2 {

	private Connection con = null;

	public ItemDAO2() throws DAOException{
		getConnection();
	}
	//全件取得
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
	//ソート用のメソッド
	public List<ItemBean>sortPrice(boolean isAscending) throws DAOException{
		if(con==null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//SQL文の作成
			String sql;
			//ソートキーの指定
			if (isAscending)
				sql = "SELECT * FROM item ORDER BY price";
			else
				sql = "SELECT * FROM item ORDER BY price desc";
			//PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			//SQLの実行
			rs = st.executeQuery();
			//結果の取得
			List<ItemBean>list = new ArrayList<ItemBean>();
			while (rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				ItemBean bean = new ItemBean(code, name, price);
				list.add(bean);
			}
			//商品一覧をlistとして返す
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました");
		}finally {
			try {
				if(st!=null)
					st.close();
				if (rs!=null)
					rs.close();
				close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("リソースの解放に失敗しました");
			}
		}
	}
	//本の追加メソッド
	public int additem(String name,int price) throws DAOException{
		if (con==null)
			getConnection();

		PreparedStatement st = null;
		try {
			//SQL文の作成
			String sql = "INSERT INTO item(name,price) VALUES(?,?)";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//商品名と値段の設定
			st.setString(1, name);
			st.setInt(2, price);
			//SQLの実行
			int rows = st.executeUpdate();
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました");
		} finally {
			try {
			//リソースの解放
				if(st!=null)
					st.close();
					close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("リソースの解放に失敗しました");
			}
		}
	}
	public List<ItemBean> findByPrice(int lePrice)throws DAOException{
		if(con == null)
			getConnection();

			PreparedStatement st = null;
			ResultSet rs = null;

		try {
			//SQL文の作成
			String sql = "SELECT * FROM item WHERE price<=?";
			//PreparedStatementの取得
			st = con.prepareStatement(sql);
			//値段のセット
			st.setInt(1, lePrice);
			//SQLの実行
			rs = st.executeQuery();
			//結果の取得
			List<ItemBean> list =new ArrayList<ItemBean>();
			while (rs.next()) {
				int code = rs.getInt("code");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				ItemBean bean = new ItemBean(code, name, price);
				list.add(bean);
			}
			//商品一覧をlistとして返す
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました");
		} finally {
			try {
			if(rs!=null)rs.close();
			if(st!=null)st.close();
			close();
			}catch (Exception e){
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}
	public int deleteByPrimaryKey(int key)throws DAOException{

		if(con == null)
			getConnection();

		PreparedStatement st = null;
		try {
			//SQL文の作成
			String sql = "DELETE FROM item WHERE code=?";
			//PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			//主キーの指定
			st.setInt(1, key);
			//SQLの実行
			int rows = st.executeUpdate();
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの操作に失敗しました。");
		} finally {
			try {
				if(st!=null)st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}

	}
	//getConnection()メソッド
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


