def call() {

inializeParams(config.codepath)
terrafromInit()
terraformValidate(config.codepath)
terraformPlan(config.codepath)
terraformAction(config.codepath)
 
slackNotification("${config.slack_channel}")
}


def inializeParams() {
 
        stage("Initializing The Project Parameters and Properties") {
            checkout scm
            config = readProperties file: 'Configuration'
            echo "**************** Contents of Configuration ****************"
            echo "${config}"
       
   }
}



 def terrafromInit(codePath){
      stage("terraform init") 
       { 
          dir("${codePath}") {
       
            sh 'terraform init init'
       }
 }
 }
def terraformValidate(codePath){
      stage("terraform validate") 
      {        dir("${codePath}") {
            sh 'terraform  validate'
      }
 }
 }
def terraformPlan(codePath){
        stage("terraform plan") 
      {   dir("${codePath}") {
          sh 'terraform  plan '
         
          }

  }
  
  }

  
def terraformAction(codePath){
          stage("terraform apply") 
        {    dir("${codePath}") {
          
                   sh 'terraform apply  -auto-approve'
                }

          
        }
       
        }

 def slackNotification(channel){
    stage("slack notification") {   
 slackSend(channel:"${channel}" , message:"job is successful '${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|>)'")
    }
}
