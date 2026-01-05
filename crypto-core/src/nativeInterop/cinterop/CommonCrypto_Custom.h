#ifndef COMMONCRYPTO_CUSTOM_H
#define COMMONCRYPTO_CUSTOM_H

#include <CommonCrypto/CommonCrypto.h>
#include <CommonCrypto/CommonDigest.h>
#include <stdint.h>

// Custom wrapper for CC_SHA512 to ensure visibility and type safety
static inline void Custom_CC_SHA512(const void *data, uint32_t len, unsigned char *md) {
    CC_SHA512(data, (CC_LONG)len, md);
}

#endif
