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
            setCSRFToken: setCSRFToken,
            unprotectedCall: unprotectedCall,
            protectedCall: protectedCall,
            fakeCSRF: fakeCSRF
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
        
        function unprotectedCall(){
            return $http(prepareGetJSON('demo/unprotected'),'');
        }
        
        
        function protectedCall(){
            return $http(prepareFormPost('demo/protected'),'');
        }
        
        function fakeCSRF(){
            return $http({
                method: 'POST',
                url: '/MyOldBooks-war/rs/demo/protected',
                data: '',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'csrf': 'fakeToken'
                }
            });
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
                    'Content-Type': 'application/json'
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


