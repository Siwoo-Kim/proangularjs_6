
angular.module('sportsStore')
    .constant('backendUrl', 'http://localhost:8080/sports')
    .controller('sportsStoreCtrl', function ($scope, $http, backendUrl) {
        $scope.data = {};

        $http.get(backendUrl)
            .then(
                (response) => $scope.data.products = response.data,
                (error) => $scope.data.error = error
            );

    });