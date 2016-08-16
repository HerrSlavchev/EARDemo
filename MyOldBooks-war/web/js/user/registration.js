/* 
 * Controller for registration form
 */


(function(){
    'use strict';
    
    function RegistrationCtrl(DataService,$q){
        
        var vm = this;
        vm.formData = {
            fName: '',
            sName: '',
            lName: '',
            nickname: '',
            pass: ''
        };
        vm.register = register;
        
        function register(){
            DataService.register(vm.formData).then(function(response){
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
    RegistrationCtrl.$inject = ['DataService','$q'];
    
    angular
    .module('myOldBooksApp')
    .controller('RegistrationCtrl', RegistrationCtrl);
})();
