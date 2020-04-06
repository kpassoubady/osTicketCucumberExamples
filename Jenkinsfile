node('win') {

    def mvnHome
    def javaHome

    stage('Gather Test Input') {
        // Set Parameters
        properties([
                buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '3', numToKeepStr: '3')),
                [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false],
                parameters([
                        [$class: 'ExtensibleChoiceParameterDefinition', choiceListProvider: [$class: 'TextareaChoiceListProvider', addEditedValue: false, choiceListText: '''http://osticket.kavinschool.com/
https://plusresources.org/osticket/
''', defaultChoice: 'http://osticket.kavinschool.com/'], description: '<span style="color: blue;">Provide the Home URL of osTicket </span>', editable: false, name: 'HOME_URL'],
                        string(defaultValue: '@title-check', description: 'Please provide cucumber tags (comma separated values)', name: 'CUCUMBER_TAGS', trim: true),
                        string(defaultValue: 'master', description: '<span style="color: blue;">Provide a valid git branch</span>', name: 'BRANCH', trim: true),
                        string(defaultValue: 'chrome', description: 'Please enter your browser type', name: 'browserType', trim: true)
                ]),
                [$class: 'ThrottleJobProperty', categories: [], limitOneJobWithMatchingParams: false, maxConcurrentPerNode: 2, maxConcurrentTotal: 2, paramsToUseForLimit: '', throttleEnabled: true, throttleOption: 'project'],
                pipelineTriggers([cron('0 23 * * *')])
        ])

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
                "CUCUMBER_TAGS=@title-check",
                "PATH=$javaHome\bin:$PATH"
        ]) {
            bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean test -Dcucumber.options="--tags ${CUCUMBER_TAGS}" /)
        }
    }

    stage('Publish Test Results') {
        // Publish results
        archiveArtifacts allowEmptyArchive: true, artifacts: 'target/*.jar', fingerprint: true, onlyIfSuccessful: true
        publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'target/cucumber-reports', reportFiles: 'index.html', reportName: 'Screenshot Simple Report', reportTitles: ''])
        cucumber expandAllSteps: true, failedFeaturesNumber: -1, failedScenariosNumber: -1, failedStepsNumber: -1, fileExcludePattern: 'classes/*.json', fileIncludePattern: '**/cucumber.json', hideEmptyHooks: true, mergeFeaturesById: true, skipEmptyJSONFiles: true, jsonReportDirectory: 'target', pendingStepsNumber: -1, skippedStepsNumber: -1, sortingMethod: 'ALPHABETICAL', undefinedStepsNumber: -1
    }

}