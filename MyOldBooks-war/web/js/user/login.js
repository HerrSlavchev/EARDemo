/* 
 * Controller for login form
 */

(function(){
    'use strict';
    
    function LoginCtrl(DataService,$q){
        
        var vm = this;
        vm.formData = {
            nickname: '',
            pass: ''
        };
        vm.login = login;
        
        function login(){
            DataService.login(vm.formData).then(function(response){
                        if (typeof response.data === 'object') {
                            DataService.setCSRFToken(response.data.res);
                            console.log(response.data);
                        } else {
                            $q.reject(response.data);
                        }
                    }, function(response) {
                        $q.reject(response.data);
                    });
        }
    }
    LoginCtrl.$inject = ['DataService','$q'];
    
    angular
    .module('myOldBooksApp')
    .controller('LoginCtrl', LoginCtrl);
})();
