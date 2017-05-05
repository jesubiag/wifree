name := """wifree"""
organization := "com.wifree"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.11"

libraryDependencies += filters

libraryDependencies ++= Seq(
  "uk.co.panaxiom" %% "play-jongo" % "2.0.0-jongo1.3",
  "org.postgresql" % "postgresql" % "42.0.0"
)
