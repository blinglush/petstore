'use strict';

// Declare app level module which depends on views, and components
var petModule = angular.module('myApp', [
    'ngRoute',
    'ngSanitize',
    'ui.router',
    'ui.select',
    'myApp.view1',
    'myApp.view2',
    'myApp.add',
    'myApp.version'

]).factory("petService", function ($http) {

    var petDataStore = {};
    var abs = 'http://localhost:8080/pet';
    var rel = '/pet';

    petDataStore.doRegistration = function (theData) {
        var promise = $http({method: 'POST', url: abs, data: theData});
        return promise;

    };

    return petDataStore;

}).directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]).service('fileUpload', ['$http','$rootScope', function ($http,$rootScope) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            .success(function(){
                console.log("image uploaded successfully");
                $rootScope.upload=false;
            })
            .error(function(){
            });


    }
}]).config(['$locationProvider', '$routeProvider', '$stateProvider', '$urlRouterProvider', function ($locationProvider, $routeProvider, $stateProvider, $urlRouterProvider) {
    $locationProvider.hashPrefix('!');

    $urlRouterProvider.otherwise('/home');

    $stateProvider.state('home', {
        url: '/home',
        templateUrl: 'view/home.html',
        controller: 'homeController',
        controllerAs: 'homeCtrl'
    }).state('dashboard', {
        url: '/dashboard',
        templateUrl: 'view/dashboard.html',
        controller: 'dashboardController',
        controllerAs: 'dashboardCtrl'

    }).state('add', {
        url: '/add',
        templateUrl: 'view/addItem.html',
        controller: 'addController',
        controllerAs: 'addCtrl'
    }).state('petDetail', {
        url: '/pet/:id',
        templateUrl: 'view/petDetails.html',
        controller: 'petDetailsController',
        controllerAs: 'petDetailCtrl'
    }).state('p-error', {
        url: '/p-error',
        templateUrl: '/view/p-error.html',
        controller: 'petErrorController',
        controllerAs: 'errorCtrl'
    });
}]).controller("homeController", function ($scope) {
    var vm = this;
    vm.dashboard = "Start Dashboard";
});
petModule.filter('propsFilter', function() {
    return function(items, props) {
        var out = [];

        if (angular.isArray(items)) {
            var keys = Object.keys(props);

            items.forEach(function(item) {
                var itemMatches = false;

                for (var i = 0; i < keys.length; i++) {
                    var prop = keys[i];
                    var text = props[prop].toLowerCase();
                    if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            // Let the output be the input untouched
            out = items;
        }

        return out;
    };
});

petModule.controller("dashboardController",function($scope, $http) {
    var vm = this;
    vm.pets = [];

    var dataFromServer = $http({method: 'GET', url: 'http://localhost:8080/pet'});
    dataFromServer.success(function (response) {
        console.log(response);
        vm.pets = response.data;
    });

    vm.deletePet = function(petId) {
        console.log("delete called" + petId);
        var serverResponse = $http({method: "DELETE", url: "/pet/" + petId});
        serverResponse.success(function(response) {

            $http({method: 'GET', url: '/pet'}).success(function(response) {
                vm.pets =response.data;
            });
        })

    };

});

petModule.controller("addController", function ($scope, $rootScope,$http, $log, $location,petService, fileUpload) {


    var vm = this;
    vm.tagsSearchEnabled=true;
    vm.pet = {};
    $scope.pic = [];
    $rootScope.upload=false;

    //vm.status = [{"id": 1, "name": "Available"}, {"id": 2, "name": "Pending"}, {"id": "3", "name": "Sold"}];

    //vm.pet.photoUrls = [{"id":"1","name":"first.jpg"},{"id":"2","name":"second.jpg"}];
    vm.pet.photoUrls = $scope.pic ;

    /*
        vm.pet = {
            "id": 0,
            "name": vm.petName,
            "status": vm.status.statusSelected,

            "category": vm.status.statusSelected ,
            "photoUrls": [
                "first.jpg",
                "second.jpg"
            ],
            "tags": vm.tagsSelected
        }
    */

    var dataFromServer = $http({method: 'GET', url: 'http://localhost:8080/pet/load'});
    dataFromServer.then(function (response) {
        $log.info(response);
        vm.status=response.data.status;
        vm.categories = response.data.categories;
        vm.tags = response.data.tags;
        console.log(vm.pet.categories);
    }, function errorCallback() {

    });

    vm.petEntry = function() {
        var promise = petService.doRegistration(vm.pet);
        promise.success(function(response,status) {
            console.log("pet successfully added");
            $location.path('/dashboard')

        }).error(function(response,status) {
            console.log("error");
        })
    };

    function createGuid()
    {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random()*16|0, v = c === 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        });
    }

    vm.uploadFile = function(){
        $rootScope.upload=true;

        var file = $scope.myFile;

        var hash = createGuid();
        debugger;
        var fileN = hash + '_' + file.name;


        $scope.pic.push(fileN);
        //console.log(hash);
        //console.log("phtourl:" +$scope.pic);
        //console.log('file is ' );
        //console.dir(file);
        var uploadUrl = "/pet/"+hash+"/uploadImage";
        fileUpload.uploadFileToUrl(file, uploadUrl);
    };




});

petModule.controller("petDetailsController", function ($scope,$http, $stateParams) {
    var vm = this;
    debugger;
    var petId = $stateParams.id;
    console.log("petid " + petId);


    var getPet = $http({method: "GET", url: '/pet/'+petId});
    getPet.success(function(response) {
        vm.pet = response;
        vm.pet.tagsa = JSON.stringify(response.petTags);
        console.log("result " + response);
    })



});
petModule.controller("petErrorController",function() {

})
