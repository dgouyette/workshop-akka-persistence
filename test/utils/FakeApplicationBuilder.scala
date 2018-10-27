package utils

import com.typesafe.config.ConfigFactory
import di.DefaultApplicationLoader
import org.scalatestplus.play.FakeApplicationFactory
import play.api.inject.DefaultApplicationLifecycle
import play.api.{Application, ApplicationLoader, Configuration, Environment}
import play.core.DefaultWebCommands

trait FakeApplicationBuilder extends FakeApplicationFactory {
  def build(): Application = {
    val env = Environment.simple()
    val context = ApplicationLoader.Context(
      environment = env,
      sourceMapper = None,
      webCommands = new DefaultWebCommands(),
      initialConfiguration = Configuration(ConfigFactory.load()),
      lifecycle = new DefaultApplicationLifecycle()
    )
    val loader = new DefaultApplicationLoader()
    loader.load(context)
  }

  def fakeApplication(): Application = build()
}
