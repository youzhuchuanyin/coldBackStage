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
@WebServlet("/two")
public class Two extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String product_no=request.getParameter("product_no");
		String deceive_locationSelect=request.getParameter("deceive_locationSelect");
		String deceive_name=request.getParameter("deceive_name");
		String procedure=request.getParameter("procedure");
		String input_time=request.getParameter("input_time");
		String system_time=request.getParameter("system_time");
		String user_name=request.getParameter("user_name");
		String status=request.getParameter("status");
		String flag=request.getParameter("flag");
		JSONArray jsonarray=null;
		if(flag.equals("two")){
			try {
				jsonarray=preInsertAllDevice(product_no,deceive_locationSelect,deceive_name,procedure,input_time,user_name,status,system_time);
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
	private JSONArray preInsertAllDevice(String product_no, String deceive_locationSelect, String deceive_name,
			String procedure, String input_time, String user_name,String status,String system_time) throws Exception {
		String presql="select status,rownum as zongshu from middle_table_read_app where  deceive_location='"+deceive_locationSelect
					+"'and deceive_name='"+deceive_name+"'";
		System.out.println(presql);
		java.sql.Connection connect =null;
		java.sql.Statement state=null;
		ResultSet result=null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connect=DriverManager.getConnection("jdbc:oracle:thin:@10.40.121.161:1521:orcl", "zc_infor", "zc_infor");
		state=connect.createStatement();
		result=state.executeQuery(presql);
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		while (result.next()) {
			String statusa=result.getString("status");
			if(statusa.equals("结束")){
				jsonarray=insertAllDevice(product_no, deceive_locationSelect, deceive_name, procedure, input_time, user_name,status,system_time);
			}else{
				jsonobj.put("msgCode",3);
				jsonarray.add(jsonobj);
			}
		}
		result.close();
		state.close();
		connect.close();
		return jsonarray;
	}
	private JSONArray insertAllDevice(String product_no, String deceive_locationSelect, String deceive_name,
			String procedure, String input_time, String user_name,String status,String system_time) throws Exception {
		String sql="update middle_table_read_app set product_no='"+
				product_no+"',procedure='"+
				procedure+"',input_time='"+
				input_time+"',user_name='"+
				user_name+"',status='"+
				status+"',system_time='"+system_time+"'where deceive_location='"+
				deceive_locationSelect+"'and deceive_name='"+deceive_name+"'";
	java.sql.Connection connect =null;
	java.sql.Statement state=null;
	int rs=0;
	Class.forName("oracle.jdbc.driver.OracleDriver");
	connect=DriverManager.getConnection("jdbc:oracle:thin:@10.40.121.161:1521:orcl", "zc_infor", "zc_infor");
	state=connect.createStatement();
	rs=state.executeUpdate(sql);
	JSONArray jsonarray = new JSONArray();
	JSONObject jsonobj = new JSONObject();
	if (rs>0) {
		jsonobj.put("msgCode",rs);
		jsonarray.add(jsonobj);
	}else {
		jsonobj.put("msgCode",rs);
		jsonarray.add(jsonobj);
	}
	state.close();
	connect.close();
	return jsonarray;
	}
}
