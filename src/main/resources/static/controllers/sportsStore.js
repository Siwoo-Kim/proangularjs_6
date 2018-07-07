
angular.module('sportsStore')
    .constant('dataUrl', 'http://localhost:8080/products')
    .constant('orderUrl', 'http://localhost:8080/orders')
    .controller('sportsStoreCtrl', function ($scope, $http, $location, dataUrl, orderUrl, cart) {
        $scope.data = {};

        $http.get(dataUrl)
            .then(
                (response) => $scope.data.products = response.data,
                (error) => $scope.data.error = error
            );

        $scope.sendOrder = function (shippingDetails) {
            var order = angular.copy(shippingDetails);
            order.products = cart.getProducts();
            $http.post(orderUrl, order)
                .then(
                    (response) => {
                        console.log(response);
                        $scope.data.orderId = response.data.id;
                        cart.getProducts().length = 0;
                    },
                    (error) => {
                        $scope.data.orderError = error;
                    })
                .finally(() => $location.path('/complete'));
        }
    });