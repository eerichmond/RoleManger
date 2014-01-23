<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Role Manager</title>

    <!-- build:css resources/css/build.css -->
    <link rel="stylesheet/less" type="text/css" href="resources/css/master.less" />
    <script type="text/javascript" src="resources/lib/less.js"></script>
    <!-- endbuild -->

</head>
<body>
    <div class="navbar navbar-fixed-top" ng-controller="MainCtrl">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" ng-click="isCollapsed = !isCollapsed">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<%= request.getContextPath() %>">
                    <div>Role Manager</div>
                </a>
            </div>
            <div class="navbar-collapse" ng-class="{collapse: isCollapsed}">
                <ul class="nav navbar-nav">
                    <li ng-class="{'active': activeTab === 'Home'}"><a ng-href="#/">Home</a></li>
                </ul>
                <div class="nav navbar-user ">
                    <span ng-if="profile.currentUser != null" ng-bind="profile.currentUser.firstName + ' ' + profile.currentUser.lastName"></span>
                </div>
            </div>
        </div>
    </div>

    <div class="container" ng-view=""></div>

    <script src="resources/lib/angular.js"></script>
    <script src="resources/lib/angular-animate.js"></script>
    <script src="resources/lib/angular-resource.js"></script>
    <script src="resources/lib/angular-route.js"></script>
    <script src="resources/lib/lodash.js"></script>
    <script src="resources/lib/moment.js"></script>
    <script src="resources/lib/ui-bootstrap.js"></script>

    <!-- build:js resources/scripts/build.js -->
    <script src="resources/scripts/app.js"></script>

    <script src="resources/scripts/controllers/main.js"></script>
    <!-- endbuild -->
</body>
</html>
