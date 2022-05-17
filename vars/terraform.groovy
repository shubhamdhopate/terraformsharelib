def call(Configuration) {
checkoutCode()
inializeParams(Configuration)
terrafromInit(config.codepath)
terraformValidate(config.codepath)
terraformPlan(config.codepath)
terraformAction(config.codepath)
 
slackNotification("${config.slack_channel}")
}

def checkoutCode() {
    stage("Checking out code repository") {
        checkout scm
    }
}

def inializeParams(Configuration) {
 
        stage("Initializing The Project Parameters and Properties") {
        
         config = readProperties file: "${Configuration}"
            echo "**************** Contents of Configuration ****************"
            echo "${config}"
       
   }
}



 def terrafromInit(codePath){
      stage("terraform init") 
       { 
        dir ("${WORKSPACE}"/"${codePath}") {
       
            sh 'terraform init'
       }
 }
 }
def terraformValidate(codePath){
      stage("terraform validate") 
      {        dir("${WORKSPACE}"/"${codePath}") {
            sh 'terraform  validate'
      }
 }
 }
def terraformPlan(codePath){
        stage("terraform plan") 
      {   dir("${WORKSPACE}"/"${codePath}") {
          sh 'terraform  plan '
         
          }

  }
  
  }

  
def terraformAction(codePath){
          stage("terraform apply") 
        {    dir("${WORKSPACE}"/"${codePath}") {
          
                   sh 'terraform apply  -auto-approve'
                }

          
        }
       
        }

 def slackNotification(channel){
    stage("slack notification") {   
 slackSend(channel:"${channel}" , message:"job is successful '${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|>)'")
    }
}
