package utils

import java.util.{List => JList}

import operations.responses.VisitsByPeriod
import play.api.libs.json._
import scala.collection.JavaConverters._


object JsonHelper {

  def toJson(list: JList[VisitsByPeriod]): JsValue = Json.toJson(list.asScala)

}
