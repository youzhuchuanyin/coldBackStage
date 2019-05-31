package coldBackStage;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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

import net.sf.json.JSONObject;
@WebServlet("/one")
public class One extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deceive_location=request.getParameter("deceive_location");
		String flag=request.getParameter("flag");
		JSONArray jsonarray=null;
		if(flag.equals("one")){
			try {
				jsonarray=getStatus(deceive_location);
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
	private JSONArray getStatus(String deceive_location) throws Exception{
//		String sql="select deceive_location,deceive, system_time,deceive_name,deceive_statu,dian_zhu_gui,dian_li_gui,heating_temp,return_temp,temp_1,temp_6 from middle_table_statu_app where deceive_location='"
//					+deceive_location+"'";
		String sql="select deceive_location,deceive, system_time,deceive_name,deceive_statu,dian_zhu_gui,dian_li_gui,heating_temp,return_temp,temp_1,temp_6 from middle_table_statu_app where deceive_location='"
				+deceive_location+"'order by deceive desc,deceive_name";
		System.out.println(sql);
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
			String dian_zhu_gui= result.getString("dian_zhu_gui");
			if(dian_zhu_gui==null){
				jsonobj.put("dian_zhu_gui","-");
			}else{
				jsonobj.put("dian_zhu_gui",dian_zhu_gui);
			}
			String dian_li_gui= result.getString("dian_li_gui");
			if(dian_li_gui==null){
				jsonobj.put("dian_li_gui","-");
			}else{
				jsonobj.put("dian_li_gui",dian_li_gui);
			}
			String heating_temp= result.getString("heating_temp");
			if(heating_temp==null){
				jsonobj.put("heating_temp","-");
			}else{
				jsonobj.put("heating_temp",heating_temp);
			}
			String return_temp= result.getString("return_temp");
			if(return_temp==null){
				jsonobj.put("return_temp","-");
			}else{
				jsonobj.put("return_temp",return_temp);
			}
			String temp_1= result.getString("temp_1");
			if(temp_1==null){
				jsonobj.put("temp_1","-");
			}else{
				jsonobj.put("temp_1",temp_1);
			}
			String temp_6= result.getString("temp_6");
			if(temp_6==null){
				jsonobj.put("temp_6","-");
			}else{
				jsonobj.put("temp_6",temp_6);
			}
			jsonarray.add(jsonobj);
		}
		result.close();
		state.close();
		connect.close();
		return jsonarray;
	}

}
