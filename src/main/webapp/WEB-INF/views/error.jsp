<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <title>Safety Inspection Tool</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/build.css"/>

</head>
<body>
    <div class="navbar navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="<%= request.getContextPath() %>">
                    <div>Role Manager</div>
                </a>
            </div>
        </div>
    </div>

    <div class="container">

        <div class="alert alert-danger" style="margin-top: 25px;">
            <h4 class="alert-heading">Well, this is embarrassing !</h4>
            <p>
                The system is not able to complete the action you are trying to perform.  <br/>
                Please reload the page and try again.
            </p>
            <p>
                Http Status: ${requestScope['javax.servlet.error.status_code']}
            </p>
        </div>
    </div>

</body>
</html>
