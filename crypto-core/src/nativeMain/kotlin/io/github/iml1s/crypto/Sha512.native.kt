package io.github.iml1s.crypto

import org.kotlincrypto.hash.sha2.Sha512

actual fun platformSha512(data: ByteArray): ByteArray {
    return Sha512().digest(data)
}
