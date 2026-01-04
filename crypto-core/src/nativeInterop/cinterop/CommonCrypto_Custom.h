#ifndef CommonCrypto_Custom_h
#define CommonCrypto_Custom_h

#include <CommonCrypto/CommonCrypto.h>
#include <CommonCrypto/CommonCryptor.h>
#include <CommonCrypto/CommonDigest.h>

#include <stdint.h>

static inline void Custom_CC_SHA512(const void *data, uint32_t len, void *md) {
    CC_SHA512(data, len, (unsigned char *)md);
}

CCCryptorStatus CCCryptorGCM(
    CCOperation op,             /* kCCEncrypt, kCCDecrypt */
    CCAlgorithm alg,            /* kCCAlgorithmAES */
    const void *key,            /* raw key material */
    size_t keyLength,
    const void *iv,             /* nonce */
    size_t ivLength,
    const void *aData,          /* additional authentication data */
    size_t aDataLength,
    const void *dataIn,
    size_t dataInLength,
    void *dataOut,
    void *tag,
    size_t *tagLength);

#endif /* CommonCrypto_Custom_h */
