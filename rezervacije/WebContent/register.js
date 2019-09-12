



$(document).on("submit", ".register-form", function(e) {
	e.preventDefault();
	var username = document.getElementById("username").value;
	var password1 = document.getElementById("password1").value;
	var password2 = document.getElementById("password2").value;
	var name = document.getElementById("name").value;
	var surname = document.getElementById("surname").value;
	var phone = document.getElementById("phone").value;
	var email = document.getElementById("email").value;
	
	
	if(username === "" || password1 === "" || password2 === "" || name === "" || surname === "" || phone === "" || email === ""){
		alert("You must fill all fields!");
		return;
	}
	
	if(password1 !== password2){
		alert("Passwords don't match!");
		return;
	}
	
	
	$.ajax({
		type : 'POST',
		url : "../rezervacije/rest/userService/register",
		contentType : 'application/json',
		dataType : "json",
		data : JSON.stringify({"username": username,"password": password1,"name":name,"surname":surname,
			"phone":phone,"email":email
		}),
		success : function(retVal) {
			if(retVal.success === false){
				alert(retVal.message);
			} else {
				alert(retVal.message);
				window.location.href = "user.html";
			}
		},
		error : function(errorThrown) {
			alert(errorThrown);
		}

	});

});







