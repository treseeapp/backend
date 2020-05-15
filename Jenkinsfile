pipeline {
  agent any
  stages {
    stage('Instalamos dependencias') {
      steps {
        sh  '''  echo "Instalamos dependencias"
                 pwd
                 cp /jenkinsCredentials/application.properties src/main/resources/application.properties
                 ls -la src/main/resources
            '''
      }
    }

    stage('Compilamos') {
      steps {
        sh  '''
            echo "Compilamos "
            mvn package
            '''
      }
    }
    stage('Construimos la imagen docker') {
            when{
                branch 'Produccion'
            }
            steps {
                sh  '''
                    echo "Contruimos la imagen docker"
                    cp ./target/app.war ./app.war
                    docker build -t back-java .
                    '''
                cleanWs()
            }
    }

    stage('Deploying image docker'){
        when{
            branch 'Produccion'
        }
        steps  {
            sh  '''
                echo "Desplegamos la imagen docker creada"
                docker stop java
                docker container rm java
                docker run -d --name java --network host back-java
                '''
            cleanWs()
        }
    }

    stage('Deploying to docker hub'){
        when{
            branch 'Produccion'
        }
        steps  {
            sh  '''
                echo "subimos a docker hub"
                docker tag back-java docker.tresee.app/back-java
                docker push docker.tresee.app/back-java
                '''
            cleanWs()
        }
    }
  }
}