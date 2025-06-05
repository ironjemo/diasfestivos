pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'apicalendario'
        CONTAINER_NAME = 'apicalendario_container'
        DOCKER_NETWORK = 'dockerfestivos_red'
        DOCKER_BUILD_DIR = 'presentacion'
        HOST_PORT = '9085'
        CONTAINER_PORT = '8080'
    }

    stages {

       stage('Generar ejecutable Maven') {
             steps {
              bat 'mvn clean package -DskipTests'
             }
        }

        stage('Construir la imagen') {
            steps {
                dir("${DOCKER_BUILD_DIR}") {
                    bat "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }

        stage('Desplegar el contenedor') {
            steps {
                script {
                    bat "docker rm -f ${CONTAINER_NAME} || exit 0"
                    bat "docker run --network ${DOCKER_NETWORK} --name ${CONTAINER_NAME} -p ${HOST_PORT}:${CONTAINER_PORT} -d ${DOCKER_IMAGE}"
                }
            }
        }

    }
}
