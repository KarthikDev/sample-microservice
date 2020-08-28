pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven-3.x"		
    }
	
	environment {
		registry = "karthikdev0312/springboot-service"
		registryCredential = 'dockerhub'
		dockerImage = ''
		PROJECT_ID = 'On-Prem-Devops'
        CLUSTER_NAME = 'karthikdev0312-cluster-1'
        LOCATION = 'europe-west1-b'
        CREDENTIALS_ID = 'On-Prem-Devops'
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
					docker.withRegistry( '', registryCredential ) {
					dockerImage.push()
					}		  
				}
			}
		}	
		stage('Deploy to GKE') {
            steps{
                sh "sed -i 's/dockerImage/g' deployment.yml"
                step([$class: 'KubernetesEngineBuilder', projectId: env.PROJECT_ID, clusterName: env.CLUSTER_NAME, location: env.LOCATION, manifestPattern: 'deployment.yml', credentialsId: env.CREDENTIALS_ID, verifyDeployments: true])
            }
        }

	}
}

