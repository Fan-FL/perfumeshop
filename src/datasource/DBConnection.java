package datasource;

import java.sql.*;

public class DBConnection {

	private static final String DB_CONNECTION = "jdbc:mysql://localhost/perfume";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";
	
	
	public static PreparedStatement prepare(String stm) throws SQLException {
		PreparedStatement preparedStatement = null;
		try {	
			Connection dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(stm);
		} catch (SQLException e) {
			System.out.println(e.getMessage());

		}

		return preparedStatement;
	}

    public static PreparedStatement prepareReturnKeys(String stm) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            Connection dbConnection = getDBConnection();
            preparedStatement = dbConnection.prepareStatement(stm,
                    com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return preparedStatement;
    }

	public static Connection getDBConnection() {
		try {
		    Class.forName("com.mysql.jdbc.Driver");
			Connection dbConnection = DriverManager.getConnection(
                            DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Connection problem");
		return null;
	}

	/**
	 * 关闭数据库资源，关闭Statement和Connection、RestltSet
	 */
	public static void release(Statement statement, Connection con, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				// 数据库连接池的Connection对象进行close时，并不是真的进行关闭，而是把该数据库连接归还到数据库连接池中。
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
