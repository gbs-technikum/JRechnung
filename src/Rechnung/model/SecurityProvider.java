package Rechnung.model;

import Rechnung.Debug;
import Rechnung.Logger;
import Rechnung.Publisher;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Base64;
import java.util.Random;

public class SecurityProvider {

    private static final String REFERENCE_TEXT = "Test27";
    private static final String ALGORITHM = "AES";
    private boolean lock;
    private Cipher cipher;
    private SecretKey secretKey;
    private Logger logger;

    public SecurityProvider(){
        setLock(true);
        this.secretKey = null;
        this.logger = Publisher.getLogger();
        this.logger.loginfo(getClass().getName());
    }

    public SecurityProvider(String password) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException, SQLException, NoSuchPaddingException {
        this();
        this.unlock(password);
    }

    public boolean firstInit(String password) throws InvalidKeySpecException, NoSuchAlgorithmException, SQLException {
        this.secretKey = KeySecurityHelper.firstInit(password,null);
        if(this.secretKey != null){
            this.setLock(false);
            return true;
        }

        return false;
    }

    public boolean reset(String password, String base64Key) throws InvalidKeySpecException, NoSuchAlgorithmException, SQLException {
        this.secretKey = KeySecurityHelper.firstInit(password,base64Key);
        if(this.secretKey != null){
            this.setLock(false);
            return true;
        }

        return false;
    }

    public String getSecretKeyAsBase64() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public boolean isInitialized(){
        try {
            SecurityDataBaseHelper sdbh = new SecurityDataBaseHelper();
            return (sdbh.getReferenzeTextData() != null && sdbh.getSecretKeyData() != null && sdbh.getSalt() != null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean reInit(String newPassword) throws InvalidKeySpecException, NoSuchAlgorithmException, SQLException {
        if(this.isLocked()){
            return false;
        }

        return KeySecurityHelper.reInit(newPassword,this.secretKey);
    }

    private boolean initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("AES");
        return (this.cipher != null);
    }

    public byte[] decrypt(byte[] data){
        byte[] result = null;
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
            result = this.cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            if(Debug.ON){
                System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
            }
        } catch (BadPaddingException e) {
            if(Debug.ON){
                System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
            }
        } catch (IllegalBlockSizeException e) {
            if(Debug.ON){
                System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
            }
        }

        return result;
    }

    public byte[] encrypt(byte[] data){
        byte[] result = null;
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            result = this.cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            if(Debug.ON){
                System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
            }
        } catch (BadPaddingException e) {
            if(Debug.ON){
                System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
            }
        } catch (IllegalBlockSizeException e) {
            if(Debug.ON){
                System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
            }
        }

        return result;
    }

    public byte[] encrypt(String data) throws UnsupportedEncodingException {
        return this.encrypt(data.getBytes("UTF-8"));
    }

    public byte[] encrypt(int data){
        return this.encrypt(ByteBuffer.allocate(4).putInt(data).array());
    }

    public byte[] encrypt(long data){
        return this.encrypt(ByteBuffer.allocate(8).putLong(data).array());
    }

    public byte[] encrypt(double data){
        return this.encrypt(ByteBuffer.allocate(8).putDouble(data).array());
    }


    public int decryptAsInt(byte[] data){
        return ByteBuffer.wrap(this.decrypt(data)).getInt();
    }

    public long decryptAsLong(byte[] data){
        return ByteBuffer.wrap(this.decrypt(data)).getLong();
    }

    public double decryptAsDouble(byte[] data){
        return ByteBuffer.wrap(this.decrypt(data)).getDouble();
    }

    public String decryptAsString(byte[] data) throws UnsupportedEncodingException {
      String result = null;

        byte[] decrypted = this.decrypt(data);
        if(decrypted != null){
            result = new String(decrypted, "UTF-8");
        }

        return result;
    }


    public boolean isLocked() {
        return lock;
    }

    private void setLock(boolean lock) {
        this.lock = lock;
    }

    public boolean unlock(String password) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, SQLException {
        if(!this.isLocked()){
            return true;
        }

        SecretKey tmpKey = KeySecurityHelper.unlockEncryptionKey(password);
        if (tmpKey != null) {
            this.secretKey = tmpKey;
            this.setLock(false);
            return this.initCipher();
        }

        Message.showErrorMessagePasswordFalse();
        return false;
    }

        /*Innere Klassen
        ###################################################################################################*/
        public static class SecurityDataBaseHelper{
            private byte[] secretKeyData;
            private byte[] referenzeTextData;
            private byte[] salt;
            private static final String SQL_QUERY = "SELECT decrypt_password, reference_text, salt FROM security WHERE id  = 0";
            private static final String SQL_INSERT = "INSERT INTO security VALUES (?,?,?,?)";
            private static final String SQL_UPDATE = "UPDATE security set decrypt_password = ?, reference_text = ?, salt = ? WHERE id = 0";
            private static final String SQL_DELETE = "DELETE FROM security";
            private boolean externalData;

            public SecurityDataBaseHelper(byte[] secretKeyData, byte[] referenzeTextData, byte[] salt) {
                this.secretKeyData = secretKeyData;
                this.referenzeTextData = referenzeTextData;
                this.salt = salt;
                this.externalData = true;
            }

            public SecurityDataBaseHelper() throws SQLException {
                this.externalData = true;
                this.loadDataFromDB();
            }

            public byte[] getSecretKeyData() {
                return secretKeyData;
            }

            public byte[] getReferenzeTextData() {
                return referenzeTextData;
            }

            public byte[] getSalt() {
                return salt;
            }

            private boolean securityDataExist() throws SQLException {
                Connection con = Publisher.getDBConnection();
                if(con != null) {
                    Statement statement = null;
                    ResultSet resultSet = null;
                    try {
                        statement = con.createStatement();
                        resultSet = statement.executeQuery(SecurityDataBaseHelper.SQL_QUERY);
                        return (resultSet != null && !resultSet.isClosed() && resultSet.next());
                    } catch (SQLException e) {
                        System.out.println("BLA: " + e.getMessage());
                    } finally {
                        if (statement != null) {
                            try {
                                statement.close();
                            } catch (SQLException e) {
                                if (Debug.ON) {
                                    System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                                }
                            }

                        }
                        statement = null;
                    }
                }

                return false;
            }

            private void loadDataFromDB() throws SQLException {

                Connection con = Publisher.getDBConnection();
                Statement statement = null;

                if(con != null) {
                    try {
                        statement = con.createStatement();
                        ResultSet resultSet = statement.executeQuery(SecurityDataBaseHelper.SQL_QUERY);
                        if(resultSet != null && !resultSet.isClosed()){
                            this.salt = resultSet.getBytes("salt");
                            this.referenzeTextData = resultSet.getBytes("reference_text");
                            this.secretKeyData = resultSet.getBytes("decrypt_password");
                            this.externalData = false;
                        }
                    }finally {
                        if (statement != null) {
                            try{
                                statement.close();
                            }catch (SQLException e){
                                if(Debug.ON){
                                    System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                                }
                            }

                        }
                        statement = null;
                        con = null;
                    }
                }
            }

            public boolean modifyDataOnDB() throws SQLException {
                if(!this.externalData) {
                    return false;
                }

                Connection con = Publisher.getDBConnection();
                if(con != null) {
                    if(this.securityDataExist()){
                        PreparedStatement preparedStatement = null;
                        try {
                            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                            preparedStatement = con.prepareStatement(SecurityDataBaseHelper.SQL_UPDATE);

                            preparedStatement.setBytes(1, this.secretKeyData);
                            preparedStatement.setBytes(2, this.referenzeTextData);
                            preparedStatement.setBytes(3, this.salt);
                            preparedStatement.execute();
                            return true;
                        }finally {
                            if (preparedStatement != null) {
                                try{
                                    preparedStatement.close();
                                }catch (SQLException e){
                                    if(Debug.ON){
                                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                                    }
                                }

                            }
                            preparedStatement = null;
                            con = null;
                        }
                    }

                }

                return false;
            }

            public boolean saveDataToDB() throws SQLException {
                if(!this.externalData) {
                    return false;
                }

                Connection con = Publisher.getDBConnection();
                if(con != null) {
                    PreparedStatement preparedStatement = null;
                    try {
                        con.createStatement().execute(SecurityDataBaseHelper.SQL_DELETE);

                        preparedStatement = con.prepareStatement(SecurityDataBaseHelper.SQL_INSERT);

                        preparedStatement.setInt(1, 0);
                        preparedStatement.setBytes(2, this.secretKeyData);
                        preparedStatement.setBytes(3, this.referenzeTextData);
                        preparedStatement.setBytes(4, this.salt);
                        preparedStatement.execute();
                        return true;
                    }finally {
                        if (preparedStatement != null) {
                            try{
                                preparedStatement.close();
                            }catch (SQLException e){
                                if(Debug.ON){
                                    System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                                }
                            }

                        }
                        preparedStatement = null;
                        con = null;
                    }
                }

                return false;
            }
        }

        private static class KeySecurityHelper{

            public KeySecurityHelper() {
            }

            private static SecretKey prepareKey(String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
                SecretKey secret = null;
                SecretKeyFactory factory = null;

                factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
                SecretKey tmp = factory.generateSecret(spec);
                secret = new SecretKeySpec(tmp.getEncoded(), "AES");

                return secret;
            }

            private static byte[] generateSalt(){
                byte[] salt = new byte[128];
                final Random random = new SecureRandom();
                random.nextBytes(salt);
                return salt;
            }

            private static SecretKey generateDBEncryptionKey() throws NoSuchAlgorithmException {
                KeyGenerator generator = null;

                generator = KeyGenerator.getInstance("AES");
                generator.init(256);
                SecretKey key = generator.generateKey();
                return key;
            }

            public static boolean reInit(String newPassword, SecretKey currentSecretKey) throws InvalidKeySpecException, NoSuchAlgorithmException, SQLException {
                try {
                    Cipher cipher = Cipher.getInstance(SecurityProvider.ALGORITHM);

                    byte[] salt = generateSalt();
                    SecretKey secret = prepareKey(newPassword,salt);

                    SecretKey secretKey = currentSecretKey;

                    cipher.init(Cipher.ENCRYPT_MODE,secret);
                    byte[] dbKeyEncrypted = cipher.doFinal(secretKey.getEncoded());
                    byte[] refTextEncrypted = cipher.doFinal(SecurityProvider.REFERENCE_TEXT.getBytes(StandardCharsets.UTF_8));

                    SecurityDataBaseHelper sdb = new SecurityProvider.SecurityDataBaseHelper(dbKeyEncrypted,refTextEncrypted, salt);
                    return  sdb.modifyDataOnDB();
                } catch (IllegalBlockSizeException e) {
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                } catch (InvalidKeyException e) {
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                } catch (NoSuchPaddingException e){
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                } catch (BadPaddingException e) {
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

                return false;
            }

            public static SecretKey firstInit(String password,String base64Key) throws InvalidKeySpecException, NoSuchAlgorithmException, SQLException {
                try {
                    Cipher cipher = Cipher.getInstance(SecurityProvider.ALGORITHM);
                    System.out.printf("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    byte[] salt = generateSalt();
                    SecretKey secret = prepareKey(password,salt);

                    byte[] decodedKey = Base64.getDecoder().decode(base64Key);

                    SecretKey secretKey = null;
                    if(decodedKey.length > 0){
                        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                    }else {
                        secretKey = generateDBEncryptionKey();
                    }

                    cipher.init(Cipher.ENCRYPT_MODE,secret);
                    byte[] dbKeyEncrypted = cipher.doFinal(secretKey.getEncoded());
                    byte[] refTextEncrypted = cipher.doFinal(SecurityProvider.REFERENCE_TEXT.getBytes(StandardCharsets.UTF_8));

                    SecurityDataBaseHelper sdb = new SecurityProvider.SecurityDataBaseHelper(dbKeyEncrypted,refTextEncrypted, salt);

                    System.out.println("save db");
                    if(sdb.saveDataToDB()){
                        return secretKey;
                    }
                } catch (IllegalBlockSizeException e) {
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                } catch (InvalidKeyException e) {
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                } catch (NoSuchPaddingException e){
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                } catch (BadPaddingException e) {
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

                return null;
            }

            private static byte[] decryptAndCheck(String password,byte[] salt, byte[] refText,byte[] dbKeyEncrypted) throws InvalidKeySpecException, UnsupportedEncodingException, NoSuchAlgorithmException {

                byte[] result = null;

                Cipher cipher = null;
                try {
                    cipher = Cipher.getInstance(SecurityProvider.ALGORITHM);

                    SecretKey secret = KeySecurityHelper.prepareKey(password,salt);

                    cipher.init(Cipher.DECRYPT_MODE,secret);

                    String plaintext = new String(cipher.doFinal(refText), "UTF-8");

                    if(plaintext.equals(SecurityProvider.REFERENCE_TEXT)){
                        result = cipher.doFinal(dbKeyEncrypted);
                    }

                }  catch (NoSuchPaddingException e) {
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                } catch (InvalidKeyException e) {
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                } catch (BadPaddingException e) {
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                } catch (IllegalBlockSizeException e) {
                    if(Debug.ON){
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

                return result;
            }

            private static SecretKey unlockEncryptionKey(String password) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException, SQLException {
                SecretKey result = null;

                SecurityDataBaseHelper sdb = new SecurityDataBaseHelper();

                byte[] databaseEncryptionKey = KeySecurityHelper.decryptAndCheck(password, sdb.getSalt(), sdb.getReferenzeTextData(), sdb.getSecretKeyData());

                if (databaseEncryptionKey != null) {
                    result = new SecretKeySpec(databaseEncryptionKey, 0, databaseEncryptionKey.length, "AES");
                }

                return result;
            }
        }
}

