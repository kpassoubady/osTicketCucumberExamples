node('win') {

    def mvnHome
    def javaHome

    stage('Gather Test Input') {
        // Set Parameters

    }

    stage('Git Checkout') {
        // Checkout code from repository
        git credentialsId: 'kpassoubady_git_ssh', url: 'git@github.com:kpassoubady/osTicketCucumberExamples.git'
        mvnHome = tool 'MVN-WIN'
        javaHome = tool 'JDK11-WIN'
    }

    stage('Build & Run Tests') {

        withEnv([
                "MVN_HOME=$mvnHome",
                "JAVA_HOME=$javaHome",
                "CUCUMBER_TAGS=title-check",
                "PATH=$javaHome\bin:$PATH"
        ]) {
            bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean test -Dcucumber.options=\"--tags ${CUCUMBER_TAGS}\" /)
        }
    }

    stage('Publish Test Results') {
        // Publish results
        archiveArtifacts allowEmptyArchive: true, artifacts: 'target/*.jar', fingerprint: true, onlyIfSuccessful: true
        publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'target/cucumber-reports', reportFiles: 'index.html', reportName: 'Screenshot Simple Report', reportTitles: ''])
        cucumber expandAllSteps: true, failedFeaturesNumber: -1, failedScenariosNumber: -1, failedStepsNumber: -1, fileExcludePattern: 'classes/*.json', fileIncludePattern: '**/cucumber-report.json', hideEmptyHooks: true, mergeFeaturesById: true, skipEmptyJSONFiles: true, jsonReportDirectory: 'target', pendingStepsNumber: -1, skippedStepsNumber: -1, sortingMethod: 'ALPHABETICAL', undefinedStepsNumber: -1
    }

}