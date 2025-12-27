package cn.csw.encryption;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Formatter;

/**
 * 加密算法
 * 不可逆算法
 * MD5 信息摘要算法 message digest algorithm
 * HMAC 哈希运算消息验证编码 hash-based message authentication code
 * SHA 安全哈希算法 secure hash algorithm
 * 可逆算法
 * 对称加密
 * DES 数据加密标准  data encryption standard
 * AES 高级加密标准 advance encryption standard
 * 非对称加密
 * RSA 由创建的三人名字构成
 * <p>
 * 不可逆算法为了避免被别人暴力破解或彩虹表攻击 可以采用随机加盐
 * 1把盐加在原始密钥前面后者后面
 * 2先根据原始密钥加密后在拼接盐在加密
 *
 * @author Lier
 * @since 2023/9/18 15:07
 */
public class EncryptionTest {


    /**
     * MD5，全称为“Message-Digest Algorithm 5”，翻译过来叫“信息摘要算法”。
     * 它可以将任意长度的数据通过散列算法，生成一个固定长度的散列值。
     * MD5算法的输出长度为128位，通常用32个16进制数表示。(16进制的数字需要4位来表示)
     */
    @Test
    public void test1() {
        String data = "Hello World";
        String encryptedData = MD5Encryption(data);
        System.out.println("MD5加密后的数据长度：" + encryptedData.length());
        System.out.println("MD5加密后的数据：" + encryptedData);
    }

    public static final String MD5_ALGORITHM = "MD5";

    /**
     * 计算速度快、输出长度固定、应用广泛等等。
     * 但是作为一个加密算法，它有一个天大的缺点，那就是不安全。(暴力破解和彩虹表)
     * byte占一个字节（8位二进制）
     *
     * @param data
     * @return
     */
    @SneakyThrows
    public static String MD5Encryption(String data) {
        // 获取MD5算法实例
        MessageDigest messageDigest = MessageDigest.getInstance(MD5_ALGORITHM);
        // 计算散列值
        byte[] digest = messageDigest.digest(data.getBytes());
        Formatter formatter = new Formatter();
        StringBuilder sb = new StringBuilder();
        // 补齐前导0，并格式化
        for (byte b : digest) {
            formatter.format("%02x", b);
            //b & 0xff 是为了将负数转正  byte范围是-128~127
            sb.append(Integer.toHexString((b & 0xff) | 0x100).substring(1, 3));
        }
        System.out.println(sb);
        return formatter.toString();
    }


    @Test
    public void test2() {
        String data = "Hello World";
        String encryptedData = SHAEncrypt(data);
        System.out.println("SHA-256加密后的数据长度：" + encryptedData.length());
        System.out.println("SHA-256加密后的数据：" + encryptedData);
    }


    public static final String SHA_256_ALGORITHM = "SHA-256";

    /**
     * SHA（Secure Hash Algorithm）系列算法是一组密码散列函数，用于将任意长度的数据映射为固定长度的散列值
     * SHA-2算法包括SHA-224、SHA-256、SHA-384和SHA-512四种散列函数，分别将任意长度的数据映射为224位、256位、384位和512位的散列值
     */
    @SneakyThrows
    public static String SHAEncrypt(String data) {
        //获取SHA-256算法实例
        MessageDigest messageDigest = MessageDigest.getInstance(SHA_256_ALGORITHM);
        //计算散列值
        byte[] digest = messageDigest.digest(data.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        //将byte数组转换为16进制字符串
        for (byte b : digest) {
            // b & 0xFF  保留八位 然后 | 0x100 产生三位16进制 字符串截取角标1开始3结束
            // 与formatter.format("%02x") 效果一致
            stringBuilder.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
        }
        return stringBuilder.toString();
    }


    @Test
    public void test3() {
        String data = "Hello World";
        String key = "12345678";

        String encryptedData = DESEncrypt(data, key);
        System.out.println("加密后的数据：" + encryptedData);

        String decryptedData = DESDecrypt(encryptedData, key);
        System.out.println("解密后的数据：" + decryptedData);
    }

    private static final String DES_ALGORITHM = "DES";

    /**
     * DES加密
     *
     * @param data 待加密的数据
     * @param key  密钥，长度必须为8位
     * @return 加密后的数据，使用Base64编码
     */
    @SneakyThrows
    public static String DESEncrypt(String data, String key) {
        // 根据密钥生成密钥规范
        KeySpec keySpec = new DESKeySpec(key.getBytes());
        // 根据密钥规范生成密钥工厂
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        // 根据密钥工厂和密钥规范生成密钥
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

        // 根据加密算法获取加密器
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        // 初始化加密器，设置加密模式和密钥
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        // 加密数据
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        // 对加密后的数据进行Base64编码
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * DES解密
     *
     * @param encryptedData 加密后的数据，使用Base64编码
     * @param key           密钥，长度必须为8位
     * @return 解密后的数据
     */
    @SneakyThrows
    public static String DESDecrypt(String encryptedData, String key) {
        // 根据密钥生成密钥规范
        KeySpec keySpec = new DESKeySpec(key.getBytes());
        // 根据密钥规范生成密钥工厂
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        // 根据密钥工厂和密钥规范生成密钥
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

        // 对加密后的数据进行Base64解码
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        // 根据加密算法获取解密器
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        // 初始化解密器，设置解密模式和密钥
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        // 解密数据
        byte[] decryptedData = cipher.doFinal(decodedData);
        // 将解密后的数据转换为字符串
        return new String(decryptedData);
    }


    /**
     * AES（Advanced Encryption Standard）即高级加密标准，是一种对称加密算法，
     * 被广泛应用于数据加密和保护领域。AES算法使用的密钥长度为128位、192位或256位，比DES算法的密钥长度更长，安全性更高。
     * AES算法采用的密钥长度更长，密钥空间更大，安全性更高，能够有效地抵抗暴力破解攻击。
     */
    @Test
    @SneakyThrows
    public void test4() {
        String data = "Hello World";
        String encryptedData = AESEncrypt(data);
        System.out.println("加密后的数据：" + encryptedData);

        String decryptedData = AESDecrypt(encryptedData);
        System.out.println("解密后的数据：" + decryptedData);
    }


    private static final String AES_ALGORITHM = "AES";
    // AES加密模式为CBC，填充方式为PKCS5Padding
    private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    // AES密钥为16位
    private static final String AES_KEY = "1234567890123456";
    // AES初始化向量为16位
    private static final String AES_IV = "abcdefghijklmnop";

    /**
     * AES加密
     *
     * @param data 待加密的数据
     * @return 加密后的数据，使用Base64编码
     */
    public static String AESEncrypt(String data) throws Exception {
        // 将AES密钥转换为SecretKeySpec对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(), AES_ALGORITHM);
        // 将AES初始化向量转换为IvParameterSpec对象
        IvParameterSpec ivParameterSpec = new IvParameterSpec(AES_IV.getBytes());
        // 根据加密算法获取加密器
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        // 初始化加密器，设置加密模式、密钥和初始化向量
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        // 加密数据
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        // 对加密后的数据使用Base64编码
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * AES解密
     *
     * @param encryptedData 加密后的数据，使用Base64编码
     * @return 解密后的数据
     */
    public static String AESDecrypt(String encryptedData) throws Exception {
        // 将AES密钥转换为SecretKeySpec对象
        SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(), AES_ALGORITHM);
        // 将AES初始化向量转换为IvParameterSpec对象
        IvParameterSpec ivParameterSpec = new IvParameterSpec(AES_IV.getBytes());
        // 根据加密算法获取解密器
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        // 初始化解密器，设置解密模式、密钥和初始化向量
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        // 对加密后的数据使用Base64解码
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        // 解密数据
        byte[] decryptedData = cipher.doFinal(decodedData);
        // 返回解密后的数据
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public void test5() {
        KeyPair keyPair = generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String data = "Hello World";

        String encryptedData = RSAEncrypt(data, publicKey);
        System.out.println("加密后的数据：" + encryptedData);

        String decryptedData = RSADecrypt(encryptedData, privateKey);
        System.out.println("解密后的数据：" + decryptedData);
    }


    private static final String RSA_ALGORITHM = "RSA";

    /**
     * 生成RSA密钥对
     *
     * @return RSA密钥对
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(2048); // 密钥大小为2048位
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 使用公钥加密数据
     *
     * @param data      待加密的数据
     * @param publicKey 公钥
     * @return 加密后的数据
     */
    public static String RSAEncrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * 使用私钥解密数据
     *
     * @param encryptedData 加密后的数据
     * @param privateKey    私钥
     * @return 解密后的数据
     */
    public static String RSADecrypt(String encryptedData, PrivateKey privateKey) throws Exception {
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    @Test
    public void test() {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();


        String data = "hello world";


        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        String encryptionData = Base64.getEncoder().encodeToString(bytes);
        System.out.println(encryptionData);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decode = Base64.getDecoder().decode(encryptionData.getBytes(StandardCharsets.UTF_8));
        byte[] bytes1 = cipher.doFinal(decode);
        System.out.println(new String(bytes1, StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String originalMsg = "Hello World";
        MessageDigest mg = MessageDigest.getInstance("MD5");
        byte[] digest = mg.digest(originalMsg.getBytes(StandardCharsets.UTF_8));
        Formatter formatter = new Formatter();
        for (byte b : digest) {
            formatter.format("%02x",b);
        }
        System.out.println(formatter);


        mg = MessageDigest.getInstance("SHA-256");
        digest = mg.digest(originalMsg.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(Integer.toHexString(b & 0xff | 0x100), 1, 3);
        }
        System.out.println(sb);


        String des_key = "12345678";
        DESKeySpec desKeySpec = new DESKeySpec(des_key.getBytes());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte[] bytes = cipher.doFinal(originalMsg.getBytes());
        String ciphertext = Base64.getEncoder().encodeToString(bytes);
        System.out.println(ciphertext);

        byte[] decode = Base64.getDecoder().decode(ciphertext);
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        System.out.println(new String(cipher.doFinal(decode)));


        SecretKeySpec secretKeySpec = new SecretKeySpec(AES_KEY.getBytes(), AES_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(AES_IV.getBytes());

        cipher= Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);
        bytes = cipher.doFinal(originalMsg.getBytes(StandardCharsets.UTF_8));
        ciphertext = Base64.getEncoder().encodeToString(bytes);
        System.out.println(ciphertext);
        decode = Base64.getDecoder().decode(ciphertext);

        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivParameterSpec);
        System.out.println(new String(cipher.doFinal(decode)));


        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();



        cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        bytes = cipher.doFinal(originalMsg.getBytes());
        ciphertext = Base64.getEncoder().encodeToString(bytes);
        System.out.println(ciphertext);

        decode = Base64.getDecoder().decode(ciphertext);
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        System.out.println(new String(cipher.doFinal(decode)));


    }

}
