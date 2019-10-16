name := "SparkStreaming"

version := "0.1"
trapExit := false
scalaVersion := "2.12.8"

libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.12" % "2.4.4"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.12" % "2.4.4"
libraryDependencies += "org.apache.spark" % "spark-core_2.12" % "2.4.4"