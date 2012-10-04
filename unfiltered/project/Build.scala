import sbt._
import sbt.Keys._
import com.github.siasia.WebPlugin._
import com.github.siasia.PluginKeys._
import aether.Aether._
import Dependencies._

object Build extends sbt.Build {

  override def settings = super.settings ++ Seq(
    organization := "no.finn",
    scalaVersion := "2.9.1"
  )

  lazy val buildSettings = Defaults.defaultSettings ++ aetherSettings ++ Seq(
    publish <<= aether.AetherKeys.deploy,
    deployRepository <<= (version) { v =>
      val proxy = "http://mavenproxy.finntech.no/finntech-internal-"
      val end = if(v endsWith "SNAPSHOT") "snapshot" else "release"
      ("Finn-" + end) at (proxy + end)
    }
  )


  // Change this to a project name
  lazy val admin = Project(
    id = "admin",
    base = file("."),
    settings = buildSettings ++ Seq(libraryDependencies ++= adminDependencies) ++ webSettings
  )

   port in container.Configuration := 8081
}

object Dependencies {

  lazy val adminDependencies = Seq() ++ test ++ jetty ++ httpCache4J ++ slf4j ++ unfiltered ++ poi ++ constretto ++ liftJson ++ openCsv ++ scalaquery ++ mysql ++ liquibase

  lazy val cglib =
    java("cglib", "cglib-nodep", "2.2")

  lazy val mysql =
    java("mysql", "mysql-connector-java", "5.1.18")

  lazy val scalaquery =
    java("org.scalaquery", "scalaquery_2.9.0-1", "0.9.5")

  lazy val liquibase =
    java("org.liquibase", "liquibase-core", "2.0.5")

  lazy val openCsv =
    java("net.sf.opencsv", "opencsv", "2.3")

  lazy val liftJson =
    scala("net.liftweb", "lift-json", "2.4-M5")

  lazy val constretto =
    scala("org.constretto", "constretto-scala", "1.0") ++
    java("org.ini4j", "ini4j", "0.5.2")

  lazy val poi = {
    val version = "3.7"
    java("org.apache.poi", "poi", version) ++
    java("org.apache.poi", "poi-ooxml", version)
  }

  lazy val test = Set(
    "org.scala-tools.testing" %% "specs" % "1.6.9" % "test",
    "org.specs2" %% "specs2" % "1.6.1" % "test",
    "org.specs2" %% "specs2-scalaz-core" % "6.0.1" % "test",
    "net.liftweb" %% "lift-testkit" % "2.4" % "test",
    "junit" % "junit" % "4.8" % "test",
    "org.scalatest" %% "scalatest" % "1.6.1" % "test",
    "org.hsqldb" % "hsqldb" % "2.2.8" % "test"
  )

  lazy val jetty = {
    val versionJetty = "7.4.2.v20110526"
    val versionJettyJsp = "2.1.v20100127"
    Seq(
      "javax.servlet" % "servlet-api" % "2.5" % "provided",
      "org.eclipse.jetty.aggregate" % "jetty-all" % versionJetty % "container,test",
      "org.eclipse.jetty" % "jetty-webapp" % versionJetty % "container,test",
      "org.mortbay.jetty" % "jsp-2.1-glassfish" % versionJettyJsp % "container,test",
      "org.mortbay.jetty" % "jsp-api-2.1-glassfish" % versionJettyJsp % "container,test")
  }

  lazy val httpCache4J = {
    val version = "3.4"
    java("org.codehaus.httpcache4j", "httpcache4j-core", version) ++
    java("org.codehaus.httpcache4j.resolvers", "resolvers-httpcomponents-httpclient", version) ++
    java("org.codehaus.httpcache4j.storage", "storage-file", version) ++
    java("org.codehaus.httpcache4j.storage", "storage-h2", version)
  }


  lazy val unfiltered =
    scala("net.databinder", "dispatch-http", "0.8.5") ++
    scala("net.databinder", "unfiltered-filter", "0.5.3") ++
    scala("net.databinder", "unfiltered-uploads", "0.5.3")


  lazy val slf4j = javaGroup("org.slf4j", "1.6.1",
    "slf4j-api",
    "slf4j-log4j12",
    "jul-to-slf4j",
    "jcl-over-slf4j")

  def javaGroup(group: String, version: String, artifacts: String*): Seq[ModuleID] = artifacts.map(x => group % x % version)

  def scalaGroup(group: String, version: String, artifacts: String*): Seq[ModuleID] = artifacts.map(x => group %% x % version)

  def scala(group: String, artifact: String, version: String): Seq[ModuleID] = Seq(group %% artifact % version)

  def java(group: String, artifact: String, version: String): Seq[ModuleID] = Seq(group % artifact % version)
}
