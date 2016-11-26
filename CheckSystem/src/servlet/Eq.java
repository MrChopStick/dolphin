package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

/**
 * Servlet implementation class Eq
 */
@WebServlet("/Eq")
public class Eq extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Eq() {
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
		
		JSONObject json = new JSONObject(DB_act.get_request(request, response));
		
		int eq_id = 0;
		String eq_name = new String();
		int eq_group = 0;
		int eq_type = 0;
		int eq_state = 0;
		int eq_manager = 0;
		String searchType = new String();		
		String search = new String();

		String type = request.getParameter("Type");
		switch(type){
		
		case "Get":
			//��ȡ�豸�б�
			/*
			searchType = json.names().toString();
			
			switch(searchType){
			
			case "eq_id":
				search = searchType + "=" + "`" + json.getString("search") + "`";
				break;
			case "eq_name":
				search = searchType + "=" + json.getString("search");
				break;
			case "eq_manager":
				search = searchType + "=" + json.getString("search");
				break;
			case "eq_state":
				search = searchType + "=" + json.getString("search");
				break;
			case "eq_group":
				search = searchType + "=" + json.getString("search");
				break;
			case "eq_type":
				search = searchType + "=" + json.getString("search");
				break;
			case "eq_all":
				search = "1=1";
				break;
			}
			*/
			
			Iterator<String> getKey = json.keys();
			String key = getKey.next().toString();
			if(key.equals("eq_ALL"))
				search = "1=1";
			else
				search = key + "=" + "'" +json.getString(key) + "'";
			try {
				response.getWriter().append(create_eq_list(search));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case "Insert":
			//����һ�����豸
			eq_name = json.getString("eq_name");
			eq_group = Integer.parseInt(json.getString("eq_group"));
			eq_type = Integer.parseInt(json.getString("eq_type"));			
			eq_manager = Integer.parseInt(json.getString("eq_manager"));
			
			response.getWriter().append(insert_eq(eq_name, eq_group, eq_type, eq_state, eq_manager));
			break;
		case "Update":
			//�޸�һ���豸
			eq_name = json.getString("eq_name");
			eq_group = Integer.parseInt(json.getString("eq_group"));
			eq_type = Integer.parseInt(json.getString("eq_type"));			
			eq_manager = Integer.parseInt(json.getString("eq_manager"));
			eq_state = Integer.parseInt(json.getString("eq_state"));
			response.getWriter().append(update_eq(eq_id, eq_name, eq_group, eq_type, eq_state, eq_manager));
			break;
		case "Delete":
			//ɾ��һ���豸
			eq_id = Integer.parseInt(json.getString("eq_id"));
			try {
				response.getWriter().append(delete_eq(eq_id));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	String create_eq_list(String search) throws SQLException{
		Unit unit = Unit.getUnit(); 
		if(DB_act.Equip_list_select(search,unit)){
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
			return resultToApp.toString();
		
		}else{	
			String result = "Failed";	
			unit.close();
			return result;
		}
			
	}
	
	String insert_eq(String eq_name, int eq_group,
			int eq_type, int eq_state, int manager){		
		String result = new String();
		if(DB_act.Equip_list_insert(eq_name, eq_group, eq_type, eq_state, manager))
			result = "OK";
		else
			result = "Failed";
		return result;
	}
	
	String delete_eq(int eq_id) throws SQLException{
		String result;
		String search = "eq_id="+String.valueOf(eq_id);
		Unit unit = Unit.getUnit();
		if(DB_act.Equip_list_select(String.valueOf(search), unit)){
			unit.rs.deleteRow();
			result = "OK";
		}else{
			result = "Failed";
		}
		return result;
	}
	
	String update_eq(int eq_id, String eq_name, int eq_group,
			int eq_type, int eq_state, int manager){	
		String result;
		if(DB_act.Equip_list_update(eq_name, eq_group, eq_type, eq_state, manager,eq_id))
			result = "OK";
		else
			result = "Failed";
		return result;
	}

}
