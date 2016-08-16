/* 
 * Handles logout procedure
 */


(function(){
    'use strict';
    
    function LogoutCtrl(DataService,$q){
        
        var vm = this;
        vm.logout = logout;
        
        function logout(){
            DataService.logout().then(function(response){
                        if (typeof response.data === 'object') {
                            console.log(response.data);
                            if(response.data.res === 'Successful logout!'){
                                DataService.setCSRFToken('');
                            }
                        } else {
                            $q.reject(response.data);
                        }
                    }, function(response) {
                        $q.reject(response.data);
                    });
        }
    }
    LogoutCtrl.$inject = ['DataService','$q'];
    
    angular
    .module('myOldBooksApp')
    .controller('LogoutCtrl', LogoutCtrl);
})();
