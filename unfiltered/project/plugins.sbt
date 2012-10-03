resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

resolvers += "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-snapshots"))(Resolver.ivyStylePatterns)

addSbtPlugin("no.arktekk.sbt" % "aether-deploy" % "0.4")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.5-SNAPSHOT")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.1.0-SNAPSHOT")

libraryDependencies <+= sbtVersion(v => "com.github.siasia" %% "xsbt-web-plugin" % (v + "-0.2.11.1"))
