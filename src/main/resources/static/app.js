
angular.module('sportsStore', ['customFilters', 'cart', 'ngRoute'])
    .config(function ($routeProvider) {

        $routeProvider.when('/complete', {
            templateUrl: '/static/views/thankYou.html'
        });

        $routeProvider.when('/placeorder', {
            templateUrl: '/static/views/placeOrder.html'
        });


        $routeProvider.when('/checkout', {
            templateUrl: '/static/views/checkoutSummary.html'
        });

        $routeProvider.when('/products', {
            templateUrl: '/static/views/productList.html'
        });

        $routeProvider.otherwise({
            templateUrl: '/static/views/productList.html'
        });

    });