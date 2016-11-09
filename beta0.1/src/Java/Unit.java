package Java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Unit {
	private Connection con=null;
	public Statement st=null;
	public ResultSet rs=null;
	public void close(){
		try {
			if(rs!=null)
			rs.close();
			if(st!=null)
			st.close();
			if(con!=null)
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Unit(){
	}
	
	public static Unit getUnit(){
		Unit u=new Unit();
		try {
			u.con=Data_operator.ds.getConnection();
			u.st=u.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			return u;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			u.close();
			return null;
		}
	}
}
