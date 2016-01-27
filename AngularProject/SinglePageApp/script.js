var app=angular.module('single-page-app',['ngRoute']);


app.config(function($routeProvider){


      $routeProvider
          .when('/',{
                templateUrl: 'biographies.html'
          })
		  
		  .when('/deniro',{
                templateUrl: 'deniro.html'
          })
		  
		   .when('/brando',{
                templateUrl: 'brando.html'
          })

          .when('/nina',{
                templateUrl: 'nina.html'
          });

});


app.controller('cfgController',function($scope){

      $scope.message="Hello world";

});
