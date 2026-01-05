pipeline {
    agent any

    stages {

        stage('Checkout Code') {
            steps {
                echo 'Kod çekiliyor...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Build ediliyor...'
                dir('Not-App') {
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Birim testler...'
                dir('Not-App') {
                    bat 'mvn test'
                }
            }
            post {
                always {
                    dir('Not-App') {
                        junit '**/target/surefire-reports/*.xml'
                    }
                }
            }
        }

        stage('Integration Tests') {
            steps {
                echo 'Entegrasyon testleri...'
                dir('Not-App') {
                    bat 'mvn verify'
                }
            }
            post {
                always {
                    dir('Not-App') {
                        junit '**/target/failsafe-reports/*.xml'
                    }
                }
            }
        }

        stage('5 - Run System in Docker') {
            steps {
                echo '========== Docker Compose ile sistem ayağa kaldırılıyor =========='

                powershell(script: '''
                    $ErrorActionPreference = "Stop"

                    Write-Host "== Önceki container'lar temizleniyor =="

                    docker rm -f not-db -ErrorAction SilentlyContinue
                    docker rm -f not-app -ErrorAction SilentlyContinue
                    docker rm -f not-pgadmin -ErrorAction SilentlyContinue

                    Write-Host "== DB ve Backend ayağa kaldırılıyor =="
                    docker compose -f docker-compose.yml up -d --build

                    Write-Host "== Container durumları =="
                    docker compose -f docker-compose.yml ps
                ''')
            }
        }

        stage('Wait for System') {
            steps {
                echo 'Sistem ayaga kalkıyor, 10 saniye bekleniyor...'
                sleep time: 10, unit: 'SECONDS'
            }
        }

                stage('6.1 - UI Test: Register Success') {
                    steps {
                        echo '========== Register Senaryosu =========='
                        dir('Not-App') {
                            bat '''
                                mvn test ^
                                -Dtest=RegisterUITest#shouldRegisterUser
                            '''
                        }
                    }
                    post {
                        always {
                            dir('Not-App') {
                                junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                            }
                        }
                    }
                }

                stage('6.2 - UI Test: Login Fail') {
                    steps {
                        echo '========== Login Fail Senaryosu =========='
                        dir('Not-App') {
                            bat '''
                                mvn test ^
                                -Dtest=LoginWrongUITest#shouldRegisterUserAndThenFailLogin
                            '''
                        }
                    }
                    post {
                        always {
                            dir('Not-App') {
                                junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                            }
                        }
                    }
                }

                stage('6.3 - UI Test: Login Success') {
                    steps {
                        echo '========== Login Success =========='
                        dir('Not-App') {
                            bat '''
                                mvn test ^
                                -Dtest=LoginSuccessUITest#shouldRegisterAndLoginUserSuccessfully
                            '''
                        }
                    }
                    post {
                        always {
                            dir('Not-App') {
                                junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                            }
                        }
                    }
                }

                stage('6.4 - UI Test: Profil Update') {
                     steps {
                        echo '========== Profil Update =========='
                            dir('Not-App') {
                                bat '''
                                    mvn test ^
                                    -Dtest=RegisterLoginProfileUpdateUITest#registerLoginAndUpdateProfile
                                 '''
                                 }
                              }
                              post {
                                always {
                                   dir('Not-App') {
                                         junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                                         }
                                     }
                       }
                }

                stage('6.5 - UI Test: Ders Note Add Test') {
                     steps {
                        echo '========== Ders Not Add =========='
                            dir('Not-App') {
                                bat '''
                                    mvn test ^
                                    -Dtest=DersNotuAddUITest#registerLoginAndAddDersNotu
                                 '''
                                 }
                              }
                              post {
                                always {
                                   dir('Not-App') {
                                         junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                                         }
                                     }
                       }
                }

                stage('6.6 - UI Test: Ders Note Add And Check') {
                     steps {
                        echo '========== Ders Not Add And Check =========='
                            dir('Not-App') {
                                bat '''
                                    mvn test ^
                                    -Dtest=DersNotuAddAndCheckUITest#registerLoginAddDersNotuAndCheckNotlarimThenLogout
                                 '''
                                 }
                              }
                              post {
                                always {
                                   dir('Not-App') {
                                         junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                                         }
                                     }
                       }
                }

                stage('6.7 - UI Test: Ders Add') {
                     steps {
                        echo '========== Ders Add =========='
                            dir('Not-App') {
                                bat '''
                                    mvn test ^
                                    -Dtest=DersAddUITest#registerLoginAddDersAndLogout
                                 '''
                                 }
                              }
                              post {
                                always {
                                   dir('Not-App') {
                                         junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                                         }
                                     }
                       }
                }

                stage('6.8 - UI Test: Begen Add ') {
                     steps {
                        echo '========== Begen Add =========='
                            dir('Not-App') {
                                bat '''
                                    mvn test ^
                                    -Dtest=BegenAddUITest#loginAndLikeAndUnlikeDersNot
                                 '''
                                 }
                              }
                              post {
                                always {
                                   dir('Not-App') {
                                         junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                                         }
                                     }
                       }
                }

                stage('6.9 - UI Test: Begen Add ') {
                     steps {
                        echo '========== Begen List Check =========='
                            dir('Not-App') {
                                bat '''
                                    mvn test ^
                                    -Dtest=BegenListUITest#registerLoginLikeAndGoToFavorilerimAndLogout
                                 '''
                                 }
                              }
                              post {
                                always {
                                   dir('Not-App') {
                                         junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                                         }
                                     }
                       }
                }

    }
}
