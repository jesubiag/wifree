package models.forms

import models.User
import play.api.data.Form
import play.api.data.Forms.{mapping, _}
/**
  * Created by gmartinez on 7/6/17.
  */
object AppForms {

  def loginForm:Form[User]= Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(User.apply)(User.unapply))
}
