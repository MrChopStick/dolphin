package servlet;
//Realm
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.*;


/**
 * Servlet implementation class test
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("Do.");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		int staff_id = 0;		
		String staff_passwd = new String();
		String DataString = DB_act.get_request(request, response);
		try{			
			JSONObject json = new JSONObject(DataString.toString());
			staff_id = Integer.parseInt(json.getString("staff_id"));
			staff_passwd = json.getString("staff_passwd");		
			String login = "staff_id="+String.valueOf(staff_id)+" AND "
									+ "staff_passwd="+staff_passwd;
			response.getWriter().append(CheckUser(login));
			System.out.println("Login");
		}catch(Exception e)
		{
			e.printStackTrace();
		}    
		
	}
	
	public String CheckUser(String login) throws IOException, SQLException
	{	
		String result;
		Unit unit = Unit.getUnit();	
		if(DB_act.Staff_select(login, unit)){
			unit.rs.last();
			JSONObject resultOfSQL = new JSONObject();
			JSONArray resultToApp = new JSONArray();
			if(unit.rs.getRow()>0){
				ResultSetMetaData metaData = unit.rs.getMetaData();
				String colName = new String();
				String value = new String();
				for(int i = 2; i<6; i++){
					colName = metaData.getColumnName(i);
					value = unit.rs.getString(colName);
					resultOfSQL.put(colName, value);
				}
				result = resultOfSQL.toString();
				System.out.println(resultOfSQL.toString());
			}else{
				result = "Failed";
			}			
		}else{
			result = "Failed";
		}
		return result;		
		}
		/*
		System.out.println("MyServlet:name = " + userId);
        System.out.println("MyServlet:password = " + passwd); 
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/checksystem";
		String user = "root";
		String password = "963355";
		boolean returnResult = false;
		try{
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);
			
			if(!conn.isClosed())
				System.out.println("Succeeded connecting to database");
			else
				System.out.println("Failed connecting to database");
			Statement statement = conn.createStatement();
			String sql = "SELECT * FROM staff WHERE staff_id='"+userId+"' AND staff_passwd='"+passwd+"'";
			ResultSet result = statement.executeQuery(sql); //ִ��
			result.last();
			
			if(result.getRow() > 0){
				ResponseJSON(request,response,"succeed");
				System.out.println("succeed");
				returnResult = true;
			}else{
				ResponseJSON(request,response,"failed");
				System.out.println("failed");
				returnResult = false;
			}
			//if()
			conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
	}
	/*
	public static void ResponseJSON(HttpServletRequest request, HttpServletResponse response,
			String resultOfSQL) throws ServletException, IOException
	{
		
		StringBuilder sbput = new StringBuilder();
	    sbput.append('[');
	    sbput.append('{');
	    sbput.append("response:");
	    sbput.append(resultOfSQL);
	    sbput.append('}');
	    sbput.append(']');
	   // response.setAttribute("json", sbput.toString());
	    //request.getRequestDispatcher("/WEB-INF/page/jsonnewslist.jsp").forward(request, response);
	    response.getWriter().append(sbput.toString());
	}
	*/
	
	