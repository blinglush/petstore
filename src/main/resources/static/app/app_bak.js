'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'ui.router',
    'myApp.view1',
    'myApp.view2',
    'myApp.add',
    'myApp.version'
]).
config(['$locationProvider', '$routeProvider','$stateProvider', function ($locationProvider, $routeProvider) {
    $locationProvider.hashPrefix('!');

    $routeProvider.otherwise({redirectTo: '/view1'});
}]);
