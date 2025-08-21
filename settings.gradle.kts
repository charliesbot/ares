pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    // TODO - remove this when Navigation 3 Adaptive doesn't need SNAPSHOT anymore
    maven { url = uri("https://androidx.dev/snapshots/builds/13617490/artifacts/repository") }
  }
}

rootProject.name = "ares"

include(":app")

include(":core")
include(":features")
