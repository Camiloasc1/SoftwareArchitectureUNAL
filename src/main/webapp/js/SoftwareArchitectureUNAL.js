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
        .when('/users', {
            templateUrl: 'partials/users.html',
            controller: 'controlUsersController'
        })
        .when('/sales', {
            templateUrl: 'partials/sales.html',
            controller: 'SalesController'
        })
        .when('/product', {
            templateUrl: 'partials/product.html',
            controller: 'ProductController'
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

                    if ($scope.materialToEdit.supply) $scope.type_edit = "supply";
                    else $scope.type_edit = "raw";

                    $scope.editing = true;
                } else {
                    alert("Material no encontrado");
                    $scope.editing = false;
                }
            })
    }

    $scope.edit = function () {

        if ($scope.type_edit === "raw") {
            $scope.materialToEdit.supply = false;
            $scope.materialToEdit.rawMaterial = true;
        } else {
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

app.controller('ProductController', ['$scope', '$http', '$location', function ($scope, $http, $location) {
    $scope.product = {};
    $scope.products = {};
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