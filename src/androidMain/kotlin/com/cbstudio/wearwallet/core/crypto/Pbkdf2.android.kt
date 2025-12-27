package com.cbstudio.wearwallet.core.crypto

import java.text.Normalizer
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/**
 * Android 平台的 PBKDF2-HMAC-SHA512 實現
 *
 * 使用 Java Cryptography Architecture (JCA) 提供的標準實現。
 *
 * ## 實現細節
 * - 使用 `javax.crypto.SecretKeyFactory`
 * - 算法：`PBKDF2WithHmacSHA512`
 * - 符合 RFC 2898 和 BIP39 標準
 *
 * ## 安全性
 * - 使用 Android 系統內建的加密庫
 * - 支援硬體加速（在支援的設備上）
 * - 經過 FIPS 認證的實現
 *
 * @see [Android Cryptography](https://developer.android.com/guide/topics/security/cryptography)
 */
internal actual fun pbkdf2HmacSha512(
    password: ByteArray,
    salt: ByteArray,
    iterations: Int,
    keyLength: Int
): ByteArray {
    try {
        // Android PBEKeySpec 不允許空鹽值，使用單字節鹽值作為後備
        val effectiveSalt = if (salt.isEmpty()) byteArrayOf(0) else salt

        // JCA 的 PBEKeySpec 需要 char array 作為密碼
        // 並非所有 ByteArray 都能透過 decodeToString() 安全轉換
        // 對於 BIP39，密碼和鹽值應該已經是 NFKD 正規化過的 UTF-8 字符串
        val passwordChars = password.decodeToString().toCharArray()

        // 創建 PBEKeySpec
        val spec = PBEKeySpec(
            passwordChars,
            effectiveSalt,
            iterations,
            keyLength * 8
        )

        try {
            // 獲取 PBKDF2WithHmacSHA512 工廠
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")

            // 生成密鑰
            val key = factory.generateSecret(spec)

            return key.encoded
        } finally {
            // 安全清理：清除 spec 中的密碼
            spec.clearPassword()
        }
    } catch (e: Exception) {
        // 提供更詳細的錯誤訊息
        throw IllegalStateException(
            "PBKDF2-HMAC-SHA512 failed on Android: ${e.message}",
            e
        )
    }
}

/**
 * Android 平台的 NFKD 正規化實現
 *
 * 使用 Java 標準庫的 `java.text.Normalizer`。
 *
 * ## NFKD 正規化
 * - Normalization Form Compatibility Decomposition
 * - 將複合字符分解為基礎字符和組合標記
 * - 確保 Unicode 字符的一致性表示
 *
 * ### 範例
 * ```kotlin
 * // é (U+00E9) -> e (U+0065) + ´ (U+0301)
 * normalizeNfkdPlatform("café") // -> "café" (分解形式)
 * ```
 *
 * @param text 需要正規化的文本
 * @return NFKD 正規化後的文本
 */
internal actual fun normalizeNfkdPlatform(text: String): String {
    return Normalizer.normalize(text, Normalizer.Form.NFKD)
}
