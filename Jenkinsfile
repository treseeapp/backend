pipeline {
  agent any
  stages {
    stage('Instalamos dependencias') {
      steps {
        sh  '''  echo "Instalamos dependencias"
                 pwd
                 ls -la /
            '''
      }
    }

    stage('Compilamos') {
      steps {
        sh  '''
            echo "Compilamos "
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