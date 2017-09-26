name := """wifree"""
organization := "com.wifree"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
    guice,
    openId,
    filters,
    ws,
    "com.typesafe.play" %% "play-json" % "2.6.5",
    //"com.typesafe.play" %% "play-iteratees" % "2.6.3",
    "uk.co.panaxiom" %% "play-jongo" % "2.0.0-jongo1.3",
    "org.postgresql" % "postgresql" % "42.0.0",
    "org.webjars" % "bootstrap" % "3.0.0",
    "org.pac4j" % "play-pac4j" % "4.0.0-RC1",
    "org.pac4j" % "pac4j-oauth" % "2.1.0",
    "org.pac4j" % "pac4j-http" % "2.1.0",
    "be.objectify" % "deadbolt-java_2.11" % "2.6.1"
)
