$(checkForTokenAndSaveInSessionStorage);

function checkForTokenAndSaveInSessionStorage() {
    var searchParams = new URLSearchParams(window.location.search);
    var token = searchParams.get('token');
    if (token) {
        sessionStorage.setItem("token", "Bearer " + token);
        window.location.replace("http://localhost:8080");
    }
}

var app = angular.module('github-analysis-app', ['ngMaterial']);

app.controller('authentication', function ($scope) {
        var currentSession = sessionStorage.getItem("token");
        $scope.isAuthenticated = !!currentSession;
        $scope.signIn = signIn;
        $scope.analyzed = false;
    });

app.controller('currentSessionCtrl', function ($scope, $http) {
    $http({
        method: 'GET',
        url: '/user/me',
        headers: {Authorization: sessionStorage.getItem("token")}
    }).then(function successCallback(response) {
        $scope.data = response.data;
    }, function errorCallback(response) {
    });

    $scope.logOut = logOut;
});

app.controller('analyzeCntrl', function ($scope, $http, $mdDialog) {
    $scope.formData = {};
    $scope.analyze = function () {
        $http({
            method: 'GET',
            url: 'github-analysis/owner/' + $scope.formData.ownerName + '/repository/' + $scope.formData.repositoryName,
            headers: {Authorization: sessionStorage.getItem("token")}
        }).then(function successCallback(response) {
            $scope.analyzedData = response.data;
        }, function errorCallback(response) {
            if (response.data.error) {
                $scope.errorMessage = response.data.message;
            }
        });

        fetchHistory($http, $scope, 0, 60);
    };
    $scope.showAlert = function (response) {
        var content = atob(response.readmeContent);
        $mdDialog.show({
            template: '<div style="padding: 25px;"><h3>Read me</h3> <br>' +
                '<b>URL: </b>' + '<a href="' + response.readmeUrl + '">' + response.readmeUrl +
                '</a>' + '<br> <b>Raw content:</b> <br>' + content + '</div>',
            parent: angular.element("#analyzedDataContainer"),
            clickOutsideToClose: true
        });
    };
    $scope.analyzeAgain = function () {
        $scope.analyzed = false;
        $scope.analyzedData = undefined;
        $scope.history = undefined;
        $scope.errorMessage = undefined;
    }
});

function fetchHistory($http, $scope, page, pageSize) {
    $http({
        method: 'GET',
        url: 'github-analysis/owner/' + $scope.formData.ownerName + '/repository/' + $scope.formData.repositoryName + '/history',
        params: {
            page: page,
            pageSize: pageSize
        },
        headers: {Authorization: sessionStorage.getItem("token")}
    }).then(function successCallback(response) {
        $scope.history = response.data;
    }, function errorCallback(response) {
    });
}

function signIn() {
    var currentSession = sessionStorage.getItem("token");
    if (!currentSession) {
        window.location.replace("http://localhost:8080/oauth2/authorize/github?redirect_uri=http://localhost:8080/");
    }
}

function logOut() {
    sessionStorage.removeItem("token");
    window.location.replace("http://localhost:8080");
}
