package com.cbstudio.wearwallet.core.crypto

/**
 * Base58 編碼/解碼實現
 * 用於 Solana 地址和交易簽名的編碼
 */
object Base58 {
    private const val ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"
    private val INDEXES = IntArray(128) { -1 }

    init {
        for (i in ALPHABET.indices) {
            INDEXES[ALPHABET[i].code] = i
        }
    }

    /**
     * 將 byte array 編碼為 Base58 字串
     *
     * 🔧 重要：此函數會在內部複製輸入數組，不會修改原始數據
     */
    fun encode(input: ByteArray): String {
        if (input.isEmpty()) return ""

        // 🔧 修復：複製輸入以避免修改原始數據
        // divmod 函數會修改傳入的數組，所以必須先複製
        val inputCopy = input.copyOf()

        // 計算前導零的數量
        var zeroCount = 0
        while (zeroCount < inputCopy.size && inputCopy[zeroCount].toInt() == 0) {
            ++zeroCount
        }

        // 將輸入轉換為 base 58
        val temp = ByteArray(inputCopy.size * 2)
        var j = temp.size

        var startAt = zeroCount
        while (startAt < inputCopy.size) {
            val mod = divmod(inputCopy, startAt, 256, 58)
            if (inputCopy[startAt].toInt() == 0) {
                ++startAt
            }
            temp[--j] = ALPHABET[mod.toInt()].code.toByte()
        }

        // 跳過前導零
        while (j < temp.size && temp[j].toInt() == ALPHABET[0].code) {
            ++j
        }

        // 將前導零轉換為 '1'
        while (--zeroCount >= 0) {
            temp[--j] = ALPHABET[0].code.toByte()
        }

        val output = temp.copyOfRange(j, temp.size)
        return output.decodeToString()
    }

    /**
     * Base58Check 編碼（包含校驗和）
     *
     * 用於 Bitcoin 地址等需要校驗和的場景
     *
     * @param input 輸入數據
     * @return Base58Check 編碼的字串
     */
    fun encodeWithChecksum(input: ByteArray): String {
        // 計算雙重 SHA-256 哈希作為校驗和
        val hash = sha256(sha256(input))
        val checksum = hash.copyOfRange(0, 4)

        // 將校驗和附加到輸入數據後
        val dataWithChecksum = input + checksum

        return encode(dataWithChecksum)
    }

    /**
     * SHA-256 哈希函數
     * 注意：這裡需要平台特定實現，暫時使用簡化版本
     */
    private fun sha256(data: ByteArray): ByteArray {
        // TODO: 使用平台特定的 SHA-256 實現
        // 這裡暫時返回空實現，實際應該使用 CryptoUtils.sha256()
        return ByteArray(32) // 臨時實現
    }

    /**
     * 將 Base58 字串解碼為 byte array
     */
    fun decode(input: String): ByteArray {
        if (input.isEmpty()) return ByteArray(0)

        // 轉換為 bytes
        val input58 = ByteArray(input.length)
        for (i in input.indices) {
            val c = input[i]
            var digit = if (c.code < 128) INDEXES[c.code] else -1
            if (digit < 0) {
                throw IllegalArgumentException("Invalid Base58 character: $c")
            }
            input58[i] = digit.toByte()
        }

        // 計算前導零
        var zeroCount = 0
        while (zeroCount < input58.size && input58[zeroCount].toInt() == 0) {
            ++zeroCount
        }

        // 轉換為 base 256
        val temp = ByteArray(input.length)
        var j = temp.size

        var startAt = zeroCount
        while (startAt < input58.size) {
            val mod = divmod(input58, startAt, 58, 256)
            if (input58[startAt].toInt() == 0) {
                ++startAt
            }
            temp[--j] = mod
        }

        // 跳過前導零
        while (j < temp.size && temp[j].toInt() == 0) {
            ++j
        }

        return ByteArray(zeroCount + (temp.size - j)).apply {
            temp.copyInto(this, zeroCount, j, temp.size)
        }
    }

    /**
     * 除法和取模運算
     */
    private fun divmod(number: ByteArray, startAt: Int, base: Int, divisor: Int): Byte {
        var remainder = 0
        for (i in startAt until number.size) {
            val digit = number[i].toInt() and 0xFF
            val temp = remainder * base + digit
            number[i] = (temp / divisor).toByte()
            remainder = temp % divisor
        }
        return remainder.toByte()
    }
}
