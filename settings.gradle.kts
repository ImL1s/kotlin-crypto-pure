pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "kotlin-crypto-pure"

include(":crypto-core")
include(":demo-cli")
include(":demo-app")
include(":kotlin-wallet-sdk")

// Composite Builds for Unified SDK (Standardized at version 1.3.0)
fun includeBuildIfExists(path: String) {
    if (file(path).exists()) {
        includeBuild(path)
    }
}

includeBuildIfExists("../kotlin-blockchain-client")
includeBuildIfExists("../kotlin-tx-builder")
includeBuildIfExists("../kotlin-utxo")
includeBuildIfExists("../kotlin-address")
includeBuildIfExists("../kotlin-solana")

