"use strict";
var app = angular.module('SoftwareArchitectureUNAL', ['ngRoute']);

app.controller('NavigationController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    $scope.user = null;

    $scope.me = function () {
        $http.get('auth/me')
            .then(function (response) {
                $scope.user = response.data;
            }, function () {
                $location.path('/');
                $scope.user = null;
            });
    };
    $scope.logout = function () {
        $http.post('auth/logout', {})
            .then(function () {
                $scope.user = null;
            });
    };

    $scope.$on('OnLogin', function (event, user) {
        $scope.user = user;
    });

    $scope.me();
}]);

app.controller('LoginController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    $scope.credentials = {};
    $scope.success = false;
    $scope.error = false;

    $scope.submit = function () {
        $http.post('auth/login', $scope.credentials)
            .then(function (response) {
                $scope.success = true;
                $scope.error = false;
                $scope.$emit('OnLogin', response.data);
                $timeout(function () {
                    $location.path('/');
                }, 2500);
                $scope.credentials = {};
            }, function () {
                $scope.success = false;
                $scope.error = true;
                $scope.credentials = {};
            });
    };
}]);

app.controller('UserController', ['$scope', '$http', function ($scope, $http) {
    $scope.user = {};
    $scope.success = false;
    $scope.error = false;

    $scope.me = function () {
        $http.get('auth/me')
            .then(function (response) {
                $scope.user = response.data;
            }, function () {
                $scope.user = {};
            });
    };
    $scope.submit = function () {
        $http.put('users/' + $scope.user.username, $scope.user)
            .then(function (response) {
                $scope.success = true;
                $scope.error = false;
                $scope.user = response.data;
                $scope.$emit('OnLogin', response.data);
            }, function () {
                $scope.success = false;
                $scope.error = true;
                $scope.me();
            });
    };

    $scope.me();
}]);

app.controller('HomeController', ['$scope', '$http', function ($scope, $http) {
}]);

app.controller('AdminController', ['$scope', '$http', function ($scope, $http) {
}]);

app.controller('SalesController', ['$scope', '$http', function ($scope, $http) {
}]);

app.controller('ProductionController', ['$scope', '$http', function ($scope, $http) {
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
        .when('/admin', {
            templateUrl: 'partials/admin.html',
            controller: 'AdminController'
        })
        .when('/sales', {
            templateUrl: 'partials/sales.html',
            controller: 'SalesController'
        })
        .when('/production', {
            templateUrl: 'partials/production.html',
            controller: 'ProductionController'
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

app.controller('MaterialController', ['$scope', '$http', function ($scope, $http) {
    $scope.material = {};
    $scope.type = "raw";

    $scope.materials = {};

    $scope.submit = function () {

        if(!$scope.material.hasOwnProperty('inventory')) return;

        if($scope.type === "raw"){
            $scope.material.rawMaterial = true;
            $scope.material.supply = false;
        }else{
            $scope.material.rawMaterial = false;
            $scope.material.supply = true;
        }

        $http.post('materials', $scope.material)
            .then(function (response) {
                if (response.status === 200) {
                    alert("Agregado correctamente");
                }
                else{

                }
            });
    }

    $scope.getMaterials = function () {
        $http.get('materials')
            .then(function (response) {
                if (response.status === 200) {
                    //console.log(response.data);
                    $scope.materials = response.data;

                    $scope.columns = [
                        { title: 'Nombre', field: 'name', visible: true },
                        { title: 'Existencias', field: 'inventory', visible: true },
                        { title: 'Precio', field: 'price', visible: true },
                        { title: 'Es insumo?', field: 'supply', visible: true },
                        { title: 'Es materia prima?', field: 'rawMaterial', visible: true },
                        { title: 'Proveedor', field: 'provider', visible: true }
                    ];

                }
                else{

                }
            });
    }

}]);