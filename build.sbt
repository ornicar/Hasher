name := "Hasher"

organization := "com.roundeights"

version := "1.2.2"

scalaVersion := "3.1.1"

// append -deprecation to the options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-feature", "-language:postfixOps")

// Repositories in which to find dependencies
resolvers ++= Seq(
  "jBCrypt Repository" at "https://repo1.maven.org/maven2/org/"
)

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

Test / publishArtifact := false

pomIncludeRepository := { _ => false }

pomExtra := (<url>https://github.com/Nycto/Hasher</url>
    <licenses>
        <license>
            <name>MIT</name>
            <url>https://opensource.org/licenses/MIT</url>
            <distribution>repo</distribution>
        </license>
    </licenses>
    <scm>
        <url>git@github.com:Nycto/Hasher.git</url>
        <connection>scm:git:git@github.com:Nycto/Hasher.git</connection>
    </scm>
    <developers>
        <developer>
            <id>Nycto</id>
            <name>James Frasca</name>
            <url>http://roundeights.com</url>
        </developer>
    </developers>)

// Application dependencies
libraryDependencies ++= Seq(
  "org.mindrot" % "jbcrypt" % "0.4" % "optional",
  "org.specs2" %% "specs2-core" % "4.15.0" % "test"
)
