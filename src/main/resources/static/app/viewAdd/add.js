'use strict';

angular.module('myApp.add', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/addItem', {
            templateUrl: 'viewAdd/addItem.html',
            controller: 'addControl',
            controllerAs: "addCtrl"
        });
    }])

    .controller('addControl', function ($scope, $http) {
        var vm = this;

        vm.getPet = function() {
            $http.get("http://localhost:8080/pet/3")
                .then(function (response) {
                    $scope.myWelcome = response.data;
                    console.log(response);
                });
        };


    });