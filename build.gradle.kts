plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    `maven-publish`
}

// Library version and metadata
group = "io.github.iml1s"
version = "1.0.0"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
                freeCompilerArgs = freeCompilerArgs + listOf("-opt-in=kotlin.ExperimentalStdlibApi")
            }
        }
    }
    
    iosArm64()
    iosSimulatorArm64()
    iosX64()
    
    watchosArm64()
    watchosSimulatorArm64()
    watchosX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                api("org.kotlincrypto.hash:sha3:0.5.3")
                api("org.kotlincrypto.hash:sha2:0.5.3")
                api("com.ionspin.kotlin:bignum:0.3.9")
                api("io.github.andreypfau:curve25519-kotlin:0.0.8")
            }
        }
        
        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
            }
        }
        
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.core.ktx)
                api("fr.acinq.secp256k1:secp256k1-kmp:0.19.0")
                api("fr.acinq.secp256k1:secp256k1-kmp-jni-android:0.19.0")
                api(libs.bcprov.jdk18on)
                api(libs.walletCore)
            }
        }
        
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }

        // Apple shared source set (CommonCrypto implementations)
        val appleMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            }
        }
        
        // iOS source set - includes secp256k1-kmp (has native iOS bindings)
        val iosMain by creating {
            dependsOn(appleMain)
            dependencies {
                api("fr.acinq.secp256k1:secp256k1-kmp:0.19.0")
            }
        }
        
        // Link iOS targets to iosMain
        val iosArm64Main by getting { dependsOn(iosMain) }
        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }
        val iosX64Main by getting { dependsOn(iosMain) }
        
        // watchOS source set - no secp256k1-kmp (no watchOS bindings available)
        // Uses pure Kotlin Secp256k1Pure instead
        val watchosMain by creating {
            dependsOn(appleMain)
        }
        
        // Link watchOS targets to watchosMain
        val watchosArm64Main by getting { dependsOn(watchosMain) }
        val watchosSimulatorArm64Main by getting { dependsOn(watchosMain) }
        val watchosX64Main by getting { dependsOn(watchosMain) }
    }
}

android {
    namespace = "io.github.iml1s.crypto"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = false
    }
}

