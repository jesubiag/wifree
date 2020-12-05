package views.dto

import java.util.{Optional, List => JList}
import scala.collection.JavaConverters._

case class ConnectionsPage(connectedUsers: List[ConnectedUser], connectionTimeout: Option[Integer]) {

  def this(connectedUsers: JList[ConnectedUser], connectionTimeout: Optional[Integer]) =
    this(connectedUsers.asScala.toList, Option(connectionTimeout.orElse(null)))

}