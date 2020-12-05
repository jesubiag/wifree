package views.dto

import java.util.Date

case class ConnectedUser(id: Long, username: String, connectionStart: Date, device: String)
