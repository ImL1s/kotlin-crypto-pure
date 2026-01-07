package io.github.iml1s.crypto

actual object Secp256k1Provider {

    actual fun sign(privateKey: ByteArray, messageHash: ByteArray): ByteArray {
        return Secp256k1Pure.sign(messageHash, privateKey)
    }

    actual fun verify(signature: ByteArray, messageHash: ByteArray, publicKey: ByteArray): Boolean {
        return Secp256k1Pure.verify(signature, messageHash, publicKey)
    }

    actual fun computePublicKey(privateKey: ByteArray, compressed: Boolean): ByteArray {
        return Secp256k1Pure.pubKeyOf(privateKey, compressed)
    }

    actual fun isValidPrivateKey(privateKey: ByteArray): Boolean {
        // Basic length check as per Secp256k1Pure usually expecting 32 bytes
        // and ideally checking if it's within curve order, but likely Secp256k1Pure has a check or we assume size verification
        return privateKey.size == 32
        // TODO: deeper validation if Secp256k1Pure exposes secKeyVerify
    }

    actual fun ecdh(privateKey: ByteArray, publicKey: ByteArray): ByteArray {
        // TODO: Implement ECDH in Secp256k1Pure if not available, or stub.
        // For now, throw to compile.
        throw UnsupportedOperationException("ECDH not yet implemented for Linux")
    }
}
