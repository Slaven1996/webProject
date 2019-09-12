
readData();


function readData() {
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
}

$(document).on("submit", ".login-form", function(e) {
	e.preventDefault();
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	
	$.ajax({
		type : 'POST',
		url : "../rezervacije/rest/userService/login",
		contentType : 'application/json',
		dataType : "json",
		cache: false,
		data : JSON.stringify({"username" : username, "password" : password}),
		success : function(user) {
			console.log(user)
			if (typeof (user) == "undefined") {
				alert("This combination of username and password doensn't exist in database!");
			}else if(user.active === false){
				alert("Your account is deactivated!");
			}
			else {
				console.log(user.userType);
				if (user.userType === "ADMIN") {
					window.location.href = "admin.html";
				} else {
					window.location.href = "user.html";
				}
			}
		},
		error : function(errorThrown) {
			alert(errorThrown);
		}

	});

});