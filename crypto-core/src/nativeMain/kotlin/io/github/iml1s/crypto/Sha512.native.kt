package io.github.iml1s.crypto

import kotlinx.cinterop.*
import commonCrypto.*

@OptIn(ExperimentalForeignApi::class)
actual fun platformSha512(data: ByteArray): ByteArray {
    val result = ByteArray(CC_SHA512_DIGEST_LENGTH)
    data.usePinned { pinned ->
        result.usePinned { resultPinned ->
            Custom_CC_SHA512(
                pinned.addressOf(0) as CPointer<out CPointed>?,
                data.size.toUInt(),
                resultPinned.addressOf(0)
            )
        }
    }
    return result
}
