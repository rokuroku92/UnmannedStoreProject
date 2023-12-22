;(function () {
	
	'use strict';

    const baseUrl = "http://" + window.location.hostname + ":8080";

    var login = function () {
        const username = document.getElementById('inputUsername').value;
        const password = document.getElementById('inputPassword').value;

        if(username === ""){
            alert("請輸入帳號");
            return;
        }
        if(password === ""){
            alert("請輸入密碼");
            return;
        }
    
        fetch(baseUrl + '/store/client/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `username=${username}&password=${password}`,
        })
        .then(response => response.json())
        .then(data => {
            // Save user ID in localStorage
            if(data===null){
                alert("帳號或密碼錯誤！");
            } else if(data.id){
                localStorage.setItem('userId', data.id);
                localStorage.setItem('userName', data.username);
                localStorage.setItem('userLevel', data.level);
                // Redirect to the shopping page
                if(data.level === 10){
                    window.location.href = 'management.html';
                } else {
                    window.location.href = 'product.html';
                }

            }
        })
        .catch(error => {
            alert("伺服器出現錯誤！");
            console.error('Error:', error);
        });
    }

    $(function(){
		document.getElementById("login").addEventListener("click", login);
		document.getElementById("signUp").addEventListener("click", function() {
            window.location.href = 'signUp.html';
        });
	});

}());