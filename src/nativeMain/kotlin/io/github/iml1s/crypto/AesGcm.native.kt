package io.github.iml1s.crypto

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.cinterop.*
import platform.CoreCrypto.*
import platform.Security.*

/**
 * iOS/watchOS 平台實作 - 暫時佔位符 (修復編譯)
 */
@OptIn(ExperimentalForeignApi::class)
actual object AesGcm {
    actual suspend fun encrypt(
        plaintext: ByteArray,
        password: String,
        salt: ByteArray,
        iterations: Int
    ): AesGcmResult = withContext(Dispatchers.Default) {
        // ⚠️ 暫時返回未加密數據（僅用於驗證 Secp256k1）
        val nonce = ByteArray(12) { 0 }
        val tag = ByteArray(16) { 0 }
        AesGcmResult(nonce, plaintext, tag)
    }

    actual suspend fun decrypt(
        encrypted: AesGcmResult,
        password: String,
        salt: ByteArray,
        iterations: Int
    ): ByteArray = withContext(Dispatchers.Default) {
        // ⚠️ 暫時返回密文作為明文
        encrypted.ciphertext
    }
}
