package controllers

import javax.inject.Inject

import models.forms.AppForms.loginForm
import com.typesafe.config.ConfigFactory
import models.User
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.ws.WSClient
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by gmartinez on 30/5/17.
  */

class AppController @Inject()(ws:WSClient)
  extends Controller with I18nSupport{

  lazy val urlDefault = ConfigFactory.load().getObject("config.router").get("url");

  def index(urlRouter:String) = Action {
    Ok(views.html.index(loginForm,urlRouter))
  }

  def login(urlRouter:String) = Action { implicit req =>
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(formWithErrors,urlRouter))
      },
      userData => { Redirect(routes.AppController.callRouter)
          .withSession(request2session
            .+("username",userData.username)
            .+("password",userData.password)
            .+("urlRouter",urlRouter))
      }
    )
  }

  def callRouter = Action.async { implicit req =>

    val url = "http://"+request2session.get("urlRouter").get

    val request = ws.url(url)
      .withHeaders(USER_AGENT -> "curl")
      .withQueryString("username" -> request2session.get("username").get)
      .withQueryString("password" -> request2session.get("password").get)

    request.get()
      .map{resp =>
        Ok(resp.body)}
  }


  override def messagesApi: MessagesApi = ???

}