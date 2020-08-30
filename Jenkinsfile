pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven-3.x"		
    }
	
	environment {
		registry = "gcr.io/my-project-1/sample-microservice"
		registryCredential = 'gcr-cred'
		dockerImage = ''
		PROJECT_ID = 'my-project-1'
        CLUSTER_NAME = 'my-cluster-1'
        LOCATION = 'europe-west1-b'
        CREDENTIALS_ID = 'gcr-cred'
	}	

    stages {
        stage('Preparing Package') {
            steps {
                // Get some code from a GitHub repository
                git credentialsId: 'KarthikDev-Github', url: 'https://github.com/KarthikDev/sample-microservice.git'

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
		stage('Building image') {
			steps{
				script {
					dockerImage = docker.build registry + ":$BUILD_NUMBER"
				}
			}
		}
		stage('Push image') {   
			steps{	
				script{	
					docker.withRegistry('https://gcr.io', 'gcr:gcr-cred') {
					dockerImage.push()
					}		  
				}
			}
		}	
		/*stage('Deploy to GKE') {
            steps{
                sh "sed -i dockerImage deployment.yml"
                step([$class: 'KubernetesEngineBuilder', projectId: env.PROJECT_ID, clusterName: env.CLUSTER_NAME, location: env.LOCATION, manifestPattern: 'deployment.yml', credentialsId: env.CREDENTIALS_ID, verifyDeployments: true])
            }
        }*/

	}
}

