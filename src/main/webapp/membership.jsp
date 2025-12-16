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

<%@ include file="nav2.jsp" %>

<div class="container my-5">

    <h2 class="mb-4">Choose Your Membership Plan</h2>

    <c:if test="${not empty membership && membership.status eq 'ACTIVE'}">
        <div class="alert alert-success">
            You currently have an active membership (<b>${membership.planId}</b>)
            valid until <b>${membership.endDate}</b>.
        </div>
    </c:if>

    <c:if test="${empty membership or (membership.status ne 'ACTIVE' and membership.status ne 'PENDING')}">
    <div class="row g-4">

        <!-- Monthly -->
        <div class="col-md-4">
            <div class="card text-center text-dark" style="background:white;">
                <div class="card-header"><b>Monthly Plan</b></div>
                <div class="card-body">
                    <h3>$6 / month</h3>
                    <div id="paypal-button-monthly"></div>
                </div>
            </div>
        </div>

        <!-- 6-Month -->
        <div class="col-md-4">
            <div class="card text-center text-dark" style="background:white;">
                <div class="card-header"><b>Half-Yearly Plan</b></div>
                <div class="card-body">
                    <h3>$30 / 6 months</h3>
                    <div id="paypal-button-halfyear"></div>
                </div>
            </div>
        </div>

        <!-- Yearly -->
        <div class="col-md-4">
            <div class="card text-center text-dark" style="background:white;">
                <div class="card-header"><b>Yearly Plan</b></div>
                <div class="card-body">
                    <h3>$50 / year</h3>
                    <div id="paypal-button-yearly"></div>
                </div>
            </div>
        </div>

        <!-- PayPal SDK -->
<script src="https://www.paypal.com/sdk/js?client-id=${paypalClientId}&vault=true&intent=subscription"></script>

<script>
const plans = {
    monthly: '${paypalPlans.MONTHLY}',
    halfyear: '${paypalPlans.HALFYEAR}',
    yearly: '${paypalPlans.YEARLY}'
};

function renderButton(container, planId) {
    paypal.Buttons({
        style: { shape:'rect', color:'blue', layout:'horizontal', label:'subscribe' },
        createSubscription: (data, actions) =>
            actions.subscription.create({ plan_id: planId }),
        onApprove: data =>
            fetch('/membership/confirm', {
                method: 'POST',
                headers: {'Content-Type':'application/json'},
                body: JSON.stringify({ subscriptionID: data.subscriptionID, planID: planId })
            }).then(() => window.location.href='/profile')
    }).render(container);
}

renderButton('#paypal-button-monthly', plans.monthly);
renderButton('#paypal-button-halfyear', plans.halfyear);
renderButton('#paypal-button-yearly', plans.yearly);
</script>

    </div>
    </c:if>
</div>

<%@ include file="footer2.jsp" %>





</body>
</html>
