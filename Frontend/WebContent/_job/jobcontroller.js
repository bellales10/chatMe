app.controller('JobController',function($scope,$location,JobService) {
	
	$scope.job={jobTitle:'',jobDescription:'',skillsRequired:'',salary:'',location:''}
	
	$scope.showJobDetail=false;
	$scope.saveJob=function() {
	  console.log('entering save job in job controller')	
	  JobService.saveJob($scope.job)
	  
	    .then(function(response) {
	    	  console.log("Successfully inserted job details");
	    	  alert("job posted successfully");
	    	  $location.path('/home');
	    	
	    },  function(response) {
	    	  console.log("failure" +response.status);
	    	    if(response.status==401)
	    	      {
	    	    	$location.path('/login')
	   	    	  }
	    	    else
	    	      {
	    	    	console.log(response.data.message)
	    	    	$location.path('/postJob')
	    	      }
	    })
	}
	
	function getAllJobs(){
		$scope.showJobDetail=false;
		console.log('entering get All jobs')
		JobService.getAllJobs()
		.then(function(response){
			console.log(response.status); //200
			$scope.jobs=response.data;  //List<Job>
			
		},function(response){
			console.log(response.status)
		})
	}
	getAllJobs();
	
	$scope.getJobDetail=function(jobId){
		$scope.showJobDetail=true;
		JobService.getJobDetail(jobId)
	.then(function(response){
		$scope.jobDetail=response.data; // single Job object
		console.log(response.status)
		
	},function(response){
		console.log(response.status)
	})
	}	

})