package io.github.iml1s.crypto

actual fun platformSha512(data: ByteArray): ByteArray {
    // TODO: Implement Linux SHA512
    throw UnsupportedOperationException("platformSha512 is not yet implemented for Linux")
}
