package servlet;

import java.io.BufferedReader;
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
 * Servlet implementation class Task
 */
@WebServlet("/Task")
public class Task extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Task() {
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
		//doGet(request, response);
		String search = new String();
		JSONObject json = new JSONObject(DB_act.get_request(request, response));
		Iterator<String> getKey = json.keys();
		String key = getKey.next().toString();
		if(key.equals("task_ALL"))
			search = "1=1";
		else
			search = key + "=" + "'" +json.getString(key) + "'";
		try {
			response.getWriter().append(create_task_list(search));
		} catch (SQLException e1) {
			e1.printStackTrace();
			response.getWriter().append("Failed");
		}
					
	}
	
	
	String create_task_list(String search) throws SQLException
	{
		String result = new String();
		Unit unit = Unit.getUnit();
		if(DB_act.Check_task_select(search, unit)){
			JSONObject resultOfSQL = new JSONObject();
			JSONArray resultToApp = new JSONArray();
			ResultSetMetaData meteData = unit.rs.getMetaData();
			while(unit.rs.next()){
				resultOfSQL = new JSONObject();
				for(int i = 1; i < 6; i++){
					String columnIndex = meteData.getColumnLabel(i);
					String value = unit.rs.getString(columnIndex);
					resultOfSQL.put(columnIndex, value);
				}
				resultToApp.put(resultOfSQL);			
			}
			unit.close();
			JSONObject ToApp = new JSONObject();
			ToApp.put("resultList", resultToApp);
			return ToApp.toString();
		
		}else{	
			result = "Failed";	
			unit.close();
			return result;
		}
	}

}
