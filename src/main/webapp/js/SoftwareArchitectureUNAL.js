"use strict";
var app = angular.module('SoftwareArchitectureUNAL', ['ngRoute']);

app.controller('NavigationController', ['$scope', '$http', function ($scope, $http) {
    $scope.user = {};

    $scope.$on('OnLogin', function (event, user) {
        $scope.user = user;
    });
}]);

app.controller('HomeController', ['$scope', '$http', function ($scope, $http) {
}]);

app.controller('MaterialController', ['$scope', '$http', function ($scope, $http) {
    $scope.material = {};

    $scope.notRaw = function () {
        $scope.material.rawMaterial = true;
        $scope.material.supply = false;
    };

    $scope.notSupply = function () {
        $scope.material.supply = true;
        $scope.material.rawMaterial = false;
    };

    $scope.submit = function () {
        $http.post('materials', $scope.material)
            .then(function (response) {
                if (response.status === 200) {
                    alert("Agregado correctamente");
                }
                else{

                }
            });
    }
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
    }
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
        .when('/material', {
            templateUrl: 'partials/material.html',
            controller: 'MaterialController'
        })


        .otherwise({redirectTo: '/'});

    //html5mode causes several issues when the front end is embedded with the web service.
    //$locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
}]);