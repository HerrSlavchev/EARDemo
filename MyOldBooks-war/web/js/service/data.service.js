/* 
 * Provides all data services.
 */

(function () {
    'use strict';

    function DataService($http, $httpParamSerializer) {

        var csrfToken = '';

        return {
            login: login,
            register: register,
            logout: logout,
            setCSRFToken: setCSRFToken
        };

        function login(formData) {
            return $http(prepareFormPost('login', formData));
        }

        function register(formData) {
            return $http(prepareFormPost('register', formData));
        }

        function logout(){
            return $http(prepareFormPost('logout', ''));
        }
        
        function prepareFormPost(subPath, formData) {
            return {
                method: 'POST',
                url: '/MyOldBooks-war/rs/' + subPath,
                data: $httpParamSerializer(formData),
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'csrf': csrfToken
                }
            };
        }

        function prepareGetJSON(subPath, data) {
            return {
                method: 'GET',
                url: '/MyOldBooks-war/rs/' + subPath,
                data: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            };
        }
        
        function setCSRFToken(token) {
            csrfToken = token;
        }
    }
    DataService.$inject = ['$http', '$httpParamSerializer'];

    angular
            .module('myOldBooksApp')
            .service('DataService', DataService);
})();


