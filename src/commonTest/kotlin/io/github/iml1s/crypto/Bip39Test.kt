package io.github.iml1s.crypto

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import io.github.iml1s.crypto.Bip39

class Bip39Test {

    @Test
    fun testBip39OfficialVectors() {
        data class Vector(val entropy: String, val mnemonic: String, val seed: String)
        
        val vectors = listOf(
            Vector(
                "00000000000000000000000000000000",
                "abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about",
                "c55257c360c07c72029aebc1b53c05ed0362ada38ead3e3e9efa3708e53495531f09a6987599d18264c1e1c92f2cf141630c7a3c4ab7c81b2f001698e7463b04"
            ),
            Vector(
                "7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f7f",
                "legal winner thank year wave sausage worth useful legal winner thank yellow",
                "2e8905819b8723fe2c1d161860e5ee1830318dbf49a83bd451cfb8440c28bd6fa457fe1296106559d3c5ad73ddc0be17c82f32b908fb278faa52775f829b95a2"
            ),
            Vector(
                "80808080808080808080808080808080",
                "letter advice cage absurd amount doctor acoustic avoid letter advice cage above",
                "d71de856f81a8acc65e6fc851a38d4d7ec216fd0796d0a6b98a3a3531b433d66385150669b95999cf9328609ce2e35092022b436a1fb81a4e8d97c4181f5dc22"
            ),
            Vector(
                "ffffffffffffffffffffffffffffffff",
                "zoo zoo zoo zoo zoo zoo zoo zoo zoo zoo zoo wrong",
                "ac75d9e5d5e6bc5a927035447bfa943e9508852d87aa74a5804226a5c1a1fa232842cbad97580713b91fa0ad8a1532e0c3eb06f131974360e6e8e7c05915c460"
            )
        )

        for (v in vectors) {
            // 1. Validate Mnemonic
            assertTrue(Bip39.validate(v.mnemonic), "Invalid mnemonic: ${v.mnemonic}")

            // 2. Entropy -> Mnemonic (Need hex utils for this, skipping strict entropy check if no hex util public, but we can check Seed)
            // Assuming we focus on Mnemonic -> Seed correctness as it's the most critical
            
            // 3. Mnemonic -> Seed
            val seed = Pbkdf2.bip39Seed(v.mnemonic, "TREZOR")
            // Simple hex string conversion for test
            val seedHex = seed.joinToString("") { it.toUByte().toString(16).padStart(2, '0') }
            assertEquals(v.seed, seedHex, "Seed mismatch for ${v.mnemonic}")
        }
    }

    @Test
    fun testInvalidMnemonics() {
        val invalid = listOf(
            "abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon", // Invalid Checksum
            "legal winner thank year wave sausage worth useful legal winner thank yellow zoo", // Wrong length
            "notaword abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about" // Invalid word
        )
        
        for (m in invalid) {
            assertFalse(Bip39.validate(m), "Should be invalid: $m")
        }
    }
}
