package io.github.iml1s.crypto

import kotlinx.cinterop.*
import platform.posix.*

@OptIn(ExperimentalForeignApi::class)
actual object AesGcm {
    actual suspend fun encrypt(
        plaintext: ByteArray,
        password: String,
        salt: ByteArray,
        iterations: Int
    ): AesGcmResult {
        // TODO: Implement Linux AES-GCM (possibly via OpenSSL or other C interop, or Bouncy Castle if available)
        throw UnsupportedOperationException("AesGcm.encrypt is not yet implemented for Linux")
    }

    actual suspend fun decrypt(
        encrypted: AesGcmResult,
        password: String,
        salt: ByteArray,
        iterations: Int
    ): ByteArray {
        // TODO: Implement Linux AES-GCM
        throw UnsupportedOperationException("AesGcm.decrypt is not yet implemented for Linux")
    }
}
