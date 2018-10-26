name := "TestGatling"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.3.1" % "test"

libraryDependencies += "io.gatling" % "gatling-test-framework" % "2.3.1" % "test"

enablePlugins(GatlingPlugin)
