package di

import play.api.{Application, ApplicationLoader}

class DefaultApplicationLoader extends ApplicationLoader{
  override def load(context: ApplicationLoader.Context): Application =  new AppComponents(context).application
}


