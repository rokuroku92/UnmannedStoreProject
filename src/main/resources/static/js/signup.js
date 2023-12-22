;(function () {
	
	'use strict';

    const baseUrl = "http://" + window.location.hostname + ":8080";
    
    var signUp = function () {
        const username = document.getElementById('inputUsername').value;
        const password = document.getElementById('inputPassword').value;
        const phone = document.getElementById('inputPhone').value;

        if(username === ""){
            alert("請輸入帳號");
            return;
        } else if(password === ""){
            alert("請輸入密碼");
            return;
        } else if(phone === ""){
            alert("請輸入電話");
            return;
        }
    
        fetch(baseUrl + '/store/client/api/signUp', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `username=${username}&password=${password}&phoneNumber=${phone}`,
        })
        .then(response => response.text())
        .then(data => {
            // Save user ID in localStorage
            if(data==="OK"){
                alert("註冊成功！");
                window.location.href = 'login.html';
            } else {
                alert("註冊失敗！");
            }
        })
        .catch(error => {
            alert("伺服器出現錯誤！");
            console.error('Error:', error);
        });
    }

    $(function(){
		document.getElementById("signUp").addEventListener("click", signUp);
	});

}());