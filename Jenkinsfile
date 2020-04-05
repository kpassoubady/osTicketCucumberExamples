node('win') {

    def mvnHome
    def javaHome

    stage('Gather Test Input') {
        // Set Parameters
        properties([[$class: 'JiraProjectProperty'],
        buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '30', numToKeepStr: '20')),
        [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false],
        [$class: 'JobRestrictionProperty'],
        parameters([[$class: 'ExtensibleChoiceParameterDefinition', choiceListProvider: [$class: 'TextareaChoiceListProvider', addEditedValue: false, choiceListText: '''http://osticket.kavinschool.com/
https://plusresources.org/osticket/
''', defaultChoice: 'http://osticket.kavinschool.com/'], description: '<span style="color: blue;">Provide the Home URL of osTicket </span>', editable: false, name: 'HOME_URL'],
        string(defaultValue: '@P2-NIPS_TEST', description: '<span style="color: blue;">Provide comma separated value of cucumber tags (make sure to add @ symbol in front of tags)</span>', name: 'CUCUMBER_TAGS', trim: false),
        string(defaultValue: 'master', description: '<span style="color: blue;">Provide a valid git branch</span>', name: 'BRANCH', trim: false),  ]),
         [$class: 'ThrottleJobProperty', categories: [], limitOneJobWithMatchingParams: false, maxConcurrentPerNode: 2, maxConcurrentTotal: 2, paramsToUseForLimit: '', throttleEnabled: true, throttleOption: 'project'],
         pipelineTriggers([cron('0 23 * * *')])])
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
                "PATH=$javaHome\bin:$PATH"
        ]) {
            bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean test -Dcucumber.options=\"--tags ${CUCUMBER_TAGS}/)
        }
    }

    stage('Publish Test Results') {
        // Publish results
        archiveArtifacts allowEmptyArchive: true, artifacts: 'target/*.jar', fingerprint: true, onlyIfSuccessful: true
        publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'target/cucumber-reports', reportFiles: 'index.html', reportName: 'Screenshot Simple Report', reportTitles: ''])
        cucumber expandAllSteps: true, failedFeaturesNumber: -1, failedScenariosNumber: -1, failedStepsNumber: -1, fileExcludePattern: 'classes/*.json', fileIncludePattern: '**/cucumber-report.json', hideEmptyHooks: true, mergeFeaturesById: true, skipEmptyJSONFiles: true, jsonReportDirectory: 'target', pendingStepsNumber: -1, skippedStepsNumber: -1, sortingMethod: 'ALPHABETICAL', undefinedStepsNumber: -1
    }

}