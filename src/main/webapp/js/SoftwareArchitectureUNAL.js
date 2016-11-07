"use strict";
var app = angular.module('SoftwareArchitectureUNAL', ['ngRoute', 'ngMaterial']);

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
                        case 'CLIENT':
                            $scope.user.client = true;
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

    $scope.material = {};
    $scope.materials = [];

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
        else {
            $http.post(URI, $scope.product)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                });
        }
    };
    $scope.delete = function (product) {
        $http.delete(URI + '/' + product.id)
            .then(function () {
                $scope.reload();
            });
    };


    $scope.deleteRecipe = function (recipe) {
        for (var key in $scope.product.recipes) {
            var my_recipe = $scope.product.recipes[key];
            if (my_recipe.id === recipe.id) {
                $scope.product.recipes.splice(key, 1);
                break;
            }
        }
    };

    $scope.searchMaterial = function () {

        if (typeof $scope.product.recipes === "undefined") {
            $scope.product.recipes = [];
        }

        $http.get("materials/name=" + $scope.material.name)
            .then(function (response) {

                if (response.status === 200) {

                    var alreadyPut = false;

                    for (var key in $scope.product.recipes) {
                        var my_recipe = $scope.product.recipes[key];
                        if (my_recipe.material.id === response.data.id) {
                            alreadyPut = true;
                            break;
                        }
                    }

                    if (!alreadyPut) {
                        var recipe = {
                            "material": response.data,
                            "requiredQuantity": 0
                        };

                        $scope.product.recipes.push(recipe);
                    } else {
                        alert("Ya habias agregado ese producto");
                    }


                } else {
                    alert("Material no encontrado");
                }

            });
    };

    $scope.reload();
}]);


app.controller('SalesController', ['$scope', '$http', '$filter', function ($scope, $http, $filter) {
    $scope.sales = {};
    $scope.sale = {};
    $scope.user = {};
    $scope.products = {};
    $scope.quantity = 0;
    $scope.selected = {};


    const URI = 'sales';
    const MODAL = '#sales';

    $scope.reload = function () {
        $http.get('auth/me')
            .then(function (response) {
                $scope.user = response.data;
                $scope.uriUser = ($scope.user.salesman) && !($scope.user.admin) ? '/seller/' + $scope.user.id : '';
                $http.get(URI + $scope.uriUser)
                    .then(function (response) {
                        $scope.sales = response.data;
                        $scope.getProducts();
                    });
            });

    };

    $scope.getSalePrice = function (sale) {
        $scope.sum = 0;
        angular.forEach(sale.saleDetail, function (saleDetail) {
            $scope.sum += saleDetail.price;
        });
        return $scope.sum;
    };

    $scope.getCommission = function (sale) {
        return 0.1 * $scope.getSalePrice(sale);
    };

    $scope.getProducts = function () {
        $http.get('products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };

    $scope.getSaleDetails = function () {
        $http.get(URI + "/" + $scope.sale.id)
            .then(function (response) {
                $scope.sale = response.data;
            });

    };

    $scope.detail = function (sale) {
        $scope.sale = sale;
        $scope.getProducts();
        $scope.getSaleDetails();
        $scope.quantity = 0;
        $scope.selected = {};
        $scope.selected.inventory = 1000;
        $('#detail').modal('show');
    };
    $scope.addProduct = function () {
        $scope.saleDetail = {
            "id": 0,
            "product": $scope.selected,
            "quantity": $scope.quantity,
            "price": ($scope.selected.price * $scope.quantity)
        };
        $http.post(URI + '/SaleDetail/' + $scope.sale.id, $scope.saleDetail)
            .then(function () {
                $scope.reload();
                $scope.getSaleDetails();
            });

        $scope.selected.inventory = $scope.selected.inventory - $scope.quantity;
        $http.put('products' + '/' + $scope.selected.id, $scope.selected)
            .then(function () {
                $scope.reload();
                $scope.getSaleDetails();
            });
    };

    $scope.deleteSaleDetail = function (saleDetail) {
        $http.delete(URI + '/SaleDetail/' + saleDetail.id + '/' + saleDetail.quantity)
            .then(function () {
                $scope.reload();
                $scope.getSaleDetails();
            });
    };

    $scope.new = function () {
        $scope.sale = {"id": 0, "client": "", "seller": $scope.user};
        $scope.sale.date = $filter('date')(new Date(), "yyyy-MM-dd");
        $scope.date = $scope.sale.date;
        $scope.option = false;
        $(MODAL).modal('show');
    };

    $scope.edit = function (sale) {
        $scope.sale = sale;
        $scope.sale.date = $filter('date')($scope.sale.date, "yyyy-MM-dd");
        $scope.date = $scope.sale.date;
        $scope.option = true;
        $(MODAL).modal('show');
    };
    $scope.submit = function () {
        $scope.sale.date = $scope.date + "T05:00:00.000Z";
        if ($scope.option) {
            $http.put(URI + '/' + $scope.sale.id, $scope.sale)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                });
        } else {
            $http.post(URI, $scope.sale)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                });
        }
    };

    $scope.delete = function (id) {
        $http.delete(URI + '/' + id)
            .then(function () {
                $scope.reload();
            });
    };
    $scope.reload();
}]);

app.controller('CreditsController', ['$scope', '$http', function ($scope, $http) {
    $scope.credits = {};
    $scope.credit = {};
    $scope.options = ["Em-Amigable","Em-Robusto","Em-Fiel","Em-Frec"];
    $scope.loading = false;
    $scope.errorMessage = false;

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
        $scope.errorMessage = false;
    };
    $scope.submit = function () {
        $scope.loading = true;
        $scope.errorMessage = false;
        if ($scope.credit.id)
            $http.put(URI + '/' + $scope.credit.id, $scope.credit)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.loading = false;
                    $scope.errorMessage = false;
                    $scope.reeload();
                });
        else {
            $http.post(URI, $scope.credit)
                .then(function () {
                    $(MODAL).modal('hide');
                    $scope.reload();
                    $scope.loading = false;
                    $scope.errorMessage = false;
                }, function (response) {
                    $scope.loading = false;
                    $scope.errorMessage = true;
                    $scope.credit = {};
                });
        };
    };
    $scope.delete = function (credit) {
        $http.delete(URI + '/' + credit.id)
            .then(function () {
                $scope.reload();
            });
    };

    $scope.reload();
}]);

app.controller('StatisticsController', ['$scope', '$http', function ($scope, $http) {
    $scope.sales = {};
    $scope.total = 0;
    $scope.sale = {};

    const URI = 'stats';
    const MODAL = '#details';

    $scope.date = new Date();

    $scope.minDate = new Date(
        $scope.date.getFullYear() - 2,
        $scope.date.getMonth(),
        $scope.date.getDate());

    $scope.maxDate = $scope.date;

    $scope.seeDetails = function (sale) {
        $scope.sale = sale;
        $(MODAL).modal('show');
    };

    $scope.fixDates = function () {
        for (var i = 0; i < $scope.sales.length; i++) {
            $scope.sales[i].date = $scope.sales[i].date.substr(0, 10) + " a las " + $scope.sales[i].date.substr(11, 8);
        }
    }

    $scope.reload = function () {
        var date = $scope.date.getFullYear() + "-" + ($scope.date.getMonth() + 1) + "-" + $scope.date.getDate();
        $http.get("sales/stats/" + date)
            .then(function (response) {
                var ctx = document.getElementById("statistics-box");
                ctx.style.display = "block";
                $scope.sales = response.data;
                console.log($scope.sales);
                $scope.fixDates();
                $scope.total = 0;
                for (var i = 0; i < $scope.sales.length; i++) {
                    var price = 0;
                    for (var j = 0; j < $scope.sales[i].saleDetail.length; j++) {
                        price += $scope.sales[i].saleDetail[j].price;
                    }
                    $scope.sales[i].price = price;
                    $scope.total += price;
                }
                $scope.salesPerSeller();
                $scope.salesPerDay();
                $scope.mostSelled();
            });
    }

    $scope.randomColors = function () {
        var letters = '0123456789ABCDEF'.split('');
        var color = "#";
        for (var i = 0; i < 6; i++)
            color += letters[Math.floor(Math.random() * 16)];
        return color;
    }

    $scope.salesPerSeller = function () {
        var ctx = document.getElementById("myChart");
        var sellers = [];
        var sells = [];
        var colors = [];
        for (var i = 0; i < $scope.sales.length; i++) {
            if (sellers.indexOf($scope.sales[i].seller.name) !== -1) {
                sells[sellers.indexOf($scope.sales[i].seller.name)]++;
            } else {
                sellers.push($scope.sales[i].seller.name);
                sells.push(1);
                var color = "";
                do {
                    color = $scope.randomColors();
                } while (colors.indexOf(color) != -1);
                colors.push(color);
            }
        }
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: sellers,
                datasets: [{
                    label: '# de ventas',
                    data: sells,
                    borderWidth: 1,
                    backgroundColor: colors
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    }

    $scope.salesPerDay = function () {
        var ctx = document.getElementById("myChart2");
        var hours = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        for (var i = 0; i < $scope.sales.length; i++) {
            var hour = parseInt($scope.sales[i].date.substr(17, 2));
            var price = 0;
            var detailLen = $scope.sales[i].saleDetail.length;
            for (var j = 0; j < detailLen; j++) {
                price += $scope.sales[i].saleDetail[j].price;
            }
            hours[hour - 8] += price;
        }

        var data = {
            labels: ["8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00"],
            datasets: [
                {
                    label: "Datos del día",
                    fill: false,
                    lineTension: 0.1,
                    backgroundColor: "rgba(75,192,192,0.4)",
                    borderColor: "rgba(75, 192, 192, 1)",
                    borderCapStyle: 'butt',
                    data: hours
                }
            ]
        }
        var myChart = new Chart(ctx, {
            type: 'line',
            data: data,
            options: {}
        });
    }

    $scope.mostSelled = function () {
        var ctx = document.getElementById("myChart3");
        var products = [];
        var prices = [];
        var quantities = [];
        var colors = [];
        for (var i = 0; i < $scope.sales.length; i++) {
            for (var j = 0; j < $scope.sales[i].saleDetail.length; j++) {
                if (products.indexOf($scope.sales[i].saleDetail[j].product.name) == -1) {
                    products.push($scope.sales[i].saleDetail[j].product.name);
                    prices.push($scope.sales[i].saleDetail[j].product.price);
                    quantities.push(1);
                    var color = "";
                    do {
                        color = $scope.randomColors();
                    } while (colors.indexOf(color) != -1);
                    colors.push(color);
                } else {
                    quantities[products.indexOf($scope.sales[i].saleDetail[j].product.name)]++;
                }
            }
        }
        for (var i = 0; i < quantities.length; i++) {
            quantities[i] = quantities[i] * prices[i];
        }
        console.log(products);
        console.log(prices);
        console.log(quantities);
        var data = {
            labels: products,
            datasets: [
                {
                    data: quantities,
                    backgroundColor: colors
                }
            ]
        };
        var myPieChart = new Chart(ctx, {
            type: 'pie',
            data: data
        });
    }
}]);


app.controller('ProductionController', ['$scope', '$http', function ($scope, $http) {

    $scope.products = {};
    $scope.product = {};
    $scope.user = {};
    $scope.fabrications = {};
    const MODAL = '#product';

    $scope.me = function () {
        $http.get('auth/me')
            .then(function (response) {
                $scope.user = response.data;
            }, function () {
                $scope.user = {};
            });
    };
    $scope.reload = function () {
        $http.get('products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };
    $scope.edit = function (product) {
        $scope.fabrications.product = product;
        $scope.product = product;
        $(MODAL).modal('show');
    };
    $scope.submit = function () {

        var check = false;

        for (var k in $scope.product.recipes) {
            var c = $scope.product.recipes[k];
            if (c.material.inventory < (c.requiredQuantity * $scope.fabrications.quantity)) {
                check = true;
                break;
            }
        }
        if (check === true) {
            alert("No hay suficiente material para la fabricacion del producto");
        }
        else {
            $scope.mQtyToUpdate = {};
            $scope.fabrications.worker = $scope.user;
            $scope.product.inventory = $scope.product.inventory + $scope.fabrications.quantity;
            var updateMaterial = function (m) {
                $http.get('materials/' + m.material.id)
                    .then(function (response) {
                        $scope.mQtyToUpdate = response.data;
                        $scope.mQtyToUpdate.inventory = $scope.mQtyToUpdate.inventory - (m.requiredQuantity * $scope.fabrications.quantity);
                        $http.put('materials/' + m.material.id, $scope.mQtyToUpdate)
                            .then(function () {
                                $scope.mQtyToUpdate = {};
                            });

                    });
            };
            for (var key in $scope.product.recipes) {
                var m = $scope.product.recipes[key];
                updateMaterial(m);
            }
            $http.post('fabrications', $scope.fabrications)
                .then(function () {
                    $(MODAL).modal('hide');
                    //alert("Fabriacion del producto completa");
                    $scope.fabrications = {};
                    $scope.reload();
                });
            $http.put('products/' + $scope.fabrications.product.id, $scope.product)
                .then(function () {
                    $scope.reload();
                });
        }
    };

    $scope.reload();
    $scope.me();
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
                                case 'CLIENT':
                                    user.client = true;
                                    break;
                            }
                        }
                    }
                }
            );
    };
    $scope.edit = function (user) {
        $scope.user = user;
        $(MODAL).modal('show');
    };
    $scope.submit = function () {
        $scope.user.roles = [];
        if ($scope.user.admin)
            $scope.user.roles.push('ADMIN');
        if ($scope.user.worker)
            $scope.user.roles.push('WORKER');
        if ($scope.user.seller)
            $scope.user.roles.push('SELLER');
        if ($scope.user.client)
            $scope.user.roles.push('CLIENT');

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
        .when('/statistics', {
            templateUrl: 'partials/statistics.html',
            controller: 'StatisticsController'
        })

        .otherwise({redirectTo: '/'});

    //html5mode causes several issues when the front end is embedded with the web service.
    //$locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
}]);
