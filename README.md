# kotlin-crypto-pure

<p align="center">
  <img src="./docs/images/hero.png" alt="kotlin-crypto-pure Hero" width="100%">
</p>

<p align="center">
  <a href="https://jitpack.io/#ImL1s/kotlin-crypto-pure"><img src="https://jitpack.io/v/ImL1s/kotlin-crypto-pure.svg" alt="JitPack"></a>
  <a href="#"><img src="https://img.shields.io/badge/kotlin-2.1.0-blue.svg?logo=kotlin" alt="Kotlin"></a>
  <a href="#"><img src="https://img.shields.io/badge/Platform-Android%20%7C%20iOS%20%7C%20watchOS%20%7C%20JVM-orange" alt="Platform"></a>
  <a href="#"><img src="https://img.shields.io/badge/WatchOS-Supported-green?style=for-the-badge&logo=apple" alt="WatchOS Supported"></a>
</p>

<p align="center">
  <strong>💎 Zero-dependency Pure Kotlin Cryptography Primitives.</strong>
</p>

---

## 🏗️ Architecture

```mermaid
graph TD
    subgraph "Core Interface"
        A[App Logic] --> B{Crypto Interface}
    end

    subgraph "Pure Kotlin Implementations"
        B --> C[Secp256k1Pure]
        B --> D[Ed25519Pure]
        B --> E[HmacSha256Pure]
    end

    subgraph "Mathematical Foundations"
        C --> F[BigInteger Library]
        D --> F
        E --> G[Bit-wise Operations]
    end
```

---

## ✨ Features

- **Secp256k1**: Complete pure Kotlin implementation of Elliptic Curve Cryptography.
- **Ed25519**: Support for Edwards curves (Solana, Cardano).
- **Hmac-Sha256**: High-speed, bit-perfect HMAC implementation.
- **Zero Native Dependencies**: No cinterop, no `.a` or `.so` files—just Kotlin.
- **Portable**: Works on any platform that runs Kotlin.

---

## 📦 Installation

```kotlin
// build.gradle.kts
implementation("com.github.ImL1s:kotlin-crypto-pure:1.0.1")
```

---

## 🚀 Usage

### Sign with Secp256k1
```kotlin
val privKey = Random.nextBytes(32)
val message = "Hello KMP".toByteArray()
val signature = Secp256k1Pure.sign(message, privKey)

val isValid = Secp256k1Pure.verify(message, signature, pubKey)
```

---

## Correctness (v1.0.1)

P1 fixes on the shipped APIs: secret-free `pubKeyOf` errors, secp256k1 identity ≠ affine `(0,0)`, RFC 6979 `bits2octets`, strict DER, BIP32 `IL>=n` reject-before-add, RLP negative integers. See [CHANGELOG](CHANGELOG.md). This is not a full API security certification.

## 📄 License
MIT License
