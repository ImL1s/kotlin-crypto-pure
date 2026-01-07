package io.github.iml1s.crypto

internal actual fun platformHmacSha512(key: ByteArray, data: ByteArray): ByteArray {
    // TODO: Implement Linux HMAC-SHA512
    throw UnsupportedOperationException("platformHmacSha512 is not yet implemented for Linux")
}
