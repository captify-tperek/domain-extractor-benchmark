import Dependencies._

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

enablePlugins(JmhPlugin)

lazy val root = (project in file("."))
  .settings(
    name := "domain-extractor-benchmark",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += automaton
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
