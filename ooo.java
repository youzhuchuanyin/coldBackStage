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
@WebServlet("/ooo")
public class ooo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		String sql="select lot_no from qc_item_seq where qc_status=1";
		System.out.println(sql);
		java.sql.Connection connect =null;
		java.sql.Statement state=null;
		ResultSet result=null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connect=DriverManager.getConnection("jdbc:oracle:thin:@10.40.123.24:1521:WHLZERPDB", "whlzrs10", "rs10_whlz");
		state=connect.createStatement();
		result=state.executeQuery(sql);
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		while (result.next()) {
			
			String lot_no= result.getString("lot_no");
			System.out.println(lot_no);
	
		}
		result.close();
		state.close();
		connect.close();
		return jsonarray;
	}

}
