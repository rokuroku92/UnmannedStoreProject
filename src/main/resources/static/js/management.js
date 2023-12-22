// const lastItems = "1, 2, 2, 2, 3, 4, 5, 5, 5, 5, 6, 7, 8";  // 1,2,3,4...
// const lastItemCount = "3";
var items = {};
var itemPrice = {};
var total=0;
var itemCount=0;
;(function () {
	
	'use strict';

    const baseUrl = "http://" + window.location.hostname + ":8080";

    var uploadItem = function() {
        $('#upload-btn').on('click', function() {
            var itemName = $('#input-item-name').val();
            var itemPrice = $('#input-item-price').val();
            var itemCategory = $('#input-item-category').val();
            var itemQuantity = $('#input-item-quantity').val();
            var itemDescribe = $('#input-item-describe').val();
            var itemImg = $('#input-item-img')[0].files[0];
    
            var formData = new FormData();
            formData.append('name', itemName);
            formData.append('price', itemPrice);
            formData.append('categoryId', itemCategory);
            formData.append('quantity', itemQuantity);
            formData.append('describe', itemDescribe);
            formData.append('img', itemImg);
    
            console.log(formData);
            fetch(baseUrl + '/store/admin/api/putItem', {
                method: 'POST',
                body: formData,
            })
            .then(response => response.text())
            .then(data => {
                console.log(data);
                if(data === "OK"){
                    alert('新增成功！');
                } else {
                    alert('新增失敗！');
                }

            })
            .catch(error => {
                console.error(error);
            });
            // $.ajax({
            //     url: baseUrl + `/store/admin/api/putItem`,
            //     type: 'POST',
            //     data: formData,
            //     contentType: false,
            //     processData: false,
            //     success: function(response) {
            //         console.log(response);
            //     },
            //     error: function(error) {
            //         console.error(error);
            //     }
            // });
        });
    }
    
    var loadItem = function () {
        document.getElementById("item-list").innerHTML = "";
        fetch(baseUrl + '/store/admin/api/getAllItem')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if(data !== null){
                data.forEach((item) => {
                    document.getElementById("item-list").innerHTML += `<div id="product-${item.id}" class="col-md-4 text-center animate-box">
                    <div class="product">
                        <div class="product-grid" style="background-image:url(images/product-${item.id}.jpg);">
                            <div class="inner">
                                <p>
                                    <a href="single.html" class="icon"><i class="icon-eye"></i></a>
                                    <button class="icon trash" data-product-name="product-${item.id}" data-product-id="${item.id}"><i class="bi bi-trash"></i></button>
                                </p>
                            </div>
                        </div>
                        <div class="desc">
                            <h3><a href="single.html">${item.name}</a></h3>
                            <span class="price">$${item.price}X<span class="quantity">${item.quantity}</span></span>
                        </div>
                    </div>
                </div>`;
                });
            } else {
                console.error("查無此商品！");
            }
        })
        .catch(error => {
            console.error('發生錯誤：', error);
        });
        setTimeout(contentWayPoint, 400);
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
                } else {
                    alert(data);
                }
            }).catch(error => {
                console.error('發生錯誤：', error);
            });
        });
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

    $(function(){
        uploadItem();
        loadItem();
        // contentWayPoint();
        // addItem();
        // pay();
        // setTimeout(bindRemoveItem, 600);
    });

}());


// function logout(){
//     localStorage.removeItem('userId');
//     localStorage.removeItem('userName');
//     localStorage.removeItem('userLevel');
//     window.location.href = 'login.html';
// }