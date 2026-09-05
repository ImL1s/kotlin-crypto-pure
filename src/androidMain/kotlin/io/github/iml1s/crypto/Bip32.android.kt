package io.github.iml1s.crypto

import org.bouncycastle.crypto.digests.RIPEMD160Digest
import java.security.MessageDigest

/**
 * Android 平台的 BIP32 輔助函數實現
 * 公鑰使用 Secp256k1Pure（與 JVM 相同）；RIPEMD160 使用 BouncyCastle。
 */

public actual fun platformGetPublicKey(privateKey: ByteArray): ByteArray {
    return Secp256k1Pure.pubKeyOf(privateKey, compressed = true)
}

/**
 * 計算 SHA256 哈希
 */
public actual fun platformSha256(data: ByteArray): ByteArray {

    val digest = MessageDigest.getInstance("SHA-256")
    return digest.digest(data)
}

/**
 * 計算 RIPEMD160 哈希
 */
public actual fun platformRipemd160(data: ByteArray): ByteArray {

    val digest = RIPEMD160Digest()
    digest.update(data, 0, data.size)
    val result = ByteArray(digest.digestSize)
    digest.doFinal(result, 0)
    return result
}
