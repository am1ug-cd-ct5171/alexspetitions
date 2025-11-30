pipeline{
    agent any
    stages{
        stage('Build'){
            steps{
                sh 'mvn clean package'
            }
        }
        stage('Code Coverage') {
                    steps {
                        sh 'mvn jacoco:report'
                        archiveArtifacts artifacts: 'target/site/jacoco/index.html', allowEmptyArchive: true
                    }
        }
    }
    post{
            success{
                echo 'Success'
                archiveArtifacts allowEmptyArchive: true, artifacts: '**/alexpetitions-*.war'

            }
            failure{
                echo 'Failure'
            }
        }
}