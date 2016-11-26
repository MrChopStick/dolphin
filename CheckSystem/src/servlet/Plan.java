package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.text.*;

/**
 * Servlet implementation class Plan
 */
@WebServlet("/Plan")
public class Plan extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Plan() {
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
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		int plan_id = 0;
		String plan_name = new String();
		int plan_cycle = 0;
		Date plan_start_date = new Date(0);

		Time plan_start_time = new Time(0);
		Time plan_dead_time = new Time(0);
		
		int inspector_id = 0;
		int manager_id = 0;
		int plan_state = 0;
		String plan_note = new String();
		//JSONObject json = new JSONObject(DB_act.get_request(request, response));
		JSONObject json = new JSONObject(DB_act.get_request(request, response));
		String type = request.getParameter("Type");
		String search = new String();
		switch(type){
		case "Get":
			
			Iterator<String> getKey = json.keys();
			String key = getKey.next().toString();
			if(key.equals("plan_ALL") || key.equals("plan_all")){
				search = "1=1";			
			}else{
				search = key + "=" + json.getString(key);
			}		
			try {
				response.getWriter().append(create_plan_list(search));
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			break;
		case "Insert":		
			JSONArray jsonArray = new JSONArray(DB_act.get_request(request, response));
			
			JSONObject json2 = jsonArray.getJSONObject(0);
			plan_name = json2.getString("plan_name");
			plan_cycle = Integer.parseInt(json2.getString("plan_cycle"));
			plan_start_date = Date.valueOf(json2.getString("plan_start_date"));
			plan_start_time = Time.valueOf(json2.getString("plan_start_time"));
			plan_dead_time = Time.valueOf(json2.getString("plan_dead_time"));
			inspector_id = Integer.parseInt(json2.getString("inspector_id"));
			manager_id = Integer.parseInt(json2.getString("manager_id"));
			plan_note = json2.getString("plan_note");
			plan_state = Integer.parseInt(json2.getString("plan_state"));
			//int cd_plan_id
			int cd_eq_id = 0;
			int cd_check_id = 0;
			int cd_plan_id = DB_act.Check_plan_insert(plan_name,plan_cycle,plan_start_date,
					plan_start_time,plan_dead_time,inspector_id,manager_id,plan_state,
					plan_note);
			int falseTime = 0;
			for(int i = 1; i<jsonArray.length(); i++){
				json2 = jsonArray.getJSONObject(i);
				cd_eq_id = Integer.parseInt(json2.getString("cd_eq_id"));
				cd_check_id = Integer.parseInt(json2.getString("cd_check_id"));
				if(Task_detail.insert_task_detail(cd_plan_id, cd_eq_id, cd_check_id)){
					;
				}else{
					falseTime++;
					break;
				}
			}
			if(falseTime>0){
				response.getWriter().append("Failed");
				falseTime = 0;
			}else{
				response.getWriter().append("OK");
			}
			
			System.out.println(cd_plan_id);
			/*
			for(int i = 0;i<jsonArray.length();i++){
				json = jsonArray.getJSONObject(i);
				cd_eq_id = Integer.parseInt(json.getString("cd_eq_id"));	
				cd_check_id = Integer.parseInt(json.getString("cd_check_id"));
				response.getWriter().append(insert_plan_detail(cd_eq_id,cd_check_id,cd_plan_id));
			}
			*/
			break;
		case "Delete":
			JSONObject json3 = new JSONObject(DB_act.get_request(request, response));
			plan_id = Integer.parseInt(json3.getString("plan_id"));
			try {
				response.getWriter().append(delete_plan(plan_id));
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			break;
		case "Update":
			JSONObject json4 = new JSONObject(DB_act.get_request(request, response));
			plan_id = Integer.parseInt(json4.getString("plan_id"));
			response.getWriter().append(update_plan(plan_name,plan_cycle,plan_start_date,
					plan_start_time,plan_dead_time,inspector_id,manager_id,plan_state,
					plan_note,plan_id));
			break;
		}				
	}
	
	String create_plan_list(String search) throws SQLException{
		String result = new String();
		Unit unit = Unit.getUnit();
		if(DB_act.Check_plan_select(search, unit)){
			JSONObject resultOfSQL = new JSONObject();
			JSONArray resultToApp = new JSONArray();
			JSONObject ToApp = new JSONObject();
			ResultSetMetaData meteData = unit.rs.getMetaData();
			while(unit.rs.next()){
				resultOfSQL = new JSONObject();
				for(int i = 1; i < 11; i++){
					String columnIndex = meteData.getColumnLabel(i);
					//String value = new String();
					String value = unit.rs.getString(columnIndex);
					/*
					if(i == 2)
						value = unit.rs.getString(columnIndex);
					else 
						value = String.valueOf(unit.rs.getInt(columnIndex));
						*/	
					resultOfSQL.put(columnIndex, value);
				}
				resultToApp.put(resultOfSQL);			
			}
			unit.close();
			//System.out.println(resultToApp.toString());
			
			ToApp.put("resultList", resultToApp);
			//System.out.println(ToApp.toString());
			return ToApp.toString();
			}else{
				result = "Failed";	
				unit.close();
				return result;
			}			
		}	
	
	int insert_plan(String name,int cycle,Date start_data,Time start_time,Time dead_time,
			int inspector,int manager,int state,String note){
		int cd_plan_id = DB_act.Check_plan_insert
				(name, cycle, start_data, start_time, dead_time, inspector, manager, state, note);
		/*
		if(DB_act.Check_plan_insert(name, cycle, start_data, 
				start_time, dead_time, inspector, manager, state, note)){
			result = "OK";
		}else{
			result = "Failed";
		}
		return result;
		*/
		return cd_plan_id;
		
	}
	
	String insert_plan_detail(int cd_eq_id, int cd_check_id, int cd_plan_id){
		String result = new String();
		if(DB_act.Check_plan_detail_insert(cd_plan_id, cd_eq_id, cd_check_id)){
			result = "OK";
		}else{
			result = "Failed";
		}
		return result;
	}
	
	String delete_plan(int plan_id) throws SQLException{
		String result = new String();
		String str = "plan_id="+String.valueOf(plan_id);
		Unit unit = Unit.getUnit();
		if(DB_act.Check_plan_select(str, unit)){
			unit.rs.next();
			unit.rs.deleteRow();
			result = "OK";
		}else{
			result = "Failed";
		}
		return result;
	}
	
	String update_plan(String name,int cycle,Date start_data,Time start_time,Time dead_time,
			int inspector,int manager,int state,String note,int id){
		String result = new String();
		if(DB_act.Check_plan_Update(name, cycle, start_data, 
				start_time, dead_time, inspector, manager, state, note,id)){
			result = "OK";
		}else{
			result = "Failed";
		}
		return result;
		
	}

}
