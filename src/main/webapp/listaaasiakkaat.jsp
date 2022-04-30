 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="main.css">
<title>Asiakkaiden listaus</title>
</head>
<body>
	<table id="listaus">
		<thead>
		<tr>
		<th 	colspan= "5" class ="oikealle"><span id = "uusiAsiakas">Lisää uusi asiakas</span></th>
			<tr>
				<th colspan="3" class="oikealle">Hakusana:</th>
				<th><input type="text" id="hakusana"></th>
				<th><input type="button" id="hae" value="Hae"></th>
			</tr>		
			<tr>
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Puhelin</th>
				<th>Sposti</th>	
				<th></th>			
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<span id="ilmo"></span>
<script>
$(document).ready(function(){	
	$(document.body).on("keydown", function(event){
		  if(event.which==13){ //Enter painettu, ajetaan haku
			  haeTiedot();
		  }
	});	
	$("#uusiAsiakas").click(function(){
		document.location="lisaaasiakas.jsp";
	});
	$("#hae").click(function(){	
		haeTiedot();
	});
	$("#hakusana").focus();
	haeTiedot();
});
function haeTiedot(){	
	$("#listaus tbody").empty();
	$.ajax({url:"asiakkaat/"+$("#hakusana").val(), type:"GET", dataType:"json", success:function(result){
		$.each(result.asiakkaat, function(i, field){  
        	var htmlStr;
        	htmlStr+="<tr>"; 
        	htmlStr+="<td>"+field.etunimi+"</td>";
        	htmlStr+="<td>"+field.sukunimi+"</td>";
        	htmlStr+="<td>"+field.puhelin+"</td>";
        	htmlStr+="<td>"+field.sposti+"</td>";  
        	htmlStr+="<td><span class='poista' onclick=poista('"+ field.asiakas_id+"')>"Poista"</span></td>";   
        	htmlStr+="</tr>";
        	$("#listaus tbody").append(htmlStr);
        });
    }});	
}
	function poista(asiakas_id){
	if(confirm("Poista asiakas " + etunimi +"?")){
		$.ajax({url:"asiakkaat/"+asiakas_id, type:"DELETE", dataType:"json", success:function(result) { //result on joko {"response:1"} tai {"response:0"}
	        if(result.response==0){
	        	$("#ilmo").html("Asiakkaan poisto epäonnistui.");
	        }else if(result.response==1){
	        	$("#rivi_"+asiakas_id).css("background-color", "orange"); 
	        	alert("Asiakkaan " + asiakas_id +" poisto onnistui.");
				haeAsiakkaat();        	
			}
	    }});
	}
}
	
</script>
</body>
</html>
    