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

// Composite Builds for Unified SDK
includeBuild("../kotlin-blockchain-client")
includeBuild("../kotlin-tx-builder")
includeBuild("../kotlin-utxo")
includeBuild("../kotlin-address")
includeBuild("../kotlin-solana")

