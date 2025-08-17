// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.compose) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.spotless)
}

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**/*.kt")
    ktfmt().googleStyle()
    trimTrailingWhitespace()
    endWithNewline()
  }

  kotlinGradle {
    target("*.gradle.kts")
    ktfmt().googleStyle()
    trimTrailingWhitespace()
    endWithNewline()
  }

  java {
    target("**/*.java")
    targetExclude("**/build/**/*.java")
    googleJavaFormat().aosp()
    removeUnusedImports()
    trimTrailingWhitespace()
    endWithNewline()
  }
}
