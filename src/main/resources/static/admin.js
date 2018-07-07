
angular.module("sportsStoreAdmin", ["ngRoute"])
    .config(function ($routeProvider) {

        $routeProvider.when("/login", {
            templateUrl: "/static/views/adminLogin.html"
        });

        $routeProvider.when("/main", {
            templateUrl: "/static/views/adminMain.html"
        });

        $routeProvider.otherwise({
            redirectTo: "login"
        });
    });