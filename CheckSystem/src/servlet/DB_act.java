package servlet;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.json.JSONTokener;
public class DB_act {
	private static Context Ctx;
	public static DataSource ds;
	static final int M_GOT_TIME=0;
	static final int M_DEAL_TIME=1;
	static final int I_GOT_TIME=2;
	static final int I_ARRIVE_TIME=3;
	static final int I_DEAL_TIME=4;
	static final int FINISH_TIME=5;
	static{
		try {
			Ctx = new InitialContext();
			ds =(DataSource)Ctx.lookup("java:comp/env/connectionPool");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//查询Repair表 ,若失败，注意关闭unit,如果是查整张表，str赋值"1=1";
	public static boolean Repair_select(String str,Unit unit) {
		StringBuffer query=new StringBuffer("SELECT * FROM repair_list WHERE ");
		query.append(str);
		try {
			unit.rs=unit.st.executeQuery(query.toString());
            return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//向Repair表中增加一项,插入不需要返回,报修单编号自动生成，
	public static boolean Repair_insert(int eqid,String detail,int state){
		String query=new String("Insert into repair_list(repair_eqid,repair_detail,repair_state) values(?,?,?)");
		PreUnit u=PreUnit.getUnit(query);
		if(u==null)
			return false;
		try {
			u.st.setInt(1, eqid);
			u.st.setString(2, detail);
			u.st.setInt(3,state);
			if(1==u.st.executeUpdate())
			{
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		u.close();
		return false;
			
		
	}
	//更新报修表的时间
	public static boolean Repair_update(int id,int type,Timestamp time){
		String type1=new String("UPDATE repair_list set m_got_time= ? WHERE repair_id = ?");
		String type2=new String("UPDATE repair_list set m_deal_time= ? WHERE repair_id = ?");
		String type3=new String("UPDATE repair_list set i_got_time= ? WHERE repair_id = ?");
		String type4=new String("UPDATE repair_list set i_arrive_time= ? WHERE repair_id = ?");
		String type5=new String("UPDATE repair_list set i_deal_time= ? WHERE repair_id = ?");
		String type6=new String("UPDATE repair_list set finish_time= ? WHERE repair_id = ?");
		PreUnit u=null;
		try {
			switch(type){
			case M_GOT_TIME:
				u=PreUnit.getUnit(type1);
				break;
			case M_DEAL_TIME:
				u=PreUnit.getUnit(type2);
				break;
			case I_GOT_TIME:
				u=PreUnit.getUnit(type3);
				break;
			case I_ARRIVE_TIME:
				u=PreUnit.getUnit(type4);
				break;
			case I_DEAL_TIME:
				u=PreUnit.getUnit(type5);
				break;
			case FINISH_TIME:
				u=PreUnit.getUnit(type6);
				break;
			default:
				return false;
			}
				if(u==null)
					return false;
				u.st.setTimestamp(1, time);
				u.st.setInt(2, id);
				if(1==u.st.executeUpdate())
				{
					u.close();
					return true;
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(u!=null)
		u.close();
		return false;
	}
	
	public static boolean Repair_Update_Inspector(int repair_id,int repair_inspector_id){
		String sql = new String("UPDATE repair_list SET repair_inspector_id="
				+String.valueOf(repair_inspector_id)+
				" WHERE repair_id="+String.valueOf(repair_id));
		PreUnit u=PreUnit.getUnit(sql);
		try {
			u.st.execute(sql);
			return true;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		
	}
	//删除采用查询得到的resultset的delectrow()实现。其他待定
	
	
	//eq_list的查询
	//同理查整张表str赋值"1=1"
	public static boolean Equip_list_select(String str,Unit unit){
		StringBuffer query=new StringBuffer("SELECT * FROM eq_list WHERE ");
		query.append(str);
		try {
			unit.rs=unit.st.executeQuery(query.toString());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//name,type,state为必填项，非必填项赋值为0
	//eq_insert
	public static boolean Equip_list_insert(String name,int group,int type,int state,int manager){
		String query=new String("INSERT INTO eq_list(eq_name,eq_group,eq_type,eq_state,eq_manager) values(?,?,?,?,?)");
		PreUnit u=PreUnit.getUnit(query);
		if(null==u)
			return false;
		try {
			u.st.setString(1, name);
			if(group==0)
			{
				u.st.setNull(2,Types.INTEGER);
			}else
			{
				u.st.setInt(2, group);
			}
			u.st.setInt(3, type);
			u.st.setInt(4, state);
			if(manager==0)
			{
				u.st.setNull(5, Types.INTEGER);
			}else
			{
				u.st.setInt(5, manager);
			}
			if(1==u.st.executeUpdate())
			{
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
				u.close();
				return false;
		}
		
	}
	//设备表更行，要求同上
	//eq_update,,,待测试
	//同上
	public static boolean Equip_list_update(String name,int group,int type,int state,int manager,int id){
		String query=new String("UPDATE eq_list SET eq_name = ?,eq_group = ? ,eq_type = ? ,"
				+ "eq_state = ?,eq_manager= ? WHERE eq_id = ?");
		PreUnit u=PreUnit.getUnit(query);
		if(null==u)
			return false;
		try {
			u.st.setString(1, name);
			if(group==0)
			{
				u.st.setNull(2,Types.INTEGER);
			}else
			{
				u.st.setInt(2, group);
			}
			u.st.setInt(3, type);
			u.st.setInt(4, state);
			if(manager==0)
			{
				u.st.setNull(5, Types.INTEGER);
			}else
			{
				u.st.setInt(5, manager);
			}
			u.st.setInt(6,id);
			if(1==u.st.executeUpdate())
			{
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
				u.close();
				return false;
		}
	}
	
	
	//eq_type查询
	//获取设备类型，同理使用“1=1”来获取所有数据
	public static boolean Equip_type_select(String str,Unit unit){ 
		StringBuffer query=new StringBuffer("SELECT * FROM eq_type WHERE "+str);
		try {
			unit.rs=unit.st.executeQuery(query.toString());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//eq_type_insert
	//check可以采用json格式
	public static boolean Equip_type_insert(String name,String check){
		String query=new String("Insert into eq_type(type_name,type_check) values(?,?)");
		PreUnit u=PreUnit.getUnit(query);
		if(null==u)
			return false;
		try {
			u.st.setString(1, name);
			u.st.setString(2,check);
			if(1==u.st.executeUpdate())
			{
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			u.close();
			return false;
		}
	}
	//eq_type_update
	//eq_type更新
	public static boolean Equip_type_update(String name,String check,int id){
		String query=new String("Update eq_type SET type_name = ?,type_check = ? WHERE type_id = ? ");
		PreUnit u=PreUnit.getUnit(query);
		if(null==u)
			return false;
		try {
			u.st.setString(1, name);
			u.st.setString(2, check);
			u.st.setInt(3, id);
			if(1==u.st.executeUpdate())
			{
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			u.close();
			return false;
		}
	}
	
	//eq_group
	public static boolean Equip_group_select(String str,Unit unit){
		StringBuffer query=new StringBuffer("Select * from eq_group WHERE ");
		query.append(str);
		try {
			unit.rs=unit.st.executeQuery(query.toString());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//eq_group_insert,id自增
	public static boolean Equip_group_insert(int manager){
		String query=new String("Insert into eq_group(group_manager) values(?)");
		PreUnit u=PreUnit.getUnit(query);
		if(u==null)
			return false;
		try {
			u.st.setInt(1, manager);
			if(1==u.st.executeUpdate()){
				u.close();
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			u.close();
			return false;
		}
		
	}
	//修改按id直接处理
	
	//plan_select
	public static boolean Check_plan_select(String str,Unit unit){
		StringBuffer query=new StringBuffer("SELECT * FROM check_plan WHERE ");
		query.append(str);
		try {
			unit.rs=unit.st.executeQuery(query.toString());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//plan_insert
	public static int Check_plan_insert(String name,int cycle,Date start_data,Time start_time,Time dead_time,
			int inspector,int manager,int state,String note){
		String query=new String("Insert into check_plan(plan_name,plan_cycle,plan_start_date,plan_start_time,plan_dead_time,"
				+ "inspector_id,manager_id,plan_state,plan_note) values(?,?,?,?,?,?,?,?,?)");
		PreUnit u=PreUnit.getUnit(query);
		if(null==u)
		{
			return 0;
		}
		try {
			u.st.setString(1, name);
			u.st.setInt(2, cycle);
			u.st.setDate(3, start_data);
			u.st.setTime(4, start_time);
			u.st.setTime(5, dead_time);
			if(inspector==0)
			{
				u.st.setNull(6, Types.INTEGER);
			}else{
				u.st.setInt(6, inspector);
			}
			u.st.setInt(7, manager);
			u.st.setInt(8, state);
			if(note==null)
			{
				u.st.setNull(9, Types.VARCHAR);
			}else{
				u.st.setString(9, note);
			}
			if(1==u.st.executeUpdate())
			{
				ResultSet rs=u.st.getGeneratedKeys();
				rs.next();
				int id=rs.getInt(1);
				rs.close();
				u.close();
				return id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			u.close();
			return  0;
		
	}
	
	//plan_update
	public static boolean Check_plan_Update(String name,int cycle,Date start_data,Time start_time,Time dead_time,
			int inspector,int manager,int state,String note,int id){
		String query=new String("Update Check_plan SET plan_name = ?,plan_cycle =?,plan_start_date =?,plan_start_time =?,plan_dead_time =?,"
				+ "inspector_id =?,manager_id =?,plan_state =?,plan_note =? WHERE plan_id = ?");
		PreUnit u=PreUnit.getUnit(query);
		try {
			u.st.setString(1, name);
			u.st.setInt(2, cycle);
			u.st.setDate(3, start_data);
			u.st.setTime(4, start_time);
			u.st.setTime(5, dead_time);
			u.st.setInt(6, inspector);
			u.st.setInt(7, manager);
			u.st.setInt(8, state);
			if(note.isEmpty())
			{
				u.st.setNull(9, Types.VARCHAR);
			}else{
				u.st.setString(9, note);
			}
			u.st.setInt(10, id);
			if(1==u.st.executeUpdate())
			{
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			u.close();
			return  false;
		}
	}

	//check_plan_detail
	public static boolean Check_plan_detail_select(String str,Unit unit){
		String query=new String("Select * From check_plan_detail WHERE "+str);
		try {
			unit.rs=unit.st.executeQuery(query);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//check_plan_detail_insert
	public static boolean Check_plan_detail_insert(int plan_id,int eq_id,int check_id){
		String query=new String("Insert into check_plan_detail(cd_plan_id,cd_eq_id,cd_check_id) values(?,?,?)");
		PreUnit u=PreUnit.getUnit(query);
		if(u==null)
			return false;
		try {
			u.st.setInt(1, plan_id);
			u.st.setInt(2, eq_id);
			u.st.setInt(3, check_id);
			if(1==u.st.executeUpdate())
			{
				u.st.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			u.close();
			return false;
	}
	//chech_plan_detail_update
	public static boolean Check_plan_detail_update(int plan_id,int eq_id,int check_id,int id){
		String query=new String("Update check_plan_detail SET cd_plan_id = ?,cd_eq_id = ?,"
				+ "cd_check_id = ? WHERE cd_id = ?");
		PreUnit u=PreUnit.getUnit(query);
		if(u==null)
			return false;
		try {
			u.st.setInt(1, plan_id);
			u.st.setInt(2, eq_id);
			u.st.setInt(3, check_id);
			u.st.setInt(4, id);
			if(1==u.st.executeUpdate())
			{
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			u.close();
			return false;
		}
	}
	
	
	//check_list,,,巡检任务
	public static boolean Check_list_select(String str,Unit unit){
		String query=new String("Select * From check_list WHERE "+str);
		try {
			unit.rs=unit.st.executeQuery(query);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//insert
	public static boolean Check_list_insert(int type,String name,String detail,int result){
		String query=new String("Insert into check_list(check_type,check_name,check_detail,check_result) values(?,?,?,?)");
		PreUnit u=PreUnit.getUnit(query);
		if(u==null)
		{
			return false;
		}
		try {
			u.st.setInt(1, type);
			u.st.setString(2, name);
			u.st.setString(3, detail);
			u.st.setInt(4, result);
			if(1==u.st.executeUpdate()){
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			u.close();
			return false;
		}
	}
	public static boolean Check_list_update(int type,String name,String detail,int result,int id){
		String query=new String("Update check_list SET check_type= ?,check_name= ?,check_detail= ?,check_result= ? "
				+ "WHERE check_id= ?");
		PreUnit u=PreUnit.getUnit(query);
		if(u==null)
		{
			return false;
		}
		try {
			u.st.setInt(1, type);
			u.st.setString(2, name);
			u.st.setString(3, detail);
			u.st.setInt(4, result);
			u.st.setInt(5, id);
			if(1==u.st.executeUpdate()){
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			u.close();
			return false;
		}
	}
	//这个更新一大堆，采用查询的方法更新。
	
	
	//check_task
	
	
	public static boolean Check_task_select(String str,Unit unit){
		StringBuffer query=new StringBuffer("Select * From Check_task WHERE "+str);
		try {
			unit.rs=unit.st.executeQuery(query.toString());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//check_insert.....由脚本产生
	//check_task_update,由查询所得ResultSet设置
	
	//check_result
	public static boolean Check_result_select(String str,Unit unit){
		StringBuffer query=new StringBuffer("Select * From check_result WHERE "+str);
		try {
			unit.rs=unit.st.executeQuery(query.toString());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//check_result_insert
	public static boolean Check_result_insert(int eq_id,String eq_name,String check_name,int task_id,String result_detail){
		String query=new String("Insert into check_result(eq_id,eq_name,check_name,task_id,result_detail) values(?,?,?,?,?)");
		PreUnit u=PreUnit.getUnit(query);
		if(u==null)
			return false;
		try {
			u.st.setInt(1, eq_id);
			u.st.setString(2, eq_name);
			u.st.setString(3, check_name);
			u.st.setInt(4, task_id);
			u.st.setString(5, result_detail);
			if(1==u.st.executeUpdate())
			{
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		u.close();
		return false;
	}
	//check_result_update,,,需不需要？
	//public static boolean Check_result_update(int eq_id,String eq_name,String check_name,int task_id,String result_detail){}
	
	//人事管理
	public static boolean Staff_select(String str,Unit u){
		StringBuffer query=new StringBuffer("Select * From staff WHERE "+str);
		try {
			u.rs=u.st.executeQuery(query.toString());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//人员增加
	public static boolean Staff_insert(String job,String pwd,String name,String phone,int role)
	{
		String query=new String("Insert into staff(staff_job,staff_pwd,staff_name,staff_phone,staff_role) values(?,?,?,?,?)");
		PreUnit u=PreUnit.getUnit(query);
		if(u==null)
			return false;
		try {
			u.st.setString(1, job);
			u.st.setString(2, pwd);
			u.st.setString(3, name);
			u.st.setString(4, phone);
			u.st.setInt(5,role);
			if(1==u.st.executeUpdate())
			{
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		u.close();
		return false;
	}
	//人员数据更新
	public static boolean Staff_update(String job,String pwd,String name,String phone,int role,int id){
		String query=new String("Update staff SET staff_job= ?,staff_pwd= ?,staff_name= ?,staff_phone= ?,staff_role= ?"
				+ " WHERE staff_id= ?");
		PreUnit u=PreUnit.getUnit(query);
		if(u==null)
			return false;
		try {
			u.st.setString(1, job);
			u.st.setString(2, pwd);
			u.st.setString(3, name);
			u.st.setString(4, phone);
			u.st.setInt(5,role);
			u.st.setInt(6, id);
			if(1==u.st.executeUpdate())
			{
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		u.close();
		return false;
	}

	//角色权限表
	public static boolean Role_select(String str,Unit u){
		StringBuffer query=new StringBuffer("Select * From role WHERE "+str);
		try {
			u.rs=u.st.executeQuery(query.toString());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	/*public static boolean Role_insert(String name){
		String query=new String("Insert into table(role) values(?)");
		PreUnit u=PreUnit.getUnit(query);
		if(u==null)
			return false;
		try {
			u.st.setString(1, name);
			if(1==u.st.executeUpdate())
			{
				u.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		u.close();
		return false;
		
	}
	*/
	public static String get_request(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer requestData = new StringBuffer();
		String DataString = new String();
		try{
			BufferedReader reader = request.getReader();
			while((DataString = reader.readLine()) != null){
				requestData.append(DataString);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return requestData.toString();
	}
	

}

class PreUnit{
	private Connection con=null;
	public PreparedStatement st=null;
	public void close(){
		try {
			if(st!=null)
			st.close();
			if(con!=null)
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private PreUnit(){
	}
	
	public static PreUnit getUnit(String query){
		PreUnit u=new PreUnit();
		try {
			u.con=DB_act.ds.getConnection();
			u.st=u.con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			return u;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			u.close();
			return null;
		}
	}
	
	
}
