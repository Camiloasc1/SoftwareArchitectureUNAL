"use strict";
var app = angular.module('SoftwareArchitectureUNAL', ['ngRoute']);

app.controller('NavigationController', ['$scope', '$http', function ($scope, $http) {
    $scope.user = null;

    $scope.me = function () {
        $http.get('auth/me')
            .then(function (response) {
                if (response.status === 200)
                    $scope.user = response.data;
                else
                    $scope.user = null;
            });
    };
    $scope.logout = function () {
        $http.post('auth/logout', {})
            .then(function (response) {
                if (response.status === 204)
                    $scope.user = null;
            });
    };

    $scope.$on('OnLogin', function (event, user) {
        $scope.user = user;
    });

    $scope.me();
}]);

app.controller('HomeController', ['$scope', '$http', function ($scope, $http) {
}]);

app.controller('LoginController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    $scope.credentials = {};
    
    $scope.submit = function () {
        $http.post('auth/login', $scope.credentials)
            .then(function (response) {
                if (response.status === 200) {
                    $scope.$emit('OnLogin', response.data);
                    $location.path('/');
                }
                else // 204 Error
                {
                }
                $scope.credentials = {};
            });
    };
}]);

app.controller('UserController', ['$scope', '$http', function ($scope, $http) {
    $scope.user = null;

    $scope.me = function () {
        $http.get('auth/me')
            .then(function (response) {
                if (response.status === 200)
                    $scope.user = response.data;
                else
                    $scope.user = null;
            });
    };

    $scope.me();
}]);

app.config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'partials/home.html',
            controller: 'HomeController'
        })
        .when('/login', {
            templateUrl: 'partials/login.html',
            controller: 'LoginController'
        })
        .when('/me', {
            templateUrl: 'partials/me.html',
            controller: 'UserController'
        })
        .otherwise({redirectTo: '/'});

    //html5mode causes several issues when the front end is embedded with the web service.
    //$locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
}]);