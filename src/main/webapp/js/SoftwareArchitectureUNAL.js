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

app.controller('ProductController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    $scope.product = {};
    $scope.products = {};
    $scope.pToUpdate = {};
    $scope.submit = function () {
        $http.post('products', $scope.product)
            .then(function (response) {
                if (response.status === 200) {
                    alert("producto creado");
                }
                else{
                    alert("No se pudo crear el producto");
                }
            });
    }
    $scope.getProducts = function () {
        $http.get('products')
            .then(function (response) {
                if (response.status === 200) {
                    $scope.products = response.data;
                    $scope.columns = [
                        { title: 'id', field: 'id', visible: true },
                        { title: 'Nombre', field: 'name', visible: true },
                        { title: 'Existencias', field: 'inventory', visible: true },
                        { title: 'Precio', field: 'price', visible: true }
                    ];
                }
                else{
                    alert("No existen productos en el sistema");
                }
            });
    }
    $scope.updateProduct = function (pId) {
        $http.put('products/'+pId, $scope.pToUpdate)
            .then(function (response) {
                if (response.status === 200) {
                    $scope.pToUpdate = {};
                    alert("Producto actualizado");
                }
                else{
                    alert("No se pudo editar el producto");
                }
            });
    }
    $scope.deleteProduct = function (pId) {
        $http.delete('products/'+pId)
            .then(function (response) {
                alert("Producto borrado");
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
        .when('/me', {
            templateUrl: 'partials/me.html',
            controller: 'UserController'
        })
        .when('/product', {
            templateUrl: 'partials/product.html',
            controller: 'ProductController'
        })
        .otherwise({redirectTo: '/'});

    //html5mode causes several issues when the front end is embedded with the web service.
    //$locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
}]);