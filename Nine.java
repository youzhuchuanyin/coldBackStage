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
@WebServlet("/nine")
public class Nine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deceive_location=request.getParameter("deceive_location");
		String deceive_name=request.getParameter("deceive_name");
		String flag=request.getParameter("flag");
		JSONArray jsonarray=null;
		if(flag.equals("nine")){
			try {
				jsonarray=queryDetail(deceive_location,deceive_name);
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
		jsonobj.put("system_time",result.getString("system_time"));
		jsonarray.add(jsonobj);
	}
	result.close();
	state.close();
	connect.close();
	return jsonarray;
	}
}
