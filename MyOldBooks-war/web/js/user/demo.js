/* 
 * Just for demo purposes
 */


(function(){
    'use strict';
    
    function DemoCtrl(DataService,$q){
        
        var vm = this;
        vm.unprotectedCall = unprotectedCall;
        vm.protectedCall = protectedCall;
        vm.fakeCSRF = fakeCSRF;
        
        function unprotectedCall(){
            DataService.unprotectedCall().then(function(response){
                        if (typeof response.data === 'object') {
                            console.log(response.data);
                        } else {
                            $q.reject(response.data);
                        }
                    }, function(response) {
                        $q.reject(response.data);
                    });
        }
        
        function protectedCall(){
            DataService.protectedCall().then(function(response){
                        if (typeof response.data === 'object') {
                            console.log(response.data);
                        } else {
                            $q.reject(response.data);
                        }
                    }, function(response) {
                        $q.reject(response.data);
                    });
        }
        
        function fakeCSRF(){
            DataService.fakeCSRF().then(function(response){
                        if (typeof response.data === 'object') {
                            console.log(response.data);
                        } else {
                            $q.reject(response.data);
                        }
                    }, function(response) {
                        $q.reject(response.data);
                    });
        }
        
    }
    DemoCtrl.$inject = ['DataService','$q'];
    
    angular
    .module('myOldBooksApp')
    .controller('DemoCtrl', DemoCtrl);
})();
