'use strict';

angular.module('myApp.view1', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl',
    controllerAs:'view1'

  });
}])

.controller('View1Ctrl', [function($scope) {
  var vm = this;
  vm.dashboard = "Start Dashboard";

}]);