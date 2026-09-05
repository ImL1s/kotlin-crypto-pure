package io.github.iml1s.crypto

import org.kotlincrypto.hash.sha2.SHA256

public actual fun platformGetPublicKey(privateKey: ByteArray): ByteArray {
    return Secp256k1Pure.pubKeyOf(privateKey, compressed = true)
}

public actual fun platformSha256(data: ByteArray): ByteArray {
    return SHA256().digest(data)
}

public actual fun platformRipemd160(data: ByteArray): ByteArray {
    return Ripemd160.hash(data)
}
