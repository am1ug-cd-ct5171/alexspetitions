pipeline{
    agent any
    stages{
        stage('Check Last Commit') {
                    steps {
                        script {
                            // Get last commit message
                            def lastCommitMsg = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()
                            echo "Last commit message: ${lastCommitMsg}"

                            // Check if it matches the version bump pattern
                            if (lastCommitMsg.startsWith("Increment version to")) {
                                echo "Last commit was a version bump. Skipping the rest of the pipeline."
                                currentBuild.result = 'SUCCESS'
                                env.IS_JENKINS_COMMIT = 'true'
                                error("Skipping pipeline due to version bump commit")
                            }
                        }
                    }
        }
        stage('Increment Version') {
                    when {
                        expression { env.IS_JENKINS_COMMIT != 'true' }
                    }
                    steps {
                        script {
                            def version = sh(
                                script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout",
                                returnStdout: true
                            ).trim()

                            echo "Current version: ${version}"

                            def versionWithoutSuffix = version.replaceAll(/-(SNAPSHOT|RC\d+|RELEASE).*$/, '')

                            echo "Version without suffix: ${versionWithoutSuffix}"

                            def parts = versionWithoutSuffix.tokenize('.')

                            if (parts.size() != 3) {
                                error("Invalid version format: ${version}. Expected format: MAJOR.MINOR.PATCH[-SUFFIX]")
                            }

                            def major = parts[0] as int
                            def minor = parts[1] as int
                            def patch = parts[2] as int

                            minor += 1
                            patch = 0

                            def newVersion = "${major}.${minor}.${patch}"

                            echo "New version will be: ${newVersion}"

                            sh "mvn versions:set -DnewVersion=${newVersion}"
                            sh "mvn versions:commit"

                            env.NEW_VERSION = newVersion
                        }
                    }
                }
        stage('Build'){
            when {
                expression { env.IS_JENKINS_COMMIT != 'true' }
            }
            steps{
                sh 'mvn clean package'
            }
        }
        stage('Code Coverage') {
                    when {
                         expression { env.IS_JENKINS_COMMIT != 'true' }
                    }
                    steps {
                        sh 'mvn jacoco:report'
                        archiveArtifacts artifacts: 'target/site/jacoco/index.html', allowEmptyArchive: true
                    }
        }
    }
    post{
            success{
                script {
                    if (env.IS_JENKINS_COMMIT == 'true') {
                        echo "Pipeline was skipped due to version bump commit. No further actions taken."
                        return
                    }
                    echo "Pipeline succeeded. Committing version ${env.NEW_VERSION}"

                    withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_TOKEN')]) {
                        sh """
                            git config user.email "jenkins@ci"
                            git config user.name "Jenkins"

                            git add pom.xml
                            git commit -m "Increment version to ${env.NEW_VERSION}" || echo "No changes to commit"

                            # Extract repository URL without protocol
                            REPO_URL=\$(echo "${scm.userRemoteConfigs[0].url}" | sed 's|https://||' | sed 's|http://||')

                            git push https://${GITHUB_TOKEN}@\${REPO_URL} HEAD:refs/heads/main
                        """
                    }

                    echo "Successfully pushed version ${env.NEW_VERSION}"

                    archiveArtifacts allowEmptyArchive: true, artifacts: '**/alexpetitions-*.war'
                }
            }
            failure{
                echo 'Pipeline failed — version increment will NOT be committed.'
            }
        }
}