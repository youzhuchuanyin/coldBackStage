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
@WebServlet("/six")
public class Six extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deceive_location=request.getParameter("deceive_location");
		String product_no=request.getParameter("product_no");
		String deceive_name=request.getParameter("deceive_name");
		String procedure=request.getParameter("procedure");
		String input_time=request.getParameter("input_time");
		String status=request.getParameter("status");
		String flag=request.getParameter("flag");
		JSONArray jsonarray=null;
		if(flag.equals("six")){
			try {
				jsonarray=update(product_no,deceive_location,deceive_name,procedure,input_time,status);
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
}
