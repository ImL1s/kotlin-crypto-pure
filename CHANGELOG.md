# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0] - 2026-01-03

### Added
- **Bech32 & Bech32m**: Support for BIP 173 and BIP 350
  - Fully verified with official long test vectors (90 characters)
  - Optimized checksum calculation for long strings
- **Solana Support**: Address generation and Keypair utilities
  - Pure Kotlin Ed25519 key generation
  - Base58 address encoding
- **Tron Support**: Address generation
  - PubKey to Address (T-prefix) via Keccak256 and Base58Check
- **Hex Utility**: Unified and optimized hex encoding/decoding
  - Consolidated redundant helpers from `Bip32`, `Base58`, and `PureEthereumCrypto`
- **UInt Extensions**: Shared `toBigEndianByteArray` helper

### Fixed
- **BigInteger**: Resolved `NoSuchElementException` in `toByteArray()` for zero magnitude values
- **Bech32m Precision**: Fixed checksum calculation overflow on JVM using `Long`
- **Base58 Checksum**: Removed redundant and inconsistent implementations

### Changed
- Refactored `Bip32` and `PureEthereumCrypto` for better utility use and architectural consistency


## [1.0.0] - 2024-12-27

### Added

- **BIP39**: Mnemonic generation, validation, and seed derivation
  - Support for 12, 15, 18, 21, and 24-word mnemonics
  - Official Trezor test vectors verified
  - NFKD Unicode normalization

- **BIP32**: HD wallet key derivation
  - Hardened and non-hardened derivation paths
  - Public key derivation from xpub
  - Test Vectors 1, 2, 3 verified

- **Secp256k1**: Pure Kotlin ECDSA implementation
  - RFC 6979 deterministic signatures
  - Signature verification
  - ECDH key exchange
  - Wycheproof edge case tests

- **PBKDF2-HMAC-SHA512**: Key derivation
  - 2048 iterations (BIP39 compliant)
  - Platform-optimized implementations

- **AES-GCM**: Authenticated encryption
  - 256-bit key support
  - Secure nonce generation

- **Hashing**: Multiple algorithms
  - SHA256, SHA512
  - Keccak256 (Ethereum)
  - RIPEMD160 (Bitcoin)

- **Encoding**
  - Base58 with checksum
  - RLP (Recursive Length Prefix)

### Platforms

- Android (API 26+)
- iOS (arm64, x64, simulator)
- watchOS (arm64, x64, simulator)
- wearOS (via Android)

[1.0.0]: https://github.com/ImL1s/kotlin-crypto-pure/releases/tag/v1.0.0
