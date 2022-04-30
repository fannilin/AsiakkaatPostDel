package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import model.Asiakas;
import model.dao.Dao;

/**
 * Servlet implementation class Asiakkaat
 */
@WebServlet("/asiakkaat/*")
public class Asiakkaat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Asiakkaat() {
        super();
        System.out.println("Asiakkaat.Asiakkaat()");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Asiakkaat.doGet()");
		String pathInfo = request.getPathInfo();	//haetaan kutsun polkutiedot, esim. /aalto			
		System.out.println("polku: "+pathInfo);	
		String hakusana="";
		if(pathInfo!=null) {		
			hakusana = pathInfo.replace("/", "");
		}		
		Dao dao = new Dao();
		ArrayList<Asiakas> asiakkaat = dao.listaaKaikki(hakusana);
		System.out.println(asiakkaat);
		String strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();	
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(strJSON);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Asiakkaat.doPost()");
		JSONObject jsonObj = new JsonStrToObj().convert(request); 
		Asiakas asiakas = new Asiakas();
		asiakas.setAsiakas_id(0);
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhelin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if (dao.lisaaAsiakas(asiakas)) {
			out.println("{\"response\":1}");
		} else {
			out.println("\"response\":0}");
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Asiakkaat.doPut()");
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Asiakkaat.doDelete()");
		String pathInfo = request.getPathInfo();	//haetaan kutsun polkutiedot, esim. /ABC-222		
		System.out.println("polku: "+pathInfo);
		String delasiakas_id = pathInfo.replace("/", "");		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();			
		if(dao.poistaAsiakas(delasiakas_id)){ //metodi palauttaa true/false
			out.println("{\"response\":1}");  //Auton poistaminen onnistui {"response":1}
		}else{
			out.println("{\"response\":0}");  //Auton poistaminen epäonnistui {"response":0}
		}	
	}
}
