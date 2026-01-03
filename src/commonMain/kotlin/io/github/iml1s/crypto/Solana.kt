package io.github.iml1s.crypto

import io.github.andreypfau.curve25519.ed25519.Ed25519
import kotlin.random.Random

data class SolanaKeyPair(val privateKey: ByteArray, val publicKey: ByteArray)

object Solana {
    
    /**
     * Generate new KeyPair
     */
    fun generateKeyPair(): SolanaKeyPair {
        val seed = ByteArray(32)
        Random.nextBytes(seed)
        val privateKeyObj = Ed25519.keyFromSeed(seed)
        val publicKeyObj = privateKeyObj.publicKey()
        return SolanaKeyPair(seed, publicKeyObj.toByteArray())
    }





    
    /**
     * Get address from public key
     */
    fun getAddress(publicKey: ByteArray): String {
        return Base58.encode(publicKey)
    }
}
