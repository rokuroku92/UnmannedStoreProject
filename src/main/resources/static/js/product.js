let lastItems = localStorage.getItem('userItems');  // 1,2,3,4...
// const lastItems = "1, 2, 2, 2, 3, 4, 5, 5, 5, 5, 6, 7, 8";  // 1,2,3,4...
let lastItemCount = localStorage.getItem('userItemCount');
// const lastItemCount = "3";
var items = {};
var itemPrice = {};
var total=0;
var itemCount=0;
;(function () {
	
	'use strict';

    const baseUrl = "http://" + window.location.hostname + ":8080";
    
    var loadItem = function () {
        document.getElementById("item-list").innerHTML = "";
        if (lastItemCount !== null){
            document.querySelectorAll("#userItemCount").forEach(el => {
                el.innerHTML = lastItemCount;
            });
        } else {
            lastItemCount = 0;
        }
        if (lastItems === 'NaN'){
            lastItems = null;
            localStorage.removeItem('userItems'); 
        }
        if (lastItems !== null){
            const itemArray = lastItems.split(',').map(function(item) {
                return parseInt(item.trim(), 10);
            });
            
            itemArray.forEach(function(id) {
                items[id] = (items[id] || 0) + 1;
            });

            // console.log(items);

            Object.entries(items).forEach(([id, quantity]) => {
                fetch(baseUrl + `/store/client/api/getItem?id=${id}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    if(data !== null){
                        itemPrice[data.id] = data.price;
                        document.getElementById("item-list").innerHTML += `<div id="product-${data.id}" class="col-md-4 text-center animate-box">
                            <div class="product">
                                <div class="product-grid" style="background-image:url(images/product-${data.id}.jpg);">
                                    <div class="inner">
                                        <p>
                                            <a href="#" class="icon"><i class="icon-eye"></i></a>
                                            <button class="icon trash" data-product-name="product-${data.id}" data-product-id="${data.id}"><i class="bi bi-trash"></i></button>
                                        </p>
                                    </div>
                                </div>
                                <div class="desc">
                                    <h3><a href="#">${data.name}</a></h3>
                                    <span class="price">$${data.price}X<span class="quantity">${quantity}</span></span>
                                </div>
                            </div>
                        </div>`;
                    } else {
                        console.error("查無此商品！");
                    }
                })
                .catch(error => {
                    console.error('發生錯誤：', error);
                });
            });
            setTimeout(contentWayPoint, 400);
            setTimeout(updateTotal, 500);
        }
    };

    var addItem = function() {
        $('#input-item-id').on('change', function() {
            // 當 input 元素的值發生改變時
            var inputId = $(this).val();  // 獲取 input 的新值
            if(inputId == NaN){
                return;
            }
            if(items[inputId] === undefined){
                fetch(baseUrl + `/store/client/api/getItem?id=${inputId}`)
                .then(response => {
                    if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    // console.log('成功獲取數據：', data);
                    if(data !== null){
                        items[data.id] = 1;
                        itemPrice[data.id] = data.price;
                        document.getElementById("item-list").innerHTML += `<div id="product-${data.id}" class="col-md-4 text-center animate-box">
                            <div class="product">
                                <div class="product-grid" style="background-image:url(images/product-${data.id}.jpg);">
                                    <div class="inner">
                                        <p>
                                            <a href="#" class="icon"><i class="icon-eye"></i></a>
                                            <button class="icon trash" data-product-name="product-${data.id}" data-product-id="${data.id}"><i class="bi bi-trash"></i></button>
                                        </p>
                                    </div>
                                </div>
                                <div class="desc">
                                    <h3><a href="#">${data.name}</a></h3>
                                    <span class="price">$${data.price}X<span class="quantity">1</span></span>
                                </div>
                            </div>
                        </div>`;
                    } else {
                        alert("查無此商品！");
                    }
                })
                .catch(error => {
                    console.error('發生錯誤：', error);
                });
            } else {
                items[inputId]++;
                document.getElementById(`product-${inputId}`).querySelector(".quantity").innerHTML = items[inputId];
            }

            itemCount++;
            localStorage.setItem('userItemCount', String(parseInt(lastItemCount) + itemCount));
            document.querySelectorAll("#userItemCount").forEach(el => {
                el.innerHTML = String(parseInt(lastItemCount) + itemCount);
            });

            localStorage.setItem('userItems', itemsToString());
            $(this).val('');
            setTimeout(contentWayPoint, 400);
            setTimeout(updateTotal, 500);
            setTimeout(bindRemoveItem, 500);
        });
    };

    var bindRemoveItem = function() {
        $('[data-product-name]').off('click');
        $('[data-product-name]').on('click', function(){
            var productName = $(this).data('product-name');
            var productId = $(this).data('product-id');
            if(items[productId] > 1){
                items[productId]--;
                document.getElementById(`product-${productId}`).querySelector(".quantity").innerHTML = items[productId];
            } else if(items[productId] === 1){
                delete items[productId];
                $("#"+productName).remove();
            }

            itemCount--;
            localStorage.setItem('userItemCount', String(parseInt(lastItemCount) + itemCount));
            document.querySelectorAll("#userItemCount").forEach(el => {
                el.innerHTML = String(parseInt(lastItemCount) + itemCount);
            });
            localStorage.setItem('userItems', itemsToString());

            setTimeout(contentWayPoint, 400);
            setTimeout(updateTotal, 500);
        });  
    };

    var pay = function() {
        $('#pay-btn').on('click', function(){
            let orderRequest = {
                accountId: parseInt(localStorage.getItem('userId')),
                paymentAmount: total,
                orderItemRequests: null
            };
            let orderItemRequestArray = [];
            Object.entries(items).forEach(([id, quantity]) => {
                let orderItem = {
                    itemId: parseInt(id),
                    quantity: quantity,
                    amount: itemPrice[id]*quantity
                };
                orderItemRequestArray.push(orderItem);
            });
            orderRequest.orderItemRequests = orderItemRequestArray;
            console.log(JSON.stringify(orderRequest));

            fetch(baseUrl + '/store/client/api/pay', {
                method: 'POST',  // 可以根据需要使用不同的 HTTP 方法
                headers: {
                  'Content-Type': 'application/json'
                },
                body: JSON.stringify(orderRequest)
            }).then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.text();
            }).then(data => {
                if(data === "OK"){
                    alert("付款成功！ " + "金額為： " + total + " 元");
                    localStorage.removeItem('userItems');
                    localStorage.removeItem('userItemCount');
                    window.location.href = 'about.html';
                } else {
                    alert(data);
                }
            }).catch(error => {
                console.error('發生錯誤：', error);
            });
        });
    };

    var updateTotal = function(){
        // 抓取所有符合條件的元素
        var priceElements = document.querySelectorAll('.price');

        // 初始化變數以儲存總價
        var totalPrice = 0;

        // 遍歷每個抓取到的元素
        priceElements.forEach(function(priceElement) {
            // 獲取價格文本內容
            var priceText = priceElement.textContent;

            // 使用正則表達式提取數值
            var matchResult = priceText.match(/\$(\d+)X(\d+)/);

            if (matchResult) {
                // 提取到數值後，將字串轉換成數字，並進行相乘
                var priceValue = parseInt(matchResult[1]);
                var quantityValue = parseInt(matchResult[2]);
                totalPrice += priceValue * quantityValue;
            }
        });

        // 輸出結果
        document.getElementById("total").innerHTML = `總計： ${totalPrice}元`;
        total = totalPrice;
        // console.log('總價格：$' + totalPrice);

    };

    var contentWayPoint = function() {
		var i = 0;
		$('.animate-box').waypoint( function( direction ) {

			if( direction === 'down' && !$(this.element).hasClass('animated-fast') ) {
				
				i++;

				$(this.element).addClass('item-animate');
				setTimeout(function(){

					$('body .animate-box.item-animate').each(function(k){
						var el = $(this);
						setTimeout( function () {
							var effect = el.data('animate-effect');
							if ( effect === 'fadeIn') {
								el.addClass('fadeIn animated-fast');
							} else if ( effect === 'fadeInLeft') {
								el.addClass('fadeInLeft animated-fast');
							} else if ( effect === 'fadeInRight') {
								el.addClass('fadeInRight animated-fast');
							} else {
								el.addClass('fadeInUp animated-fast');
							}

							el.removeClass('item-animate');
						},  k * 200, 'easeInOutExpo' );
					});
					
				}, 100);
				
			}

		} , { offset: '85%' } );
	};

    var itemsToString = function () {
        return Object.keys(items).map(function(id) {
            return Array(items[id]).fill(id).join(',');
        }).join(',');

    }

    $(function(){
		loadItem();
        contentWayPoint();
        addItem();
        pay();
        setTimeout(bindRemoveItem, 600);
	});

}());


// function logout(){
//     localStorage.removeItem('userId');
//     localStorage.removeItem('userName');
//     localStorage.removeItem('userLevel');
//     window.location.href = 'login.html';
// }