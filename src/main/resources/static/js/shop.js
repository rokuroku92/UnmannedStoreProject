const userId = localStorage.getItem('userId');
const userName = localStorage.getItem('userName');
const userLevel = localStorage.getItem('userLevel');
;(function () {
	
	'use strict';

    // Check if user is logged in
    var checkLogin = function () {
        console.log("userId: ", userId);
        if (!userId) {
            // Redirect to the login page if not logged in
            if(window.location.pathname !== "/store/login" && window.location.pathname !== "/store/login.html" && window.location.pathname !== "/login.html"){
                window.location.href = 'login.html';
            }
        } else {
            let level = userLevel === "10" ? "管理員" : "客戶";
            // const nav = document.querySelector(".fh5co-nav");

            document.querySelectorAll("#accountInfo").forEach(el => {
                el.innerHTML = `<li class="w-100 p-2">
                                    <p>ID: ${userId}</p>
                                    <p>帳號: ${userName}</p>
                                    <p>權限: ${level}</p>
                                </li>
                                <li class="w-100 p-2">
                                    <input type="button" class="btn btn-primary dropdown-item d-flex align-items-center" value="登出" onclick="logout()"/>
                                </li>`;
            });

            $('#fh5co-offcanvas button').append(`<label>${userName}</label>`);
        }
    }

    $(function(){
		checkLogin();
        // localStorage.removeItem('userId');
        // 登出
	});

}());

function logout(){
    localStorage.removeItem('userId');
    localStorage.removeItem('userName');
    localStorage.removeItem('userLevel');
    localStorage.removeItem('userItems');
    localStorage.removeItem('userItemCount');
    window.location.href = 'login.html';
}