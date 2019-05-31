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
@WebServlet("/eight")
public class Eight extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deceive_location=request.getParameter("deceive_location");
		String flag=request.getParameter("flag");
		JSONArray jsonarray=null;
		if(flag.equals("eight")){
			try {
				jsonarray=getDeceiveName(deceive_location);
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
