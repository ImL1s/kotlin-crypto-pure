package io.github.iml1s.crypto

internal actual fun platformHmacSha256(key: ByteArray, data: ByteArray): ByteArray {
    // Falls back to pure kotlin implementation if available, or stub
    // Since we are in crypto-core, we might need a pure Kotlin HMAC logic or throw.
    // However, looking at commonMain, there often is a pure implementation available or relied upon in other targets differently.
    // Ideally we'd use OpenSSL here for Linux but for now stubbing to pass compile.
     throw UnsupportedOperationException("platformHmacSha256 is not yet implemented for Linux")
}
