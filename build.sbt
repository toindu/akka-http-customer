//enablePlugins(JavaServerAppPackaging)
enablePlugins(DockerPlugin)

name := "akka-http-customer"

version := "1.0.0"

organization := "com.sbux"

scalaVersion := "2.11.8"


fork in  (Test,run) := true

libraryDependencies ++= {
  val AkkaVersion       = "2.4.0"
  val AkkaHttpVersion   = "2.4.11"
  val Json4sVersion     = "3.2.11"
  val akkaV       = "2.4.3"
  val scalaTestV  = "2.2.6"
  Seq(
    "com.typesafe.akka" %% "akka-slf4j"      % AkkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % AkkaHttpVersion,
    "com.typesafe.akka" %%"akka-http-testkit" % AkkaHttpVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.1.2",
    "org.json4s"        %% "json4s-native"   % Json4sVersion,
    "org.json4s"        %% "json4s-ext"      % Json4sVersion,
    "de.heikoseeberger" %% "akka-http-json4s" % "1.4.2"
  )
}

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

libraryDependencies +="com.datastax.cassandra" % "cassandra-driver-core" % "3.0.1"

//javaOptions in  run += "-Djavax.net.ssl.trustStore=/Users/inbanerj/cdx/cassandra.truststore"

javaOptions in  (Test,run) += "-Djavax.net.ssl.trustStore=src/main/resources/cassandra.truststore"

javaOptions in  (Test,run) += "-Djavax.net.ssl.trustStorePassword=Sbux1Sbux1"


javaHome := Some(file("/Library/Java/JavaVirtualMachines/jdk1.8.0_91.jdk/Contents/Home"))

// Assembly settings
//mainClassStr in Global := Some("com.sbux.cust.mgmt.Main")

mainClass in Compile := Some("com.sbux.cust.mgmt.Main")

jarName in assembly := "akka-http-crud.jar"

version in docker := "1.0.0"

//imageNames in docker := Seq(
//  // Sets the latest tag
//  ImageName(s"${organization.value}/${name.value}:latest"),
//
//  // Sets a name with a tag that contains the project version
//  ImageName(
//    namespace = Some(organization.value),
//    repository = name.value,
//    tag = Some("v" + version.value)
//  )
//)

dockerfile in docker := {
  val jarFile: File = sbt.Keys.`package`.in(Compile, packageBin).value
  val classpath = (managedClasspath in Compile).value
  val mainclass = mainClass.in(Compile, packageBin).value.getOrElse(sys.error("Expected exactly one main class"))
  val jarTarget = s"/app/${jarFile.getName}"
  // Make a colon separated classpath with the JAR file
  val classpathString = classpath.files.map("/app/" + _.getName)
    .mkString(":") + ":" + jarTarget
  new Dockerfile {
    // Base image
    from("committed/java-jce")
    // Add all files on the classpath
    add(classpath.files, "/app/")
    // Add the trust file
    // Add the JAR file
    add(jarFile, jarTarget)
    // On launch run Java with the classpath and the main class
    env("CLASSPATH", classpathString)
    entryPoint("java", mainclass
    )
  }
}