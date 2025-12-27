package cn.csw.encryption;
/**
 * 常用加密算法
 * 不可逆算法
 *  MD5 message digest algorithm 5 信息摘要算法5 32个4位 MessageDigest.getInstance("MD5") format("%02x")
 *  HMAC hase-based message  hashtion code 哈希算法信息验证码
 *  SHA secure hash algorithm 安全哈希算法 MessageDigest.getInstance("SHA-256") Integer.toHexString(byte && 0xFF | 0x100)
 *
 *
 * 可逆算法
 * 对称算法
 *  DES data encryption standard 数据加密算法 DesKetSpec SecretKeyFactory  SecretKey  Cipher.getInstance() Cipher.init Cipher.dofinal
 *  AES advance encryption standard 高级加密标准 SecretKeySpec(key,algorithm) IvParameterSpec Cipher.getInstance("AES/CBC/PKCS...")
 *
 * 非对称算法
 * RSA 由发明的三个人首字母构成 KeyPairGenerate PublicKey  PrivateKey
 *
 */