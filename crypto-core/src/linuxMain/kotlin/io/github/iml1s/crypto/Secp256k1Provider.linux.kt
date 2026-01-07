package io.github.iml1s.crypto

actual object Secp256k1Provider {
    actual fun verify(signature: ByteArray, message: ByteArray, pubKey: ByteArray): Boolean {
        // Use pure Kotlin implementation
        return Secp256k1Pure.verify(signature, message, pubKey)
    }

    actual fun sign(message: ByteArray, privateKey: ByteArray): ByteArray {
         // Use pure Kotlin implementation
        return Secp256k1Pure.sign(message, privateKey)
    }
}
