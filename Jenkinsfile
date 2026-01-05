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

        // ===== UI TESTLER =====
        stage('6.1 - UI Test: Register Success') {
            steps {
                dir('Not-App') {
                    bat 'mvn test -Pui-tests -Dtest=RegisterUITest#shouldRegisterUser'
                }
            }
        }

        stage('6.2 - UI Test: Login Fail') {
            steps {
                dir('Not-App') {
                    bat 'mvn test -Pui-tests -Dtest=LoginWrongUITest#shouldRegisterUserAndThenFailLogin'
                }
            }
        }

        stage('6.3 - UI Test: Login Success') {
            steps {
                dir('Not-App') {
                    bat 'mvn test -Pui-tests -Dtest=LoginSuccessUITest#shouldRegisterAndLoginUserSuccessfully'
                }
            }
        }

        stage('6.4 - UI Test: Profil Update') {
            steps {
                dir('Not-App') {
                    bat 'mvn test -Pui-tests -Dtest=RegisterLoginProfileUpdateUITest#registerLoginAndUpdateProfile'
                }
            }
        }

        stage('6.5 - UI Test: Ders Note Add') {
            steps {
                dir('Not-App') {
                    bat 'mvn test -Pui-tests -Dtest=DersNotuAddUITest#registerLoginAndAddDersNotu'
                }
            }
        }

        stage('6.6 - UI Test: Ders Note Add & Check') {
            steps {
                dir('Not-App') {
                    bat 'mvn test -Pui-tests -Dtest=DersNotuAddAndCheckUITest#registerLoginAddDersNotuAndCheckNotlarimThenLogout'
                }
            }
        }

        stage('6.7 - UI Test: Ders Add') {
            steps {
                dir('Not-App') {
                    bat 'mvn test -Pui-tests -Dtest=DersAddUITest#registerLoginAddDersAndLogout'
                }
            }
        }

        stage('6.8 - UI Test: Begen Add') {
            steps {
                dir('Not-App') {
                    bat 'mvn test -Pui-tests -Dtest=BegenAddUITest#loginAndLikeAndUnlikeDersNot'
                }
            }
        }

        stage('6.9 - UI Test: Begen List') {
            steps {
                dir('Not-App') {
                    bat 'mvn test -Pui-tests -Dtest=BegenListUITest#registerLoginLikeAndGoToFavorilerimAndLogout'
                }
            }
        }
    }
}
