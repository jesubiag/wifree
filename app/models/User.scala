package models

import play.api.libs.json.Json

/**
  * Created by gmartinez on 31/5/17.
  */
case class User (username: String, password: String)

object User {
  implicit val userFormat = Json.format[User]
}