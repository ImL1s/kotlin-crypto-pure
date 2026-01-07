package io.github.iml1s.crypto

internal actual fun pbkdf2HmacSha512(
    password: ByteArray,
    salt: ByteArray,
    iterations: Int,
    keyLength: Int
): ByteArray {
    // TODO: Implement pure Kotlin PBKDF2 or use Linux crypto lib
    throw UnsupportedOperationException("pbkdf2HmacSha512 is not yet implemented for Linux")
}

internal actual fun pbkdf2HmacSha256(
    password: ByteArray,
    salt: ByteArray,
    iterations: Int,
    keyLength: Int
): ByteArray {
    // TODO: Implement pure Kotlin PBKDF2 or use Linux crypto lib
    throw UnsupportedOperationException("pbkdf2HmacSha256 is not yet implemented for Linux")
}

internal actual fun normalizeNfkdPlatform(text: String): String {
    // Basic implementation or stub
    // Linux doesn't have direct NSString equivalent easily accessible without specialized libs
    // Can potentially use ICU if available, or just return text for now if precise normalization is not critical for basic compile
    return text // WARNING: This is a placeholder and NOT correct NFKD normalization
}
