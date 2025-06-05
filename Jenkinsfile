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

        stage('Detener y eliminar contenedor') {
            steps {
             script {
                 catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                  bat """
                     docker container inspect ${CONTAINER_NAME} >nul 2>&1 && (
                     docker container stop ${CONTAINER_NAME}
                     docker container rm ${CONTAINER_NAME}
                ) || echo "No existe el contenedor '${CONTAINER_NAME}'."
                """
            }
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
