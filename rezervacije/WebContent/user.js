checkLoggedUser();




/*function readData() {
	$.ajax({
		type : 'GET',
		url : "../rezervacije/rest/userService/readData",
		success : function() {
			console.log("Data was successfully loaded!");
		},
		error : function(errorThrown) {
			alert(errorThrown);
		}
	});
}*/



function checkLoggedUser() {
	$.ajax({
		type : 'GET',
		url : "../rezervacije/rest/userService/checkLoggedUser",
		success : function(user) {
			if (typeof (user) == 'undefined') {
				window.location.href = "login.html";
			} else {
				if (user.userType == "ADMIN") {
					//alert("You don't have permission to access to this page because you're admin!")
					window.location.href = "admin.html";
				}else{
					$(document).ready(function(){

						$("#username").attr('value',user.username);
						$("#password1").attr('value',user.password);
						$("#password2").attr('value',user.password);
						$("#name").attr('value',user.name);
						$("#surname").attr('value',user.surname);
						$("#phone").attr('value',user.phone);
						$("#email").attr('value',user.email);
						
						readReservations();
						readFlightsIDs();
						filterFlights();
						
					});
				}
			}
		},
		error : function(errorThrown) {
			alert(errorThrown);
		}
	});
}

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
				"phone":phone,"email":email,"admin": false
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


$(document).on("submit","#searchFlightsForReservation",function(e){
	e.preventDefault();
	var start = document.getElementById("start").value;
	var departure = document.getElementById("departure").value;
	var date = document.getElementById("dateOfFlight").value;
	
	var today = new Date().setHours(0,0,0,0);
	var d = new Date(date).setHours(0,0,0,0);
	
	
	if(d<today){
		alert("Date must be today or in the future!");
	}else{
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			dataType : "json",
			cache: false,
			url : "../rezervacije/rest/flightService/searchFlightsForReservation",
			data: JSON.stringify({"start": start,"departure": departure, "date": date}),
			success : function(flights) {
				
				$("#flightsForReservation").find("tr:not(:first)").remove();
				
				
					if(flights.length === 0){
						$("#flightsForReservation").hide();
						$("#reservationOfFlight").hide();
						$("#messageFlightsFound").show();
					}else{
						$("#messageFlightsFound").hide();
						
						$("#flightsForReservation").show();
						$("#reservationOfFlight").show();
						$.each(flights, function(index,flight){
							var tr = $("<tr></tr>");
							
							tr.append(
									"<td>" + flight.number+"</td>" + 
									"<td>" + flight.price+"</td>" +
									"<td>" + flight.airplane+"</td>" +
									"<td>" + flight.fc+"</td>" + 
									"<td>" + flight.bi+"</td>" +
									"<td>" + flight.ec+"</td>" +
									"<td>" + flight.classOfFlight+"</td>" +
									"<td><input type=\"radio\" name=\"chosenFlight\" value=\"" + flight.number + "\"/></td>"
									
							);
							$("#flightsForReservation").append(tr);	
						})
					}
				
				
				
				
			},
			error : function(errorThrown) {
				alert(errorThrown);
			}
		});
	}
	
	
	
	
})


$(document).on("submit","#reservationFlight",function(e){
	e.preventDefault();
	var flightNumber = $('input[name=chosenFlight]:checked').val();
	var classType = document.getElementById("classType").value;
	var passengersNumber = document.getElementById("passengersNumber").value;
	
	if(flightNumber == null){
		alert("You must select flight!");
		return;
	}
	

	if(passengersNumber <= 0){
		alert("Passengers number must be positive integer!");
	}else{
		$.ajax({
			type : 'POST',
			contentType : 'application/json',
			dataType : "json",
			cache: false,
			url : "../rezervacije/rest/reservationService/bookFlight",
			data: JSON.stringify({"flightNumber":flightNumber,"classType":classType,"numberOfPassengers":passengersNumber}),
			success : function(retVal) {
				
				alert(retVal.message);
				if(retVal.success === true){
					readReservations();
				}
				
				
			},
			error : function(errorThrown) {
				alert(errorThrown);
			}
		});
	}
})

function readReservations() {
	$.ajax({
		type : 'GET',
		url : "../rezervacije/rest/reservationService/getReservations",
		dataType : "json",
		cache: false,
		success : function(reservations) {
			$("#reservations").find("tr:not(:first)").remove();
			
			if(reservations.length === 0){
				$("#reservations").hide();
				$("#messageReservationsFound").show();
			}else{
				$("#reservations").show();
				$("#messageReservationsFound").hide();
				
				$.each(
						reservations,
						function(index,r){			
							var tr = $('<tr></tr>');
							tr.append(
									"<td>" + r.number+"</td>" + 
									"<td>" + r.flightNumber+"</td>" + 
									"<td>" + r.localDateTime+"</td>" +
									"<td>" + r.classType+"</td>" +
									"<td>" + r.numberOfPassengers+"</td>" + 
									"<td><button name=\"" + r.number + " " + r.flightNumber + "\" id=\"cancel\" class=\"btn btn-warning\" background-color=\"#555555\">Cancel</button></td>"
							);
							$("#reservations").append(tr);			
						}
				)		
			}
		},	
		error : function(errorThrown) {
			alert(errorThrown);
		}
	});
}

function readFlightsIDs() {

	$.ajax({
		type : 'GET',
		url : "../rezervacije/rest/flightService/getIDsOfFlights",
		dataType : "json",
		cache: false,
		success : function(IDs) {
			
			if(IDs.length !== 0){
				$.each(IDs,function(index,id){			
					$("#flightIDs").append("<option value=\"" + id + "\">" + id + "</option>");
						
				})
			}
			
		},	
		error : function(errorThrown) {
			alert(errorThrown);
		}
	});
}


function filterFlights(){
	var id = document.getElementById("flightIDs").value;
	var date = document.getElementById("dateFlightFilter").value;
	var flightClass = document.getElementById("flightClass").value;
	
	$.ajax({
		type : 'POST',
		url : "../rezervacije/rest/flightService/filterFlights",
		contentType : 'application/json',
		dataType : "json",
		data: JSON.stringify({"number":id,"date":date,"classFlight":flightClass}),
		cache: false,
		success : function(flights) {
			
			$("#flightsForFilterView").find("tr:not(:first)").remove();
			
			if(flights.length === 0){
				$("#messageFlightsForFilterFound").show();
			}else{
				$("#messageFlightsForFilterFound").hide();
				
				$.each(
						flights,
						function(index,f){			
							var tr = $('<tr></tr>');
							tr.append(
									"<td>" + f.number+"</td>" + 
									"<td>" + f.start+"</td>" +
									"<td>" + f.departure+"</td>" +
									"<td>" + f.price+"</td>" + 
									"<td>" + f.airplane+"</td>" +
									"<td>" + f.date+"</td>" +
									"<td>" + f.flightClass+"</td>"
							);
							$("#flightsForFilterView").append(tr);			
						}
				)		
			}
		},	
		error : function(errorThrown) {
			alert(errorThrown);
		}
	});
}

$(document).ready(function(){
	$('#flightIDs').on('change',function() {
		filterFlights();
	});

});

$(document).ready(function(){
	$('#flightClass').on('change',function() {
		filterFlights();
	});

});

$(document).ready(function(){
	$('#dateFlightFilter').on('change',function() {
		filterFlights();
	});

});

$(document).on('click', '#cancel', function(e) {
	e.preventDefault();
		var a = $(this).attr("name").split(" ");
		
		var number = a[0];
		var flightNumber = a[1];
		
		$.ajax({
			type : 'DELETE',
			url : "../rezervacije/rest/reservationService/cancel/" + number + "/" + flightNumber,
			contentType : 'application/json',
			cache: false,
			success : function() {
				//readData();
				readReservations();
			},
			error : function(errorThrown) {
				alert(errorThrown);
			}
		});
	

})

$(document).ready(function(){
	$('input[type=radio][name=parametersType]').on('change',function() {
		
		var param =  $('input[name=parametersType]:checked').val();
		if(param.includes("destination")){
			$.ajax({
				type : 'GET',
				url : "../rezervacije/rest/destinationService/getNamesOfDestinations",
				cache: false,
				dataType : "json", 
				success : function(names) {
					
					if(names.length != 0){	
						var s = "<option value=\"\"></option>";
						
						$.each(names,function(index,n){
							s += "<option value=\"" + n+ "\">" + n + "</option>";
						})
						
						s+= "</select>";
						$("#from").replaceWith("<select id=\"from\">" + s);
						$("#to").replaceWith("<select id=\"to\">" + s);
						
						
					}
				},
				error : function(errorThrown) {
					alert(errorThrown);
				}
			});
		}else{
			$("#from").replaceWith("<input type=\"text\" class=\"form-control\" id=\"from\">");
			$("#to").replaceWith("<input type=\"text\" class=\"form-control\" id=\"to\">");
		}
			
	});

});





$(document).ready(function(){
	$(document).on("submit","#userSearchFlight",function(e){
		e.preventDefault();
		var from = document.getElementById("from").value;
		var to = document.getElementById("to").value;
		var date = document.getElementById("dateFlightSearch").value;
		
		var param =  $('input[name=parametersType]:checked').val();
			
		var country = true;
		if(param.includes("destination")){
			country = false;
		}
			
			
		if(from === to && from !== "" && param !== "country"){
			alert("From and to field must be different!");
		}else{
			$.ajax({
				type : 'POST',
				contentType : 'application/json',
				dataType : "json",
				cache: false,
				url : "../rezervacije/rest/flightService/searchFlightsForUser",
				data: JSON.stringify({"from": from,"to": to,"date": date,"country": country}),
				success : function(flights) {
						
					$("#flightsForUserView").find("tr:not(:first)").remove();
						
						
						if(flights.length === 0){
							$("#flightsForUserView").hide();
							$("#messageFlightsForUserFound").show();
						}else{
							$("#messageFlightsForUserFound").hide();
								
							$("#flightsForUserView").show();
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
								$("#flightsForUserView").append(tr);	
							})
						}
				},
				error : function(errorThrown) {
					alert(errorThrown);
				}
			});
		}

	})
})




