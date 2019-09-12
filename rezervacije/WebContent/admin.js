checkLoggedUser();
readClientsToTable();
readDestinationsToTable();
readFlightsToTable();

function checkLoggedUser() {
	$.ajax({
		type : 'GET',
		url : "../rezervacije/rest/userService/checkLoggedUser",
		success : function(user) {
			if (typeof (user) == 'undefined')	 {
				window.location.href = "login.html";
			} else {
				if (user.userType == "CLIENT") {
					//alert("You don't have permission to access to this page because you're client!");
					window.location.href = "user.html";
				}else{
					$(document).ready(function(){

						$("#username").attr('value',user.username);
						$("#password1").attr('value',user.password);
						$("#password2").attr('value',user.password);
						$("#name").attr('value',user.name);
						$("#surname").attr('value',user.surname);
						$("#phone").attr('value',user.phone);
						$("#email").attr('value',user.email);
						
					});
					
				}
			}
		},
		error : function(errorThrown) {
			alert(errorThrown);
		}
	});
}

$(document).on('click',"#logout",function(e){
	e.preventDefault();
	if (confirm('Are you sure that you want to log out?')) {
		$.ajax({
			type : 'GET',
			url : "../rezervacije/rest/userService/logout",
			success : function() {
				window.location.href = "login.html";
			},
			error : function(errorThrown) {
				alert(errorThrown);
			}
		});
	}
	
})


function readClientsToTable() {
	$.ajax({
		type : 'GET',
		url : "../rezervacije/rest/userService/clients",
		cache: false,
		success : function(clients) {
			$("#clients").find("tr:not(:first)").remove();
			$.each(
					clients,
					function(index,user){			
						var tr = $('<tr></tr>');
						var block = "Block";
						
						if(user.active !== true){
							block = "Activate";
						}
						
						tr.append(
								"<td>" + user.username+"</td>" + 
								"<td>" + user.name+"</td>" +
								"<td>" + user.surname+"</td>" +
								"<td>" + user.phone+"</td>" + 
								"<td>" + user.email+"</td>" +
								"<td><button name=\"" + user.username + "\" id=\"blockUser\" class=\"btn btn-default\" background-color=\"#555555\">" + block+"</button></td>"
								
						);
						$("#clients").append(tr);			
					}
			)		
		},
		error : function(errorThrown) {
			alert(errorThrown);
		}
	});
}

$(document).on('click', '#blockUser', function(e) {
	e.preventDefault();
		var username = $(this).attr("name");
		$.ajax({
			type : 'PUT',
			url : "../rezervacije/rest/userService/blockClient/" + username,
			contentType : 'application/json',
			cache: false,
			success : function() {
				readClientsToTable();
			},
			error : function(errorThrown) {
				alert(errorThrown);
			}
		});
	

})


function readDestinationsToTable() {
	$.ajax({
		type : 'GET',
		url : "../rezervacije/rest/destinationService/getDestinations",
		cache: false,
		success : function(destinations) {
			
			if(destinations.length === 0){
				$("#titleDestinations").hide();
				$("#messageDestinationsFound").show();
				$("#dest").hide();
			}else{
				$("#titleDestinations").show();
				$("#messageDestinationsFound").hide();
				$("#dest").show();
				
				$("#dest").find("tr:not(:first)").remove();
				$.each(
						destinations,
						function(index,d){			
							var tr = $('<tr></tr>');
							var arc = "Archive";
							
							if(d.active !== true){
								arc = "Activate";
							}
							
							tr.append(
									"<td><input type=\"text\" id=\"" + d.name+"\" value=\"" + d.name + "\" readonly/></td>" + 
									"<td><input type=\"text\" id=\"country" + d.name+"\" value=\"" + d.country + "\" readonly/></td>" +
									"<td><input type=\"text\" id=\"nameOfAirport" + d.name+"\" value=\"" + d.nameOfAirport + "\" readonly required/></td>" +
									"<td><input type=\"text\" id=\"code" + d.name+"\" value=\"" + d.code + "\" readonly required/></td>" + 
									"<td><button class=\"btn btn-primary\" id=\"editDestination\" name=\"" + d.name + "\">" + "Edit" + "</button>   <button class=\"btn btn-primary\" id=\"saveDestination\" name=\"" + d.name + "\">" + "Save" + "</button></td>" + 
									"<td><button name=\"" + d.name + "\" id=\"archive\" class=\"btn btn-default\" background-color=\"#555555\">" + arc+"</button></td>"
									
							);
							$("#dest").append(tr);			
							

						}
				)		
			}
		},
		error : function(errorThrown) {
			alert(errorThrown);
		}
	});
}

$(document).on('click', '#archive', function(e) {
	e.preventDefault();
		var name = $(this).attr("name");
		
		console.log(name);
		$.ajax({
			type : 'PUT',
			url : "../rezervacije/rest/destinationService/archiveDestination/" + name,
			contentType : 'application/json',
			cache: false,
			success : function() {
				readDestinationsToTable();
			},
			error : function(errorThrown) {
				alert(errorThrown);
			}
		});
	

})


$(document).on('click', '#editDestination', function(e) {
	e.preventDefault();
		var name = $(this).attr("name");
		
		$('tr td input').prop("readonly",true);
	
		/*$('[id=' + name + ']').prop("readonly",false);
		$('[id=country' + name + ']').prop("readonly",false);*/
		$('[id=nameOfAirport' + name + ']').prop("readonly",false);
		$('[id=code' + name + ']').prop("readonly",false);
		

})

$(document).on('click', '#saveDestination', function(e) {
	e.preventDefault();
		var name = $(this).attr("name");
	
		var airportName = document.getElementById("nameOfAirport"+name).value;
		var code = document.getElementById("code"+name).value;

		$.ajax({
			type : 'PUT',
			contentType : 'application/json',
			dataType : "json",
			cache: false,
			url : "../rezervacije/rest/destinationService/editDestination",
			data : JSON.stringify({"name": name,"nameOfAirport": airportName,"code":code}),
			success : function(retVal) {
						
				if(retVal.success){
					readDestinationsToTable();
				}
						
				alert(retVal.message);
				},
				error : function(errorThrown) {
					alert(errorThrown);
				}
		});
})


$(document).on('submit', '#addFlight', function(e) {
	e.preventDefault();
		var number = document.getElementById("flightID").value;
		var start = document.getElementById("startDestination").value;
		var departure= document.getElementById("departureDestination").value;
		var price = document.getElementById("priceTicket").value;
		var airplane = document.getElementById("airplaneModel").value;
		var nFirst = document.getElementById("nFirst").value;
		var nBusiness = document.getElementById("nBusiness").value;
		var nEconomic = document.getElementById("nEconomic").value;
		var dateTime = document.getElementById("dateAndTime").value;
		var classFlight = document.getElementById("classOfFlight").value;
		
		
		var date = dateTime.split("T")[0];
		
		var today = new Date().setHours(0,0,0,0);
		var d = new Date(date).setHours(0,0,0,0);
		console.log(date);
		console.log(d);
		console.log(today);
		
		var pass = true;
		
		
		if(price<=0 || nFirst <= 0 || nBusiness <=0 || nEconomic <= 0){
			alert("Type of number must be positive integer!");
			pass = false;
		}
		
		if(d<today){
			alert("Date must be today or in the future!");
			pass = false;
		}
		
		
		if(pass){
			$.ajax({
				type : 'POST',
				url : "../rezervacije/rest/flightService/addFlight",
				contentType : 'application/json',
				dataType : "json", 
				data : JSON.stringify({"number": number, "start": start,"departure": departure,"priceTicket": price,
					"airplaneModel":airplane, "numberFirstClass":nFirst,"numberBusinessClass":nBusiness,"numberEconomicClass":nEconomic,
					"dateTime":dateTime,"classFlight":classFlight}),
				success : function(retVal) {
					if(retVal.success){
						readFlightsToTable();
					}
					alert(retVal.message);
				},
				error : function(errorThrown) {
					alert(errorThrown);
				}
			});
		}
})

function readFlightsToTable() {
	$.ajax({
		type : 'GET',
		url : "../rezervacije/rest/flightService/getFlights",
		cache: false,
		success : function(flights) {
			var flightClasses = ["CHARTER","REGIONAL","TRANSOCEANIC"];
			
			if(flights.length === 0){
				$("#titleFlights").hide();
				$("#messageFlightsFound").show();
				$("#flightsTable").hide();
			}else{
				$("#titleFlights").show();
				$("#messageFlightsFound").hide();
				$("#flightsTable").show();
				
				$("#flightsTable").find("tr:not(:first)").remove();
				$.each(
						flights,
						function(index,f){			
							var tr = $('<tr></tr>');
							var del = "Delete";
						
							var select = "<select id=\"classFlight" + f.number + "\" disabled=\"true\" ><option value=\"" + f.classFlight + "\" selected>"  +  f.classFlight + "</option>";
							for(i in flightClasses){
								
								if(flightClasses[i] === f.classFlight){
									continue;
								}
								
								select += "<option value=\"" + flightClasses[i] + "\">" + flightClasses[i] + "</option>";
							}
							
							select += "</select>";
							
							tr.append(
									"<td>" + f.number + "</td>" + 
									"<td>" + f.start + "</td>" +
									"<td>" + f.departure + "</td>" +
									"<td><input type=\"number\" size=\"1\" id=\"price" + f.number+"\" value=\"" + f.price + "\" readonly required/></td>" + 
									"<td><input type=\"text\" size=\"7\" id=\"airplane" + f.number+"\" value=\"" + f.airplane + "\" readonly required/></td>" + 
									"<td><input type=\"number\" size=\"1\" id=\"numberFirst" + f.number+"\" value=\"" + f.numberFirst + "\" readonly required/></td>" + 
									"<td><input type=\"number\" size=\"1\" id=\"numberBussiness" + f.number+"\" value=\"" + f.numberBussiness + "\" readonly required/></td>" + 
									"<td><input type=\"number\" size=\"1\" id=\"numberEconomic" + f.number+"\" value=\"" + f.numberEconomic + "\" readonly required/></td>" + 
									"<td><input type=\"datetime-local\" size=\"14\" id=\"dateTime" + f.number+"\" value=\"" + f.dateTime + "\" readonly required/></td>" + 
									//"<td><input type=\"text\" size=\"13\" id=\"classFlight" + f.number+"\" value=\"" + f.classFlight + "\" readonly required/></td>" + 
									"<td>" + select + "</td>" + 
									"<td><button class=\"btn btn-primary\" id=\"editFlight\" name=\"" + f.number + "\">" + "Edit" + "</button>   <button class=\"btn btn-primary\" id=\"saveFlight\" name=\"" + f.number + "\">" + "Save" + "</button></td>" + 
									"<td><button name=\"" + f.number + "\" id=\"delete\" class=\"btn btn-warning\" background-color=\"#555555\">" + del+"</button></td>"
									
							);
							$("#flightsTable").append(tr);			
							

						}
				)		
			}
		},
		error : function(errorThrown) {
			alert(errorThrown);
		}
	});
}


$(document).on('click', '#editFlight', function(e) {
	e.preventDefault();
		var number = $(this).attr("name");
		$('tr td input').prop("readonly",true);
		$('tr td select').prop("disabled",true);
	
		$('[id=price' + number + ']').prop("readonly",false);
		$('[id=airplane' + number + ']').prop("readonly",false);
		$('[id=numberFirst' + number + ']').prop("readonly",false);
		$('[id=numberBussiness' + number + ']').prop("readonly",false);
		$('[id=numberEconomic' + number + ']').prop("readonly",false);
		$('[id=dateTime' + number + ']').prop("readonly",false);
		//$('[id=classFlight' + number + ']').prop("readonly",false);
		$('[id=classFlight' + number + ']').prop("disabled",false);
		

})


$(document).on('click', '#saveFlight', function(e) {
	e.preventDefault();
		var number = $(this).attr("name");
		
	
		var price = document.getElementById("price" + number).value;
		var airplane = document.getElementById("airplane" + number).value;
		var nFirst = document.getElementById("numberFirst" + number).value;
		var nBusiness = document.getElementById("numberBussiness" + number).value;
		var nEconomic = document.getElementById("numberEconomic" + number).value;
		var dateTime = document.getElementById("dateTime" + number).value;
		var classFlight = document.getElementById("classFlight" + number).value;
		
		
		var date = dateTime.split("T")[0];
		
		var today = new Date().setHours(0,0,0,0);
		var d = new Date(date).setHours(0,0,0,0);
		var pass = true;
		
		if(price<=0 || nFirst <= 0 || nBusiness <=0 || nEconomic <= 0){
			alert("Type of number must be positive integer!");
			pass = false;
		}
		
		if(d<today){
			alert("Date must be today or in the future!");
			pass = false;
		}
		
		if(pass){
			$.ajax({
				type : 'PUT',
				contentType : 'application/json',
				dataType : "json",
				cache: false,
				url : "../rezervacije/rest/flightService/editFlight",
				data : JSON.stringify({"number": number,"priceTicket": price,
					"airplaneModel":airplane, "numberFirstClass":nFirst,"numberBusinessClass":nBusiness,"numberEconomicClass":nEconomic,
					"dateTime":dateTime,"classFlight":classFlight}),
				success : function(retVal) {
							
					if(retVal.success){
						readFlightsToTable();
					}
							
					alert(retVal.message);
					},
					error : function(errorThrown) {
						alert(errorThrown);
					}
			});
		}
		
})



$(document).on('click', '#delete', function(e) {
	e.preventDefault();
		var number = $(this).attr("name");
		$.ajax({
			type : 'DELETE',
			url : "../rezervacije/rest/flightService/deleteFlight/" + number,
			contentType : 'application/json',
			cache: false,
			success : function(retVal) {
				if(retVal.success === false){
					alert(retVal.message);
				}else{
					readFlightsToTable();
				}
			},
			error : function(errorThrown) {
				alert(errorThrown);
			}
		});
	

})





$(document).on("submit", "#editUser", function(e){
	e.preventDefault();
	var username = document.getElementById("username").value;
	var password1 = document.getElementById("password1").value;
	var password2 = document.getElementById("password2").value;
	var name = document.getElementById("name").value;
	var surname = document.getElementById("surname").value;
	var phone = document.getElementById("phone").value;
	var email = document.getElementById("email").value;
	
	var pass = true;
	
	if(username === "" || password1 === "" || password2 === "" || name === "" || surname === "" || phone === "" || email === ""){
		alert("You must fill all fields!");
		pass = false;
	}
	
	if(password1 !== password2){
		alert("Passwords don't match!");
		pass = false;
	}
	
	if(pass){
		$.ajax({
			type : 'PUT',
			contentType : 'application/json',
			dataType : "json",
			cache: false,
			url : "../rezervacije/rest/userService/editUser",
			data : JSON.stringify({"username": username,"password": password1,"name":name,"surname":surname,
				"phone":phone,"email":email,"admin": true
			}),
			success : function(retVal) {
				alert(retVal.message);
			},
			error : function(errorThrown) {
				alert(errorThrown);
			}
		});
	}
})


$(document).on('submit', '#addDestination', function(e) {
	e.preventDefault();
		var name = document.getElementById("nameDestination").value;
		var country = document.getElementById("country").value;
		var nameOfAirport = document.getElementById("airport").value;
		var code = document.getElementById("code").value;
		
		$.ajax({
			type : 'POST',
			url : "../rezervacije/rest/destinationService/addDestination",
			contentType : 'application/json',
			dataType : "json", 
			data : JSON.stringify({"name": name, "country": country,"nameOfAirport": nameOfAirport,"code": code}),
			success : function(retVal) {
				if(retVal.success){
					readDestinationsToTable();
				}
				alert(retVal.message);
			},
			error : function(errorThrown) {
				alert(errorThrown);
			}
		});
})

$(document).ready(function(){
	$(document).on("submit","#adminSearch",function(e){
		e.preventDefault();
		var number = document.getElementById("numFlight").value;
		var start = document.getElementById("begin").value;
		var departure = document.getElementById("end").value;
		var country = document.getElementById("countryFlight").value;
		var date = document.getElementById("flightDate").value;
		var flightClass = document.getElementById("flightClass").value;
		

		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			dataType : "json",
			cache: false,
			url : "../rezervacije/rest/flightService/adminFlightSearch",
			data: JSON.stringify({"number": number,"begin": start,"end": departure,"country": country,"date": date,"classFlight":flightClass}),
			success : function(flights) {
						
				$("#flightsForAdminView").find("tr:not(:first)").remove();
						
						
					if(flights.length === 0){
						$("#flightsForAdminView").hide();
						$("#messageFlightsForAdminSearchFound").show();
					}else{
						$("#messageFlightsForAdminSearchFound").hide();
						$("#flightsForAdminView").show();
						$.each(flights, function(index,flight){
							var tr = $("<tr></tr>");
									
							tr.append(
									"<td>" + flight.number+"</td>" + 
									"<td>" + flight.start+"</td>" +
									"<td>" + flight.departure+"</td>" +
									"<td>" + flight.price+"</td>" + 
									"<td>" + flight.airplane+"</td>" +
									"<td>" + flight.date+"</td>" +
									"<td>" + flight.flightClass+"</td>"
							);
							$("#flightsForAdminView").append(tr);	
						})
					}
			},
			error : function(errorThrown) {
				alert(errorThrown);
			}
		});
	
	})
})




