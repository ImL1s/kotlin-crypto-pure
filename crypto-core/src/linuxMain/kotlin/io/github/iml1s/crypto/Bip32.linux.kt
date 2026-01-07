package io.github.iml1s.crypto

public actual fun platformGetPublicKey(privateKey: ByteArray): ByteArray {
    require(privateKey.size == 32) { "Private key must be 32 bytes, got ${privateKey.size}" }
    // Using portable implementation if available, otherwise stub
    return try {
        Secp256k1Pure.pubKeyOf(privateKey, compressed = true)
    } catch (e: Exception) {
        throw IllegalArgumentException("Failed to derive public key: ${e.message}", e)
    }
}

public actual fun platformSha256(data: ByteArray): ByteArray {
    return Sha256.hash(data)
}

public actual fun platformRipemd160(data: ByteArray): ByteArray {
    return Ripemd160.hash(data)
}
