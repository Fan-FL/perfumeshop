package datasource;

import java.sql.*;

public class DBConnection {

	private static final String DB_CONNECTION =
			"jdbc:postgresql://ec2-75-101-153-56.compute-1.amazonaws" +
					".com:5432/daitg26u2scsub?sslmode=require";
	private static final String DB_USER = "zxowbenqtkiqor";
	private static final String DB_PASSWORD = "ea708f31bd5c0db6655bfcee724c5e40bbcd6d709d3e13075056be3ecbc8d987";

//	private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/perfume";
//	private static final String DB_USER = "postgres";
//	private static final String DB_PASSWORD = "root";
	
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
                    Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return preparedStatement;
    }

	public static Connection getDBConnection() {
		try {
		    Class.forName("org.postgresql.Driver");
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
	 * Close database resources，Close Statement, Connectio and RestltSet
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
				statement.getConnection().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
