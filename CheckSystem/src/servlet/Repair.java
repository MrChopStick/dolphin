package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




import java.text.*;
import java.util.Iterator;
import java.sql.*;

/**
 * Servlet implementation class Repair
 */
@WebServlet("/Repair")
public class Repair extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Repair() {
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
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String requestData = DB_act.get_request(request, response);
		int eqid = 0;
		int repair_id = 0;
		String detail = new String();
		SimpleDateFormat transfer = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		/*
		Timestamp report_date = new Timestamp(0);		
		Timestamp m_got_time = new Timestamp(0);
		Timestamp m_deal_time = new Timestamp(0);		
		Timestamp i_got_time = new Timestamp(0);		
		Timestamp i_arrive_time = new Timestamp(0);		
		Timestamp i_deal_time = new Timestamp(0);		
		Timestamp finish_time = new Timestamp(0);	
		*/
		Timestamp time = new Timestamp(0);
		int repair_state = 0;
		int inspector_id = 0;
		String Type = request.getParameter("Type");
		int TypeOfTime = 0;
		JSONObject json = new JSONObject(requestData);
		switch(Type){
		case "Get":
			String search = new String();
			Iterator<String> getKey = json.keys();
			String key = getKey.next().toString();
			if(key.equals("repair_ALL")){
				search = "1=1";
				System.out.println(key);
				try {
					response.getWriter().append(create_repair_list(search));
				} catch (JSONException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}else{
				search = key + "=" + json.getString(key);
				System.out.println(search);
				try {
					response.getWriter().append(create_repair_list(search));
				} catch (JSONException | SQLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
			try {
				create_repair_list(search);
			} catch (JSONException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case "Insert":
			try{
				eqid = Integer.parseInt(json.getString("repair_eqid"));
				detail = json.getString("repair_detail");
				time = Timestamp.valueOf(json.getString("report_time"));			
				response.getWriter().append(insert_repair_record(eqid,detail,repair_state));
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		case "Update":
			try{				
				TypeOfTime = Integer.parseInt(json.getString("TypeOfTime"));
				repair_id = Integer.parseInt(json.getString("repair_id"));
				time = Timestamp.valueOf(json.getString("Time"));
				if(TypeOfTime == 1){
					inspector_id = Integer.parseInt(json.getString("repair_inspector_id"));
					response.getWriter().append(update_repair(TypeOfTime,repair_id,time));
					DB_act.Repair_Update_Inspector(repair_id, inspector_id);
				}else{
					response.getWriter().append(update_repair(TypeOfTime,repair_id,time));
				}
				
			}catch(Exception e2){
				e2.printStackTrace();
			}
			break;
		case "Delete":
			try{
				repair_id = Integer.parseInt(json.getString("repair_id"));
				response.getWriter().append(create_repair_list(String.valueOf(repair_id)));
			}catch(Exception e3){
				e3.printStackTrace();
			}
			break;
		}		
	}
	
	
	String insert_repair_record(int eqid, String detail, int repair_state)
	{
		String result = new String();
		
		if(DB_act.Repair_insert(eqid, detail, repair_state)){
			result = "OK";
		}else{
			result = "Failed";
		}
		return result;
	}
	
	String create_repair_list(String search) throws JSONException, SQLException
	{	
		Unit unit = Unit.getUnit();
		if(DB_act.Repair_select(search,unit)){
			JSONObject resultOfSQL = new JSONObject();
			JSONArray resultToApp = new JSONArray();
			ResultSetMetaData meteData = unit.rs.getMetaData();
			while(unit.rs.next()){
				resultOfSQL = new JSONObject();
				for(int i = 1; i < 13; i++){
					String columnIndex = meteData.getColumnLabel(i);
					String value = new String();
					value = unit.rs.getString(columnIndex);	
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
	
	String update_repair(int TypeOfTime, int repair_id, Timestamp time){
		String result;
		if(DB_act.Repair_update(repair_id, TypeOfTime, time)){
			result = "OK";
		}else{
			result = "Failed";
		}
		return result;
	}
	
	
	
	String delete_repair(int repair_id) throws SQLException{
		String result = new String();
		String search = "repair_id="+String.valueOf(repair_id);
		Unit unit = Unit.getUnit();
		if(DB_act.Repair_select(String.valueOf(search), unit)){
			unit.rs.deleteRow();
			result = "OK";
		}else{
			result = "Failed";
		}
		return result;
	}
	
	String inspector(int TypeOfTime, int repair_id, Timestamp time , int inspector_id){
		String result;
		if(DB_act.Repair_update(repair_id, TypeOfTime, time)){
			result = "OK";
		}else{
			result = "Failed";
		}
		return result;
	}

}
