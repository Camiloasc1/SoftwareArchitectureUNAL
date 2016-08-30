"use strict";
var app = angular.module('SoftwareArchitectureUNAL', ['ngRoute']);

app.controller('NavigationController', ['$scope', '$http', '$location', '$timeout', function ($scope, $http, $location, $timeout) {
    $scope.user = null;
    $scope.credentials = {};
    $scope.success = false;
    $scope.error = false;

    $scope.me = function () {
        $http.get('auth/me')
            .then(function (response) {
                $scope.user = response.data;
            }, function () {
                $location.path('/');
                $scope.user = null;
            });
    };
    $scope.login = function () {
        $http.post('auth/login', $scope.credentials)
            .then(function (response) {
                $scope.success = true;
                $scope.error = false;
                $scope.credentials = {};
                $scope.user = response.data;
                $timeout(function () {
                    $("#login").modal("hide");
                }, 1000);
            }, function () {
                $scope.success = false;
                $scope.error = true;
                $scope.credentials = {};
            });
    };
    $scope.logout = function () {
        $http.post('auth/logout', {})
            .then(function () {
                $scope.user = null;
            });
    };

    $scope.me();
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
        $http.put('users/' + $scope.user.id, $scope.user)
            .then(function (response) {
                $scope.success = true;
                $scope.error = false;
                $scope.user = response.data;
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

app.controller('MaterialController', ['$scope', '$http', function ($scope, $http) {
    $scope.materials = {};
    $scope.material = {};

    const RAW = "Materia Prima";
    const SUPPLY = "Insumo";
    $scope.columns = ['Nombre', 'Existencias', 'Precio unitario', 'Tipo', 'Proveedor', 'a', 'b'];

    $scope.get = function () {
        $http.get('materials')
            .then(function (response) {
                $scope.materials = response.data;
                for (var m in $scope.materials) {
                    if ($scope.materials[m].supply)
                        $scope.materials[m].type = SUPPLY;
                    else
                        $scope.materials[m].type = RAW;
                }
            });
    };
    $scope.edit = function (material) {
        $scope.material = material;

        if ($scope.material.rawMaterial)
            $scope.material.selectedType = "raw";
        else
            $scope.material.selectedType = "supply";

        $("#material").modal("show");
    };
    $scope.submit = function () {
        if ($scope.material.selectedType === "raw") {
            $scope.material.rawMaterial = true;
            $scope.material.supply = false;
            $scope.material.type = RAW;
        } else {
            $scope.material.rawMaterial = false;
            $scope.material.supply = true;
            $scope.material.type = SUPPLY;
        }

        if ($scope.material.id)
            $http.put('materials/' + $scope.material.id, $scope.material)
                .then(function (response) {
                    $("#material").modal("hide");
                    $scope.get();
                });
        else
            $http.post('materials', $scope.material)
                .then(function (response) {
                    $("#material").modal("hide");
                    $scope.get();
                });
    };
    $scope.delete = function (material) {
        $http.delete('materials/' + material.id)
            .then(function (response) {
                $scope.get();
            });
    };

    $scope.get();
}]);

app.controller('ProductsController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    $scope.products = {};
    $scope.product = {};
    $scope.consult = 0;
    $scope.editing = false;

    $scope.submit = function () {
        $http.post('products', $scope.product)
            .then(function (response) {
                if (response.status === 200) {
                    alert("producto creado");
                }
                else {
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
                        {title: 'id', field: 'id', visible: true},
                        {title: 'Nombre', field: 'name', visible: true},
                        {title: 'Existencias', field: 'inventory', visible: true},
                        {title: 'Precio', field: 'price', visible: true}
                    ];
                }
                else {
                    alert("No existen productos en el sistema");
                }
            });
    }
    $scope.getProduct = function () {
        $http.get('products/' + $scope.consult)
            .then(function (response) {

                if (response.status === 200) {
                    $scope.pToUpdate = response.data;
                    $scope.editing = true;
                } else {
                    alert("Producto no encontrado");
                    $scope.editing = false;
                }
            })
    }
    $scope.updateProduct = function () {
        $http.put('products/' + $scope.pToUpdate.id, $scope.pToUpdate)
            .then(function (response) {
                if (response.status === 200) {
                    $scope.pToUpdate = {};
                    alert("Producto actualizado");
                }
                else {
                    alert("No se pudo editar el producto");
                }
            });
    }
    $scope.deleteProduct = function () {
        $http.delete('products/' + $scope.pToUpdate.id)
            .then(function (response) {
                if (response.status === 204) {
                    $scope.pToUpdate = {};
                    alert("Producto borrado");
                    $scope.editing = false;
                } else {
                    alert("No se pudo borrar el producto");
                }

            });
    }
}]);

app.controller('SalesController', ['$scope', '$http', function ($scope, $http) {
}]);

app.controller('ProductionController', ['$scope', '$http', function ($scope, $http) {
}]);

app.controller('controlUsersController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    $scope.allUsers = {};
    $scope.userPassword = {password: ""};
    $scope.typeUser = "";
    $scope.existUser = false;
    $scope.checkPassword = "";

    $scope.submit = function () {
        $http.get('users/' + $scope.userPassword.user.username)
            .then(function (userResponse) {
                $scope.existUser = userResponse.status === 200;
                if (!$scope.existUser) {
                    $scope.userPassword.user.admin = ($scope.typeUser === "admin");
                    $scope.userPassword.user.worker = ($scope.typeUser === "worker");
                    $scope.userPassword.user.salesman = ($scope.typeUser === "salesman");
                    $http.post('users', $scope.userPassword)
                        .then(function (response) {
                            if (response.status === 200) {
                                alert("El usuario se creo correctamente")
                                $scope.newuser.$setPristine();
                                $scope.userPassword = {password: ""};
                                $scope.typeUser = "";
                                $scope.checkPassword = "";
                            } else {
                            }
                        });
                }
            });

    }
    $scope.getUsers = function () {
        $http.get('users')
            .then(function (response) {
                if (response.status === 200) {
                    //console.log(response.data);
                    $scope.allUsers = response.data;

                    $scope.columns = [
                        {title: 'Borrar', field: 'delete', visible: true},
                        {title: 'Id', field: 'id', visible: true},
                        {title: 'Nombre', field: 'name', visible: true},
                        {title: 'Nombre de usuario', field: 'username', visible: true},
                        {title: 'Correo electronico', field: 'email', visible: true},
                        {title: 'Cuenta de administrador', field: 'isAdmin', visible: true},
                        {title: 'Cuenta de empleado', field: 'isWorker', visible: true},
                        {title: 'Cuenta de vendedor', field: 'isSalesman', visible: true}
                    ];

                }
                else {

                }
            });
    }
}]);

app.config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'partials/home.html',
            controller: 'HomeController'
        })
        .when('/me', {
            templateUrl: 'partials/me.html',
            controller: 'UserController'
        })
        .when('/admin', {
            templateUrl: 'partials/admin.html',
            controller: 'AdminController'
        })
        .when('/materials', {
            templateUrl: 'partials/materials.html',
            controller: 'MaterialController'
        })
        .when('/products', {
            templateUrl: 'partials/products.html',
            controller: 'ProductsController'
        })
        .when('/users', {
            templateUrl: 'partials/users.html',
            controller: 'controlUsersController'
        })
        .when('/sales', {
            templateUrl: 'partials/sales.html',
            controller: 'SalesController'
        })


        .otherwise({redirectTo: '/'});

    //html5mode causes several issues when the front end is embedded with the web service.
    //$locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
}]);
