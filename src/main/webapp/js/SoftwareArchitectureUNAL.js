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
    $scope.consult = 0;
    $scope.editing = false;

    $scope.materials = {};

    $scope.submit = function () {

        if (!$scope.material.hasOwnProperty('inventory')) return;

        if ($scope.type === "raw") {
            $scope.material.rawMaterial = true;
            $scope.material.supply = false;
        } else {
            $scope.material.rawMaterial = false;
            $scope.material.supply = true;
        }

        $http.post('materials', $scope.material)
            .then(function (response) {
                if (response.status === 200) {
                    $scope.material = {};
                    alert("Agregado correctamente");
                }
                else {

                }
            });
    }

    $scope.getMaterials = function () {
        $http.get('materials')
            .then(function (response) {
                if (response.status === 200) {
                    //console.log(response.data);

                    for (var x in response.data) {
                        var reg = response.data[x];
                        if (reg.supply) {
                            reg.my_type = "Insumo";
                        } else {
                            reg.my_type = "Materia prima";
                        }
                    }

                    $scope.materials = response.data;

                    $scope.columns = [
                        {title: 'Referencia', field: 'id', visible: true},
                        {title: 'Nombre', field: 'name', visible: true},
                        {title: 'Existencias', field: 'inventory', visible: true},
                        {title: 'Precio unitario', field: 'price', visible: true},
                        {title: 'Tipo', field: 'my_type', visible: true},
                        {title: 'Proveedor', field: 'provider', visible: true}
                    ];

                }
                else {

                }
            });
    }

    $scope.getMaterialID = function () {
        $http.get('materials/' + $scope.consult)
            .then(function (response) {

                if (response.status === 200) {
                    $scope.materialToEdit = response.data;

                    if($scope.materialToEdit.supply) $scope.type_edit = "supply";
                    else $scope.type_edit = "raw";

                    $scope.editing = true;
                } else {
                    alert("Material no encontrado");
                    $scope.editing = false;
                }
            })
    }
    
    $scope.edit = function () {

        if($scope.type_edit === "raw"){
            $scope.materialToEdit.supply = false;
            $scope.materialToEdit.rawMaterial = true;
        }else{
            $scope.materialToEdit.supply = true;
            $scope.materialToEdit.rawMaterial = false;
        }

        $http.put('materials/' + $scope.materialToEdit.id, $scope.materialToEdit)
            .then(function (response) {

                if (response.status === 200) {
                    $scope.materialToEdit = {};
                    alert("Material editado correctamente");
                    $scope.editing = false;
                } else {
                    alert("No se pudieron guardar los cambios");
                }

            });
    }


    $scope.delete = function () {
        $http.delete('materials/' + $scope.materialToEdit.id)
            .then(function (response) {
                if (response.status === 204) {
                    $scope.materialToEdit = {};
                    alert("Material borrado correctamente");
                    $scope.editing = false;
                } else {
                    alert("No se pudo borrar el material");
                }

            });
    }
}]);