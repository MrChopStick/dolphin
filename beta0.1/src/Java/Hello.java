package Java;
import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class Hello
 */
@WebServlet("/Hello")
public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath()); 
		/*String a="test";
		String b="[1,2,3]";
		Data_operator.Equip_type_insert(a, b);
		*/
		/*
		Unit u=Unit.getUnit();
		if(u!=null)
		{
			
			try {
				Data_operator.Equip_type_select("1=1",u);
				while(u.rs.next())
				{
					System.out.println(u.rs.getString(3));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				u.close();
			}
		}
		*/
		//Data_operator.Repair_insert(1, "umbrella", 0);
		//Data_operator.Repair_update(2, "m_got_time", "1996-10-30 11:11:11");
		//Data_operator.Equip_insert("2", 0, 3, 2, 0);
		/*
		Data_operator.Repair_insert(112, "kaiwanxiao", 0);
		Data_operator.Equip_list_insert("shebei", 1, 2, 1, 4);
		Data_operator.Equip_type_insert("as", "[1,2,3]");
		Data_operator.Equip_group_insert(213123);
		Date da=Date.valueOf("1998-10-22");
		Time t1=Time.valueOf("12:22:31");
		Time t2=Time.valueOf("11:43:22");
		Data_operator.Check_plan_insert("as", 24, da, t1, t1, 123, 321, 123, "666");
		Data_operator.Check_plan_detail_insert(11, 213, 666);
		Data_operator.Check_list_insert(12, "gg", "asd", 22);
		Data_operator.Check_result_insert(123, "asd", "zx", 123, "[\"kk\"]");
		Data_operator.Staff_insert("jj", "123", "asd", "11111111111", 3);
		*/
		Timestamp t=Timestamp.valueOf(LocalDateTime.now());
		Data_operator.Repair_update(3, Data_operator.FINISH_TIME, t);
		Data_operator.Equip_list_update("ASD", 21, 12, 4, 213, 1);
		Data_operator.Equip_type_update("asd", "[1, 4, 6]", 1);
		Date da=Date.valueOf(LocalDate.now());
		Time t1=Time.valueOf(LocalTime.now());
		Time t2=Time.valueOf(LocalTime.now());
		Data_operator.Check_plan_Update("123", 2, da, t1, t2, 44, 33, 2, "a", 1);
		Data_operator.Check_plan_detail_update(2, 1, 3, 1);
		Data_operator.Check_list_update(3,"45","sad",1,1);
		Data_operator.Staff_update("oo", "as", "zxc", "12222111111", 5, 100002);
		Unit u=Unit.getUnit();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
