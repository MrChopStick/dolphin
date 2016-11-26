package servlet;

import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Check_list
 */
@WebServlet("/Check_list")
public class Check_list extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Check_list() {
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
		JSONObject json =new JSONObject(DB_act.get_request(request, response));
		String type = request.getParameter("Type");
		String str = new String();
		switch(type){
		case "Get":
			Iterator<String> getKey = json.keys();
			String key = getKey.next().toString();
			if(key.equals("check_all") || key.equals("check_ALL"))
				str = "1=1";
			else {
				str = key + "=" + json.getString(key);
			}
			try {
				response.getWriter().append(create_check_list(str));
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}	
			
		}
	}
	
	String create_check_list(String str) throws SQLException{
		String result = new String();
		Unit unit = Unit.getUnit();
		if(DB_act.Check_list_select(str, unit)){
			JSONObject resultOfSQL = new JSONObject();
			JSONArray resultToApp = new JSONArray();
			JSONObject ToApp = new JSONObject();
			ResultSetMetaData meteData = unit.rs.getMetaData();
			while(unit.rs.next()){
				resultOfSQL = new JSONObject();
				for(int i = 1; i < 6; i++){
					String columnIndex = meteData.getColumnLabel(i);
					//String value = new String();
					String value = unit.rs.getString(columnIndex);
					resultOfSQL.put(columnIndex, value);
				}
				resultToApp.put(resultOfSQL);			
			}
			unit.close();			
			ToApp.put("resultList", resultToApp);
			return ToApp.toString();
			}else{
				result = "Failed";	
				unit.close();
				return result;
			}			
	}

}
