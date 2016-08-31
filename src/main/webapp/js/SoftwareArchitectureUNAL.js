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
                for (var r in $scope.user.credentials.roles) {
                    switch ($scope.user.credentials.roles[r]) {
                        case 'ADMIN':
                            $scope.user.admin = true;
                            break;
                        case 'WORKER':
                            $scope.user.worker = true;
                            break;
                        case 'SELLER':
                            $scope.user.seller = true;
                            break;
                    }
                }
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
                $scope.me();
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
                for (var r in $scope.user.credentials.roles) {
                    switch ($scope.user.credentials.roles[r]) {
                        case 'ADMIN':
                            $scope.user.admin = true;
                            break;
                        case 'WORKER':
                            $scope.user.worker = true;
                            break;
                        case 'SELLER':
                            $scope.user.seller = true;
                            break;
                    }
                }
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

    const URI = 'materials';
    const MODAL = '#material';
    const RAW = "Materia Prima";
    const SUPPLY = "Insumo";

    $scope.reload = function () {
        $http.get(URI)
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

        $(MODAL).modal('show');
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
            $http.put(URI + '/' + $scope.material.id, $scope.material)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                });
        else
            $http.post(URI, $scope.material)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                });
    };
    $scope.delete = function (material) {
        $http.delete(URI + '/' + material.id)
            .then(function () {
                $scope.reload();
            });
    };

    $scope.reload();
}]);

app.controller('ProductsController', ['$scope', '$http', function ($scope, $http) {
    $scope.products = {};
    $scope.product = {};

    const URI = 'products';
    const MODAL = '#product';

    $scope.reload = function () {
        $http.get(URI)
            .then(function (response) {
                $scope.products = response.data;
            });
    };
    $scope.edit = function (product) {
        $scope.product = product;
        $(MODAL).modal('show');
    };
    $scope.submit = function () {
        if ($scope.product.id)
            $http.put(URI + '/' + $scope.product.id, $scope.product)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                });
        else
            $http.post(URI, $scope.product)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                });
    };
    $scope.delete = function (product) {
        $http.delete(URI + '/' + product.id)
            .then(function () {
                $scope.reload();
            });
    };

    $scope.reload();
}]);

app.controller('CreditsController', ['$scope', '$http', function ($scope, $http) {
    $scope.credits = {};
    $scope.credit = {};

    const URI = 'credits';
    const MODAL = '#credit';

    $scope.reload = function () {
        $http.get(URI)
            .then(function (response) {
                $scope.credits = response.data;
            });
    };
    $scope.edit = function (credit) {
        $scope.credit = credit;
        $(MODAL).modal('show');
    };
    $scope.submit = function () {
        if ($scope.credit.id)
            $http.put(URI + '/' + $scope.credit.id, $scope.credit)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                });
        else
            $http.post(URI, $scope.credit)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                });
    };
    $scope.delete = function (credit) {
        $http.delete(URI + '/' + credit.id)
            .then(function () {
                $scope.reload();
            });
    };

    $scope.reload();
}]);

app.controller('SalesController', ['$scope', '$http', function ($scope, $http) {
}]);

app.controller('ProductionController', ['$scope', '$http', function ($scope, $http) {
}]);

app.controller('UsersController', ['$scope', '$http', function ($scope, $http) {
    $scope.users = {};
    $scope.user = {};

    const URI = 'users';
    const MODAL = '#user';

    $scope.reload = function () {
        $http.get(URI)
            .then(function (response) {
                    $scope.users = response.data;
                    for (var u in $scope.users) {
                        var user = $scope.users[u];
                        user.id = u;
                        for (var r in user.roles) {
                            switch (user.roles[r]) {
                                case 'ADMIN':
                                    user.admin = true;
                                    break;
                                case 'WORKER':
                                    user.worker = true;
                                    break;
                                case 'SELLER':
                                    user.seller = true;
                                    break;
                            }
                        }
                    }
                }
            );
    }
    ;
    $scope.edit = function (user) {
        $scope.user = user;
        $(MODAL).modal('show');
    };
    $scope.submit = function () {
        $scope.user.roles=[];
        if($scope.user.admin)
            $scope.user.roles.push('ADMIN');
        if($scope.user.worker)
            $scope.user.roles.push('WORKER');
        if($scope.user.seller)
            $scope.user.roles.push('SELLER');
        
        if ($scope.user.id)
            $http.put(URI + '/' + $scope.user.username, $scope.user)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                });
        else
            $http.post(URI, $scope.user)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                });
    };
    $scope.delete = function (user) {
        $http.delete(URI + '/' + user.username)
            .then(function () {
                $scope.reload();
            });
    };

    $scope.reload();
}])
;

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
            controller: 'UsersController'
        })
        .when('/credits', {
            templateUrl: 'partials/credits.html',
            controller: 'CreditsController'
        })
        .when('/sales', {
            templateUrl: 'partials/sales.html',
            controller: 'SalesController'
        })
        .when('/production', {
            templateUrl: 'partials/production.html',
            controller: 'ProductionController'
        })


        .otherwise({redirectTo: '/'});

    //html5mode causes several issues when the front end is embedded with the web service.
    //$locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
}]);
