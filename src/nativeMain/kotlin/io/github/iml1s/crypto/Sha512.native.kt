package io.github.iml1s.crypto

import kotlinx.cinterop.*
import platform.CoreCrypto.*

@OptIn(ExperimentalForeignApi::class)
actual fun platformSha512(data: ByteArray): ByteArray {
    val result = ByteArray(CC_SHA512_DIGEST_LENGTH)
    data.usePinned { pinned ->
        result.usePinned { resultPinned ->
            CC_SHA512(
                pinned.addressOf(0) as CPointer<UByteVar>?,
                data.size.toUInt(),
                resultPinned.addressOf(0) as CPointer<UByteVar>?
            )
        }
    }
    return result
}
