package coldBackStage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@WebServlet("/four")
public class Four extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deceive_location=request.getParameter("deceive_location");
		String deceive_name=request.getParameter("deceive_name");
		String user_name=request.getParameter("user_name");
		String status=request.getParameter("status");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String flag=request.getParameter("flag");
		JSONArray jsonarray=null;
		if(flag.equals("four")){
			try {
				jsonarray=queryList(user_name,startDate,endDate,deceive_location,deceive_name,status);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		response.setContentType("text/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonarray);
	}
	private JSONArray queryList(String user_name,String startDate,String endDate,String deceive_location,String deceive_name,String status) throws Exception{
		if(status.equals("全部")){
			String sql="select * from middle_table_read_app where system_time>='"+startDate
					+"'and system_time<='"+endDate
					+"'and deceive_location='"+deceive_location+"'";
			java.sql.Connection connect =null;
			java.sql.Statement state=null;
			ResultSet result=null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connect=DriverManager.getConnection("jdbc:oracle:thin:@10.40.121.161:1521:orcl", "zc_infor", "zc_infor");
			state=connect.createStatement();
			result=state.executeQuery(sql);
			JSONArray jsonarray = new JSONArray();
			JSONObject jsonobj = new JSONObject();
			while (result.next()) {
				jsonobj.put("product_no",result.getString("product_no"));
				jsonobj.put("status",result.getString("status"));
				jsonobj.put("system_time",result.getString("system_time"));
				jsonobj.put("deceive_name",result.getString("deceive_name"));
				jsonobj.put("deceive_location",result.getString("deceive_location"));
				jsonarray.add(jsonobj);
			}
			result.close();
			state.close();
			connect.close();
			return jsonarray;
		}else{
			String sql="select * from middle_table_read_app where system_time>='"+startDate
					+"'and system_time<='"+endDate
					+"'and deceive_location='"+deceive_location
					+"'and status = '"+status+"'";
			java.sql.Connection connect =null;
			java.sql.Statement state=null;
			ResultSet result=null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connect=DriverManager.getConnection("jdbc:oracle:thin:@10.40.121.161:1521:orcl", "zc_infor", "zc_infor");
			state=connect.createStatement();
			result=state.executeQuery(sql);
			JSONArray jsonarray = new JSONArray();
			JSONObject jsonobj = new JSONObject();
			while (result.next()) {
				jsonobj.put("product_no",result.getString("product_no"));
				jsonobj.put("status",result.getString("status"));
				jsonobj.put("system_time",result.getString("system_time"));
				jsonobj.put("deceive_name",result.getString("deceive_name"));
				jsonobj.put("deceive_location",result.getString("deceive_location"));
				jsonarray.add(jsonobj);
			}
			result.close();
			state.close();
			connect.close();
			return jsonarray;
		}
		
	
	}
}
