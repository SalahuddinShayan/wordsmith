<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <script type="text/javascript" src="//c.pubguru.net/pghb.easternwordsmith_com.tc.js" async></script>
    <title>Membership Plans</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">

</head>

<body>
<script src="https://www.paypal.com/sdk/js?client-id=${paypalClientId}&currency=USD&intent=capture"></script>
<%@ include file="nav2.jsp" %>

<div class="container my-5">

    <h2 class="mb-4">Buy Coins</h2>

    

    
    <div class="row g-4">

        <!-- Small -->
        <div class="col-md-4">
            <div class="card text-center text-dark" style="background:white;">
                <div class="card-header"><b>Small Bundel</b></div>
                <div class="card-body">
                    <h3 class="coin-price-line">
                      <span class="coin-amount">80</span>

                      <span class="coin-inline">
                        <span class="ew-coin" Style="height: 23px; width: 23px;">
                          <img src="/images/enso.svg" alt="EWS Coins">
                        </span>
                      </span>

                      <span class="coin-for">For $1</span>
                    </h3>


                    <div id="paypal-small"></div>
                </div>
            </div>
        </div>


        

        <!-- Mid -->
        <div class="col-md-4">
            <div class="card text-center text-dark" style="background:white;">
                <div class="card-header"><b>Half-Yearly Plan</b></div>
                <div class="card-body">
                    <h3 class="coin-price-line">
                      <span class="coin-amount">450</span>
                      <span class="coin-inline">
                        <span class="ew-coin " Style="height: 23px; width: 23px;">
                          <img src="/images/enso.svg" alt="EWS Coins">
                        </span>
                      </span>
                      <span class="coin-for">For $5</span>
                    </h3>
                    <div id="paypal-medium"></div>
                </div>
            </div>
        </div>

        <!-- Large -->
        <div class="col-md-4">
            <div class="card text-center text-dark" style="background:white;">
                <div class="card-header"><b>Yearly Plan</b></div>
                <div class="card-body">
                    <h3 class="coin-price-line">
                      <span class="coin-amount">1000</span>
                      <span class="coin-inline">
                        <span class="ew-coin " Style="height: 23px; width: 23px;">
                          <img src="/images/enso.svg" alt="EWS Coins">
                        </span>
                      </span>
                      <span class="coin-for">For $10</span>
                    </h3>
                    <div id="paypal-large"></div>
                  </div>
            </div>
        </div>
</div>
</div>

<%@ include file="footer2.jsp" %>


<script>
paypal.Buttons({
    createOrder: function () {
        return fetch('/paypal/create-coin-order?pack=SMALL', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(res => res.json())
        .then(data => data.orderId);
    },

    onApprove: function (data, actions) {
        return actions.order.capture().then(function () {
            alert("Payment completed. Coins will appear shortly.");
        });
    },

    onError: function (err) {
        console.error(err);
        alert("Payment error. Please try again.");
    }

}).render('#paypal-small');

paypal.Buttons({
    createOrder: function () {
        return fetch('/paypal/create-coin-order?pack=MEDIUM', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(res => res.json())
        .then(data => data.orderId);
    },

    onApprove: function (data, actions) {
        return actions.order.capture().then(function () {
            alert("Payment completed. Coins will appear shortly.");
        });
    },

    onError: function (err) {
        console.error(err);
        alert("Payment error. Please try again.");
    }

}).render('#paypal-medium');

paypal.Buttons({
    createOrder: function () {
        return fetch('/paypal/create-coin-order?pack=LARGE', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(res => res.json())
        .then(data => data.orderId);
    },

    onApprove: function (data, actions) {
        return actions.order.capture().then(function () {
            alert("Payment completed. Coins will appear shortly.");
        });
    },

    onError: function (err) {
        console.error(err);
        alert("Payment error. Please try again.");
    }

}).render('#paypal-large');
</script>


</body>
</html>
