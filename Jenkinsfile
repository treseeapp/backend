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

      steps {
        sh  '''
            echo "Contruimos la imagen docker"
            mv ./target/app.war ./app.war
            docker build -t back-java .
            '''
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
                docker run -d --name java --network host -p 8081:8080 back-java
                '''
        }
    }

    stage('Deploying to docker hub'){
        when{
            branch 'Produccion'
        }
        steps  {
        sh  '''
            echo "subimos a docker hub"
            '''

        }
    }
  }
}