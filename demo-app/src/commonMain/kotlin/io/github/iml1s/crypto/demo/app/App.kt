package io.github.iml1s.crypto.demo.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.iml1s.crypto.*
import io.github.andreypfau.curve25519.ed25519.Ed25519

data class WalletEntry(
    val chain: String,
    val emoji: String,
    val address: String,
    val path: String,
    val color: Color
)

@Composable
fun App() {
    var mnemonic by remember {
        mutableStateOf("abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon abandon about")
    }
    var wallets by remember { mutableStateOf(emptyList<WalletEntry>()) }
    var isLoading by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF0D1117)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header
                Text(
                    text = "🦄 Kotlin Crypto Pure",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Multi-Chain Wallet Generator",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                // Mnemonic Input
                OutlinedTextField(
                    value = mnemonic,
                    onValueChange = { mnemonic = it },
                    label = { Text("BIP39 Mnemonic") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.LightGray,
                        focusedBorderColor = Color(0xFF58A6FF),
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Generate Button
                Button(
                    onClick = {
                        isLoading = true
                        wallets = generateWallets(mnemonic)
                        isLoading = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF238636)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Generate Wallets", fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Wallet List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(wallets) { wallet ->
                        WalletCard(wallet)
                    }
                }
            }
        }
    }
}

@Composable
fun WalletCard(wallet: WalletEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF161B22)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Emoji
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(wallet.color.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = wallet.emoji,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = wallet.chain,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = wallet.path,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (wallet.address.length > 42) 
                        "${wallet.address.take(20)}...${wallet.address.takeLast(8)}"
                    else wallet.address,
                    color = Color(0xFF58A6FF),
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

private fun generateWallets(mnemonic: String): List<WalletEntry> {
    val wallets = mutableListOf<WalletEntry>()

    try {
        val seed = Pbkdf2.bip39Seed(mnemonic, "")

        // Ethereum
        val ethKey = Bip32.derivePath(seed, "m/44'/60'/0'/0/0")
        val ethAddress = PureEthereumCrypto.getEthereumAddress(Hex.encode(ethKey.privateKey))
        wallets.add(WalletEntry("Ethereum", "🔷", ethAddress, "m/44'/60'/0'/0/0", Color(0xFF627EEA)))

        // Bitcoin (public key only for demo)
        val btcKey = Bip32.derivePath(seed, "m/44'/0'/0'/0/0")
        val btcPub = Secp256k1Pure.generatePublicKey(btcKey.privateKey)
        wallets.add(WalletEntry("Bitcoin", "🟠", Hex.encode(btcPub).take(32) + "...", "m/44'/0'/0'/0/0", Color(0xFFF7931A)))

        // Solana (Ed25519)
        val solSeed = Bip32.derivePath(seed, "m/44'/501'/0'/0'").privateKey
        val solPrivKey = Ed25519.keyFromSeed(solSeed)
        val solPubKey = solPrivKey.publicKey().toByteArray()
        val solAddress = Solana.getAddress(solPubKey)
        wallets.add(WalletEntry("Solana", "☀️", solAddress, "m/44'/501'/0'/0'", Color(0xFF00FFA3)))

        // TON
        val tonKeypair = Ton.keyPairFromMnemonic(mnemonic)
        val tonAddress = Ton.getAddress(tonKeypair.publicKey, workchain = 0, bounceable = false)
        wallets.add(WalletEntry("TON", "💎", tonAddress, "(mnemonic)", Color(0xFF0088CC)))

        // Polkadot (Sr25519)
        val dotKeypair = Sr25519.keypairFromSeed(seed.copyOfRange(0, 32))
        val dotAddress = Polkadot.getAddress(dotKeypair.publicKey, networkId = 0)
        wallets.add(WalletEntry("Polkadot", "🟣", dotAddress, "(seed)", Color(0xFFE6007A)))

        // Cosmos
        val cosmosKey = Bip32.derivePath(seed, "m/44'/118'/0'/0/0")
        val cosmosPub = Secp256k1Pure.generatePublicKey(cosmosKey.privateKey, compressed = true)
        val cosmosAddress = Cosmos.getAddress(cosmosPub, hrp = "cosmos")
        wallets.add(WalletEntry("Cosmos", "⚛️", cosmosAddress, "m/44'/118'/0'/0/0", Color(0xFF2E3148)))

        // Tron
        val trxKey = Bip32.derivePath(seed, "m/44'/195'/0'/0/0")
        val trxPub = Secp256k1Pure.generatePublicKey(trxKey.privateKey)
        val trxAddress = Tron.getAddress(trxPub)
        wallets.add(WalletEntry("Tron", "⚡", trxAddress, "m/44'/195'/0'/0/0", Color(0xFFFF0013)))

        // Ripple
        val xrpKey = Bip32.derivePath(seed, "m/44'/144'/0'/0/0")
        val xrpPub = Secp256k1Pure.generatePublicKey(xrpKey.privateKey, compressed = true)
        val xrpAddress = Xrp.getAddress(xrpPub)
        wallets.add(WalletEntry("XRP", "💧", xrpAddress, "m/44'/144'/0'/0/0", Color(0xFF00AAE4)))

    } catch (e: Exception) {
        wallets.add(WalletEntry("Error", "❌", e.message ?: "Unknown error", "", Color.Red))
    }

    return wallets
}
