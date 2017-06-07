package controllers

import javax.inject.Inject

import models.forms.AppForms.loginForm
import com.typesafe.config.ConfigFactory
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.ws.WSClient
import play.api.mvc._


import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by gmartinez on 30/5/17.
  */

class AppController @Inject()(ws:WSClient)
  extends Controller with I18nSupport{

  lazy val url = ConfigFactory.load().getObject("config.router").get("url");

  def index = Action {
    Ok(views.html.index(loginForm))
  }

  def login() = Action { implicit req =>
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(formWithErrors))
      },
      userData => { Redirect(routes.AppController.callRouter)
          .withSession(request2session
            .+("username",userData.username)
            .+("password",userData.password))
      }
    )
  }

  def callRouter = Action.async { implicit req=>
    ws.url(url.unwrapped().toString)
      .withHeaders(USER_AGENT -> "curl")
      .post(convertData(req))
      .map(resp => Ok(resp.body))
  }
  //TODO: utils/commons function?
  def convertData(req: Request[AnyContent]): Map[String,Seq[String]] = {
    req.session.data.map({case (key,value)=>(key,Seq(value))})
  }

  override def messagesApi: MessagesApi = ???

}