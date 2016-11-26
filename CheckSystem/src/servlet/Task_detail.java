package servlet;

import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Task_detail
 */
@WebServlet("/Task_detail")
public class Task_detail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Task_detail() {
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
		JSONObject json = new JSONObject(DB_act.get_request(request, response));
		String type = request.getParameter("Type");
		switch(type){
		case "Get":
			int cd_plan_id = Integer.parseInt(json.getString("cd_plan_id"));
			//int cd_eq_id = Integer.parseInt(json.getString("cd_eq_id"));
			//int cd_check_id = Integer.parseInt(json.getString("cd_check_id"));
			String str = "cd_plan_id="+String.valueOf(cd_plan_id);
			try {
				response.getWriter().append(display_task_detail(str));
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
	//按设备打包
	String display_task_detail(String search) throws SQLException{
		String result = "Failed";
		Unit unit = Unit.getUnit();					
		if(DB_act.Check_plan_detail_select(search, unit)){	
				JSONObject resultOfSQL = new JSONObject(); //储存当前行的数据		
				JSONArray check_list_of_eq = new JSONArray(); //某一个设备的检查项数据数组
				JSONObject seq_of_eq = new JSONObject(); //打包一个设备的检查项
				
				JSONObject ToApp = new JSONObject(); //打包最终发给App的数据
				ResultSetMetaData meta = unit.rs.getMetaData();			
				ArrayList<Integer> seq=new ArrayList<Integer>();
				seq.add(-1);
				String CurrentEqID = "Default";
				while(unit.rs.next()){		
					resultOfSQL = new JSONObject();
					//将数据库每一行的每一个属性加入JSON数组
					for(int i = 2; i<7; i++){
						String colIndex = meta.getColumnName(i);
						String value = unit.rs.getString(colIndex);
						resultOfSQL.put(colIndex, value);
					}
					//检查当前检查项的设备ID是否与之前的相同
					CurrentEqID = resultOfSQL.getString("cd_eq_id");
					int CurrentSeq = checkExist(CurrentEqID,seq);
					if(CurrentSeq>=0){
						//seq_of_eq.put("eq"+String.valueOf(seq.size()-1), check_list_of_eq);
						seq_of_eq.getJSONArray("eq"+(CurrentSeq)).put(resultOfSQL);
					}else{						
						seq.add(Integer.parseInt(CurrentEqID));					
						check_list_of_eq = new JSONArray();
						check_list_of_eq.put(resultOfSQL);
						seq_of_eq.put("eq"+String.valueOf(seq.size()-1), check_list_of_eq);
					}					
					//resultToApp.put(seq_of_eq);
					
				}
				//对整理好的数据按照设备ID进行分类打包
				ToApp.put("resultList", seq_of_eq);
				unit.close();
				return ToApp.toString();
				
		}else{
				result = "Failed";
				unit.close();
				return result;
			}
	}	
	
	int checkExist(String value,ArrayList<Integer> seq){
		for(int i = 0;i < seq.size(); i++){
			if(value.equals(String.valueOf(seq.get(i)))){
				return i;
			}
				
		}
		return -1;
	}
	
	static boolean insert_task_detail(int cd_plan_id,int cd_eq_id,int cd_check_id){
		boolean result = false;
		if(DB_act.Check_plan_detail_insert(cd_plan_id, cd_eq_id, cd_check_id)){
			result = true;
			return result;
		}else{
			result = false;
			return result;
		}
	}
}
	


