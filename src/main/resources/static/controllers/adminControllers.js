/**
 * @author SiWoo Kim,
 * @email sm123tt@gmail.com
 * @version 1.0.0
 * @since 2018-07-07 오전 9:00
 * @github : https://github.com/Siwoo-Kim
 **/

angular.module("sportsStoreAdmin")
    .constant("authUrl", "http://localhost:8080/users/login")
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
    });
    