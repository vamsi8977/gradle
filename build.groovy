pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr:'5' , artifactNumToKeepStr: '5'))
    timestamps()
    }
  stages {
    stage('CheckOut') {
      steps {
        echo 'Checking out project from Bitbucket....'
        cleanWs()
        git(url: 'git@github.com:vamsi8977/gradle.git', branch: 'main')
      }
    }
    stage('Build') {
      steps {
        script {
          sh "gradle clean build"
        }
      }
    }
  }
  post {
    success {
      echo "The build passed."
      archiveArtifacts artifacts: "build/libs/*.jar"
    }
    failure {
      echo "The build failed."
    }
    cleanup {
      deleteDir()
    }
  }
}
