#ifndef COMMONCRYPTO_CUSTOM_H
#define COMMONCRYPTO_CUSTOM_H

#include <CommonCrypto/CommonCrypto.h>
#include <CommonCrypto/CommonDigest.h>
#include <CommonCrypto/CommonCryptor.h>
#include <CommonCrypto/CommonKeyDerivation.h>
#include <stdint.h>
#include <stddef.h>

/**
 * Explicit declaration of CCCryptorGCM.
 * This function is present in the CommonCrypto library starting from iOS 13.0 and watchOS 6.0,
 * but is sometimes omitted from the public umbrella headers in certain SDK configurations.
 */
extern int32_t CCCryptorGCM(
    uint32_t op,           /* CCOperation */
    uint32_t alg,          /* CCAlgorithm */
    const void *key,
    size_t keyLength,
    const void *iv,
    size_t ivLen,
    const void *aad,
    size_t aadLen,
    const void *dataIn,
    size_t dataInLen,
    void *dataOut,
    void *tag,
    size_t *tagLength
);

/**
 * Custom wrapper for CC_SHA512 to ensure visibility and type safety across all KMP targets.
 */
static inline void Custom_CC_SHA512(const void *data, uint32_t len, unsigned char *md) {
    CC_SHA512(data, (uint32_t)len, md);
}

/**
 * Custom wrapper for CCCryptorGCM to ensure visibility across all KMP targets (iOS 13+, watchOS 6+).
 * This calls the extern function declared above.
 */
static inline int32_t Custom_CCCryptorGCM(
    uint32_t op,
    uint32_t alg,
    const void *key,
    size_t keyLength,
    const void *iv,
    size_t ivLen,
    const void *aad,
    size_t aadLen,
    const void *dataIn,
    size_t dataInLen,
    void *dataOut,
    void *tag,
    size_t *tagLength
) {
    return CCCryptorGCM(op, alg, key, keyLength, iv, ivLen, aad, aadLen, dataIn, dataInLen, dataOut, tag, tagLength);
}

#endif
