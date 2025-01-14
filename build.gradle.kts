plugins {
  // this is necessary to avoid the plugins to be loaded multiple times
  // in each subproject's classloader
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.jetbrains.compose) apply false
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.cocoapods) apply false
  alias(libs.plugins.sqldelight) apply false
  alias(libs.plugins.spotless)
  alias(libs.plugins.buildkonfig) apply false
}

subprojects {
  apply(plugin = "com.diffplug.spotless")
  spotless { kotlinGradle { ktfmt().googleStyle() } }
  if (plugins.hasPlugin("org.jetbrains.compose")) {
    composeCompiler {
      reportsDestination = rootProject.layout.projectDirectory.dir("compose_compiler")
    }
  }
}

spotless {
  kotlin {
    target("buildSrc/src/**/*.kt")
    ktfmt().googleStyle()
  }

  kotlinGradle {
    target("buildSrc/**/*.gradle.kts")
    ktfmt().googleStyle()
  }

  format("swift") {
    target("iosApp/iosApp/**/*.swift")
    nativeCmd("swiftFormat","/usr/bin/env", listOf("swift-format"))
  }
}
