/**
 * @author SiWoo Kim,
 * @email sm123tt@gmail.com
 * @version 1.0.0
 * @since 2018-07-07 오전 9:00
 * @github : https://github.com/Siwoo-Kim
 **/

angular.module("sportsStoreAdmin")
    .constant("authUrl", "http://localhost:8080/users/login")
    .constant("ordersUrl", "http://localhost:8080/orders")
    .controller("authCtrl", function ($scope, $http, $location, authUrl) {
        $scope.authenticate = (user, pass) => {
            $http.post(authUrl,
                    {
                          username: user,
                          password: pass
                    },
                    {
                        withCredentials: true
                    })
                .then(
                    (response) => $location.path("/main"),
                    (error) => $scope.authenticationError = error);

        }
    })
    .controller('mainCtrl', function ($scope) {
        $scope.screens = ["Products", "Orders"];
        $scope.current = $scope.screens[0];

        $scope.setScreen = (index) => {
            $scope.current = $scope.screens[index];
        };

        $scope.getScreen = () => {
            return $scope.current === "Products" ? "/static/views/adminProducts.html" : "/static/views/adminOrders.html";
        }
    })
    .controller("ordersCtrl", function ($scope, $http, ordersUrl) {
        $http.get(ordersUrl, { withCredentials: true })
            .then(
                (response) => $scope.orders = response.data,
                (error) => $scope.error = error);

        $scope.selectOrder = (order) => $scope.selectedOrder = order;

        $scope.calcTotal = (order) => {
            var total = 0;
            for(var i=0; i<order.products.length; i++) {
                total += order.products[i].count * order.products[i].price;
            }
            return total;
        }
    });
    