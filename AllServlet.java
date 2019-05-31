package coldBackStage;

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

@WebServlet("/allServlet")
public class AllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deceive_location=request.getParameter("deceive_location");
		String product_no=request.getParameter("product_no");
		String deceive_locationSelect=request.getParameter("deceive_locationSelect");
		String deceive_name=request.getParameter("deceive_name");
		String procedure=request.getParameter("procedure");
		String input_time=request.getParameter("input_time");
		String system_time=request.getParameter("system_time");
		String user_name=request.getParameter("user_name");
		String status=request.getParameter("status");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String flag=request.getParameter("flag");
		JSONArray jsonarray=null;
		if(flag.equals("one")){
			try {
				jsonarray=getStatus(deceive_location);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(flag.equals("eight")){
			try {
				jsonarray=getDeceiveName(deceive_location);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(flag.equals("two")){
			try {
				jsonarray=preInsertAllDevice(product_no,deceive_locationSelect,deceive_name,procedure,input_time,user_name,status,system_time);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(flag.equals("five")){
			try {
				jsonarray=getProductNo();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(flag.equals("four")){
			try {
				jsonarray=queryList(user_name,startDate,endDate,deceive_location,deceive_name,status);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(flag.equals("nine")){
			try {
				jsonarray=queryDetail(deceive_location,deceive_name);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(flag.equals("seven")){
			try {
				jsonarray=end(deceive_location,deceive_name,status);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(flag.equals("six")){
			try {
				jsonarray=update(product_no,deceive_location,deceive_name,procedure,input_time,status);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
//				jsonarray=insertDepart(user_code,own_depart,record_date,content,connect_date,out_depart,connect_way,connect_nature);
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
	private JSONArray end(String deceive_location, String deceive_name, String status) throws Exception {
		String sql="update middle_table_read_app set status='"+status
				+"' where deceive_location='"+deceive_location
				+"' and deceive_name='"+deceive_name+"'";
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
	private JSONArray update(String product_no, String deceive_location, String deceive_name, String procedure,
			String input_time, String status) throws Exception {
		String sql="update middle_table_read_app set product_no='"+product_no
					+"',input_time='"+input_time
					+"',procedure='"+procedure
					+"',status='"+status
					+"' where deceive_location='"+deceive_location
					+"' and deceive_name='"+deceive_name+"'";
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
	private JSONArray queryDetail(String deceive_location, String deceive_name) throws Exception {
		String sql="select * from middle_table_read_app where  deceive_location='"+deceive_location
				+"'and deceive_name='"+deceive_name+"'";
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
		jsonobj.put("status",result.getString("status"));
		jsonobj.put("product_no",result.getString("product_no"));
		jsonobj.put("procedure",result.getString("procedure"));
		jsonobj.put("deceive_name",result.getString("deceive_name"));
		jsonobj.put("input_time",result.getString("input_time"));
		jsonobj.put("user_name",result.getString("user_name"));
		jsonobj.put("deceive_location",result.getString("deceive_location"));
		jsonarray.add(jsonobj);
	}
	result.close();
	state.close();
	connect.close();
	return jsonarray;
	}
	private JSONArray queryList(String user_name,String startDate,String endDate,String deceive_location,String deceive_name,String status) throws Exception{
		String sql="select * from middle_table_read_app where user_name='"
					+user_name+"'and system_time>='"+startDate
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
	private JSONArray getProductNo() throws Exception {
		String sql="select distinct product_no from middle_table_read_app ";
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
			jsonarray.add(jsonobj);
		}
		result.close();
		state.close();
		connect.close();
		return jsonarray;
	}
	private JSONArray preInsertAllDevice(String product_no, String deceive_locationSelect, String deceive_name,
			String procedure, String input_time, String user_name,String status,String system_time) throws Exception {
		String presql="select status,rownum as zongshu from middle_table_read_app where  deceive_location='"+deceive_locationSelect
					+"'and deceive_name='"+deceive_name
					+"'and system_time = (select max(system_time) from MIDDLE_TABLE_READ_APP)";
		java.sql.Connection connect =null;
		java.sql.Statement state=null;
		ResultSet result=null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connect=DriverManager.getConnection("jdbc:oracle:thin:@10.40.121.161:1521:orcl", "zc_infor", "zc_infor");
		state=connect.createStatement();
		result=state.executeQuery(presql);
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		
		if(result.next()==true) {
			if(result.getString("status")=="结束"){
				jsonarray=insertAllDevice(product_no, deceive_locationSelect, deceive_name, procedure, input_time, user_name,status,system_time);
			}else{
				jsonobj.put("msgCode",3);
				jsonarray.add(jsonobj);
			}
		}
		else {
			jsonarray=insertAllDevice(product_no, deceive_locationSelect, deceive_name, procedure, input_time, user_name,status,system_time);
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
	private JSONArray getStatus(String deceive_location) throws Exception{
		String sql="select deceive_location,deceive, system_time,deceive_name,deceive_statu,dian_zhu_gui,dian_li_gui,heating_temp,return_temp,temp_1,temp_6 from middle_table_statu_app where deceive_location='"
					+deceive_location+"'";
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
			jsonobj.put("deceive_location", result.getString("deceive_location"));
			jsonobj.put("deceive", result.getString("deceive"));
			jsonobj.put("system_time", result.getString("system_time"));
//			jsonobj.put("deceive_location", result.getString("deceive_location"));
			jsonobj.put("deceive_name", result.getString("deceive_name"));
			jsonobj.put("deceive_statu", result.getString("deceive_statu"));
			jsonobj.put("dian_zhu_gui", result.getString("dian_zhu_gui"));
			jsonobj.put("dian_li_gui", result.getString("dian_li_gui"));
			jsonobj.put("heating_temp", result.getString("heating_temp"));
			jsonobj.put("return_temp", result.getString("return_temp"));
			jsonobj.put("temp_1", result.getString("temp_1"));
			jsonobj.put("temp_6", result.getString("temp_6"));
			jsonarray.add(jsonobj);
		}
		result.close();
		state.close();
		connect.close();
		return jsonarray;
	}
	private JSONArray getDeceiveName(String deceive_location) throws Exception{
		String sql="select deceive_name from check_list where deceive_location='"
					+deceive_location+"'";
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
			jsonobj.put("deceive_name", result.getString("deceive_name"));
			jsonarray.add(jsonobj);
		}
		result.close();
		state.close();
		connect.close();
		return jsonarray;
	}

}
