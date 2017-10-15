name := """wifree"""
organization := "com.wifree"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.11"

val playVersion = "2.6.6"
val pac4jVersion = "2.1.0"

libraryDependencies ++= Seq(
    guice,
    openId,
    filters,
    ws,
    ehcache,
    "com.typesafe.play" %% "play-json" % playVersion,
    "com.typesafe.play" % "play-cache_2.11" % playVersion,
    "uk.co.panaxiom" %% "play-jongo" % "2.0.0-jongo1.3",
    "org.postgresql" % "postgresql" % "42.0.0",
    "org.webjars" % "bootstrap" % "3.0.0",
    "org.pac4j" % "play-pac4j" % "4.0.0-RC1",
    "org.pac4j" % "pac4j-oauth" % pac4jVersion,
    "org.pac4j" % "pac4j-http" % pac4jVersion,
    "org.pac4j" % "pac4j-oidc" % pac4jVersion,
    "commons-io" % "commons-io" % "2.4",
    "be.objectify" % "deadbolt-java_2.11" % "2.6.1"
)

routesGenerator := InjectedRoutesGenerator
