# kotlin-crypto-pure

[![Kotlin](https://img.shields.io/badge/kotlin-2.0.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Multiplatform](https://img.shields.io/badge/multiplatform-android%20%7C%20ios%20%7C%20watchOS-brightgreen)](https://kotlinlang.org/docs/multiplatform.html)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

> 🔐 **The first complete pure Kotlin cryptography library for wearable wallets.**

A Kotlin Multiplatform library implementing BIP32, BIP39, and secp256k1 **without any native dependencies**. Designed for watchOS, wearOS, and any Kotlin environment.

## ✨ Features

| Feature | Description |
|---------|-------------|
| **BIP39** | Mnemonic generation, validation, and seed derivation |
| **BIP32** | HD wallet key derivation (both private and public) |
| **Secp256k1** | Pure Kotlin ECDSA signing, verification, and ECDH |
| **PBKDF2** | HMAC-SHA512 with 2048 iterations (BIP39 compliant) |
| **AES-GCM** | Authenticated encryption |
| **Hashing** | SHA256, SHA512, Keccak256, RIPEMD160 |
| **Encoding** | Base58, RLP |

## 🎯 Target Platforms

- ✅ **Android** (API 21+)
- ✅ **iOS** (arm64, x64)
- ✅ **watchOS** (arm64, arm32)
- ✅ **wearOS** (Wear OS 2.0+)

## 📦 Installation

### Gradle (Kotlin DSL)

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

// build.gradle.kts (common source set)
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("com.example:kotlin-crypto-pure:1.0.0")
        }
    }
}
```

## 🚀 Quick Start

### Generate a Mnemonic & Seed

```kotlin
import com.cbstudio.wearwallet.core.crypto.Pbkdf2

// Generate seed from mnemonic (BIP39)
val mnemonic = "abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about"
val seed = Pbkdf2.bip39Seed(mnemonic, passphrase = "")

// seed is now a 64-byte ByteArray ready for BIP32 derivation
```

### HD Key Derivation (BIP32)

```kotlin
import com.cbstudio.wearwallet.core.crypto.Bip32

// Derive master key from seed
val masterKey = Bip32.derivePath(seed, "m")

// Derive Ethereum account (BIP44)
val ethKey = Bip32.derivePath(seed, "m/44'/60'/0'/0/0")

// Get private key and chain code
val privateKey = ethKey.privateKey  // 32 bytes
val chainCode = ethKey.chainCode    // 32 bytes
```

### ECDSA Signing (Secp256k1)

```kotlin
import com.cbstudio.wearwallet.core.crypto.Secp256k1Pure

// Generate public key from private key
val publicKey = Secp256k1Pure.generatePublicKey(privateKey)

// Sign a message hash (32 bytes)
val signature = Secp256k1Pure.sign(messageHash, privateKey)

// Verify signature
val isValid = Secp256k1Pure.verify(messageHash, signature, publicKey)
```

### Ethereum Address Derivation

```kotlin
import com.cbstudio.wearwallet.core.crypto.PureEthereumCrypto

// Derive address from private key
val address = PureEthereumCrypto.deriveAddressFromPrivateKey(privateKey)
// Returns: "0x9858EfFD232B4033E47d90003D41EC34EcaEda94"

// Derive address from xpub
val address2 = PureEthereumCrypto.deriveAddressFromXpub(xpub, "m/0/0")
```

## 🛡️ Security

### Verified Against Standard Test Vectors

- ✅ **BIP39**: Official Trezor test vectors
- ✅ **BIP32**: Test Vectors 1, 2, 3 from specification
- ✅ **Secp256k1**: Wycheproof edge case tests
- ✅ **RFC 6979**: Deterministic signature generation

### Secure Memory Handling

```kotlin
// Automatic cleanup of sensitive data
Secp256k1Pure.sign(message, privateKey)  // k value is securely wiped after use

// SecureByteArray for sensitive data
val secureKey = SecureByteArray.wrap(privateKey)
secureKey.use { key ->
    // Use the key
}  // Automatically wiped when done
```

## 📚 API Reference

### Core Classes

| Class | Purpose |
|-------|---------|
| `Pbkdf2` | BIP39 seed derivation |
| `Bip32` | HD key derivation |
| `Secp256k1Pure` | ECDSA operations |
| `PureEthereumCrypto` | Ethereum utilities |
| `AesGcm` | Encryption/Decryption |
| `Keccak256` | Ethereum hashing |
| `Base58` | Bitcoin address encoding |

## 🙏 Acknowledgements

- BIP39/32 specifications from [Bitcoin Improvement Proposals](https://github.com/bitcoin/bips)
- Wycheproof test vectors from [Google Security](https://github.com/google/wycheproof)
- RFC 6979 for deterministic signatures

## 📄 License

```
Copyright 2024 CB Studio

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
```
