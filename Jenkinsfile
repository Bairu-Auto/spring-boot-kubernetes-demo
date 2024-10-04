pipeline {
    agent any

    environment {
        JAVA_HOME = "/opt/java/openjdk" // Optional: Define the path to the Java version you want to use
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    def branchName = env.GIT_BRANCH ?: 'main' // Fallback to 'main' if GIT_BRANCH is not set
                    checkout([$class: 'GitSCM',
                        branches: [[name: branchName]],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [],
                        userRemoteConfigs: [[url: 'https://github.com/Bairu-Auto/spring-boot-kubernetes-demo.git', refspec: '+refs/heads/*:refs/remotes/origin/*']]
                    ])
                }
            }
        }

        stage('Set Gradlew Permissions') {
            steps {
                // Ensure gradlew is executable
                sh 'chmod +x ./gradlew'
            }
        }

        stage('Build') {
            steps {
                // Run the Gradle build
                sh './gradlew build'
            }
        }

        stage('Test') {
            steps {
                // Run tests (if needed)
                sh './gradlew test'
            }
            post {
                always {
                    junit 'build/test-results/**/*.xml' // Adjust to the path where test results are generated
                }
            }
        }

        stage('Allure Report') {
            steps {
                allure([
                    results: [[path: 'build/allure-results']],
                    reportBuildPolicy: 'ALWAYS'
                ])
            }
        }

        stage('Archive Artifacts') {
            steps {
                // Archive build artifacts (optional)
                archiveArtifacts artifacts: 'build/libs/*.jar', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            cleanWs() // Clean up after the build
        }
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
