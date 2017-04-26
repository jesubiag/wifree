name := """wifree"""
organization := "com.wifree"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.11"

libraryDependencies += filters

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.14"
)
