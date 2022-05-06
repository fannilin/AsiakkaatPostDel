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
		ArrayList<Asiakas> asiakkaat;
		Dao dao = new Dao();
		String strJSON = "";
		if(pathInfo==null) {		
			asiakkaat = dao.listaaKaikki();
			strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();
		}	else if (pathInfo.indexOf("haeyksi") != -1){		
			int asiakas_id = Integer.parseInt(pathInfo.replace("/", ""));
			Asiakas asiakas = dao.etsiAsiakas(asiakas_id);
			if (asiakas == null) {
				strJSON = "{}";
			} else {
			JSONObject JSON = new JSONObject();
			JSON.put("etunimi", asiakas.getEtunimi());
			JSON.put("sukunimi", asiakas.getSukunimi());
			JSON.put("puhelin", asiakas.getPuhelin());
			JSON.put("sposti", asiakas.getSposti());
			strJSON = JSON.toString();
			}
		} else {
			String hakusana = pathInfo.replace("/", "");
			asiakkaat = dao.listaaKaikki(hakusana);
			strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();
		}
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
		JSONObject jsonObj = new JsonStrToObj().convert(request); 
		String vanhasukunimi = jsonObj.getString("vanhasukunimi");
		Asiakas asiakas = new Asiakas();
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhelin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if (dao.muutaAsiakas(asiakas, vanhasukunimi)) {
			out.println("{\"response\":1}");
		} else {
			out.println("\"response\":0}");
		}
	}
	

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Asiakkaat.doDelete()");
		String pathInfo = request.getPathInfo();	
		int asiakas_id = Integer.parseInt(pathInfo.replace("/", ""));	
		Dao dao = new Dao();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();		
		if(dao.poistaAsiakas(asiakas_id)){ //metodi palauttaa true/false
			out.println("{\"response\":1}");  //poistaminen onnistui {"response":1}
		}else{
			out.println("{\"response\":0}");  //poistaminen epäonnistui {"response":0}
		}	
	}
}
