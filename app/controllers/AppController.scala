package controllers

import javax.inject.Inject

import com.typesafe.config.ConfigFactory
import models.User
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by gmartinez on 30/5/17.
  */

class AppController @Inject()(ws:WSClient)
  extends Controller with I18nSupport{

  lazy val url = ConfigFactory.load().getObject("config.router").get("url");

  def loginForm:Form[User]= Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(User.apply)(User.unapply))

  def index = Action {
    Ok(views.html.index(loginForm))
  }

  def login() = Action { implicit req =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.index(formWithErrors)),
      user => Ok(user.username)
    )
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(formWithErrors))
      },
      userData => {
        Redirect(routes.AppController.callRouter())
      }
    )
  }

  override def messagesApi: MessagesApi = ???

  def callRouter = Action.async { implicit req =>
    ws
      .url(url.unwrapped().toString)
      .withHeaders(USER_AGENT -> "curl")
      .get
      .map(resp => Ok(resp.body))
  }
}