//pipeline {
//    agent {
//        node {
//            label 'linux_slaves'
//        }
//    }
//    options {
//        timestamps()
//        buildDiscarder(logRotator(daysToKeepStr: '21', numToKeepStr: '50'))
//        disableConcurrentBuilds()
//    }
//    tools {
//        maven 'Maven_3.9.4' //Tool name from Jenkins configuration
//    }
//    environment {
//        GIT_REPO_URL = 'ssh://git@bitbucket.audatex.com:7999/qat/sg-qa-autoonline-mx.git'
//        GIT_USERID = 'GDC-Git-SSH-Key' //User ID from Jenkins configuration
//        NEXUS_VERSION = 'nexus2'
//        NEXUS_PROTOCOL = 'https'
//        NEXUS_URL = 'nexus.audatex.com/nexus'
//        NEXUS_USERID = 'ConcourseNexusUser' //User ID from Jenkins configuration
//        NEXUS_REPO = 'releases'
//    }
//    parameters {
//        string(name: 'BRANCH_NAME', defaultValue: 'feature/AXNTT-27903-aomx-add-property-files', description: 'Specify branch name to checkout, mandatory field')
//         //listGitBranches(branchFilter: '*', defaultValue: 'develop', name: 'BRANCH_NAME', type: 'BRANCH', remoteURL: 'ssh://git@bitbucket.audatex.com:7999/qat/sg-qa-taf.git', credentialsId: 'GDC-Git-SSH-Key')
//     	//gitParameter name: 'BRANCH_NAME', branchFilter: 'origin/(.*)', defaultValue: 'develop',  selectedValue: 'DEFAULT', sortMode: 'ASCENDING', type: 'PT_BRANCH'
//         choice(name: 'TEST_SUITE', choices: ['autoonline:web:regression',
//                                              'autoonline:web:debug'
//                                             ], description: 'Specify test suite to run, mandatory field')
//       string(name: 'TESTRUN_NAME', defaultValue: 'FEATURE_BRANCH_TEST',
//               description: 'Test run name for the report, to be used in TestRail, optional field. If not defined, test run name will be generated automatically.')
//   }
//   stages {
//   	stage("Build init") {
//        steps {
//            script {
//                branchName = "${BRANCH_NAME}" //.replaceAll("refs/heads/","")
//            	def BUILD_TRIGGER_BY = "${currentBuild.getBuildCauses()[0].shortDescription} [${currentBuild.getBuildCauses()[0].userId}]".replace("Started by user ", "")
//            	currentBuild.displayName = "#${BUILD_NUMBER}-${branchName}"
//                currentBuild.description =
//                """
//                Triggered by: ${BUILD_TRIGGER_BY}
//                <br>
//                <br>Branch: ${branchName}
//                """
//           }
//        }
//    }
//       stage("Download sources") {
//           steps {
//               git branch: "${branchName}", url: "${GIT_REPO_URL}", credentialsId: "${GIT_USERID}"
//           }
//       }
//       stage("Build project") {
//           steps {
//               script {
//                   sh """
//                       mvn clean install -DskipTests=true
//                      """
//               }
//           }
//       }
//         stage("Setup test environment ") {
//             steps {
//                 script {
//
//                 }
//             }
//         }
//        stage("Run tests") {
//            steps {
//                script {
//                    sh """
//                        mvn clean test -P${TEST_SUITE} -DtestRunName=${TESTRUN_NAME}
//                       """
//                }
//            }
//        }
//    }
//     post {
//         always {
//             publishHTML([
//                     allowMissing: false,
//                     alwaysLinkToLastBuild: false,
//                     keepAll: false,
//                     reportDir: 'qapterclaims-gsa/qapterclaims-gsa-web/target/reports',
//                     reportFiles: 'results.html',
//                     reportName: 'ExtentReport',
//                     reportTitles: '',
//                     useWrapperFileDirectly: true])
//             cleanWs()
//         }
//         failure {
//             mail charset: 'UTF-8', mimeType: 'text/html',
//                     subject: "QapterClaims GSA Tests Failed - ${env.JOB_NAME}",
//                     body: "<b>QapterClaims GSA Tests Failed</b>" +
//                             "<br>Job Name: ${env.JOB_NAME} " +
//                             "<br>Build Number: ${env.BUILD_NUMBER} " +
//                             "<br>Build URL: <a href=${env.BUILD_URL}>URL</a> " +
//                             "<br> Branch: ${branchName}.",
//
//                     replyTo: '',
//                     to: "alexander.rabetski@solera.com",
//                     cc: "alexander.rabetski@solera.com",
//                     bcc: ''
//         }
//     }
//}