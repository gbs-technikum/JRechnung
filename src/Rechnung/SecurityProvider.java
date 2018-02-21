package Rechnung;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Random;
import java.util.ServiceConfigurationError;

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
       // KeySecurityHelper keySecurityHelper = new KeySecurityHelper();
     //   this.secretKey = keySecurityHelper.firstInit(password);
        KeySecurityHelper.firstInit(password);
        if(this.secretKey != null){
            this.setLock(false);
            return true;
        }

        return false;
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

    public byte[] encrypt(double data){
        return this.encrypt(ByteBuffer.allocate(8).putDouble(data).array());
    }


    public int decryptAsInt(byte[] data){
        return ByteBuffer.wrap(this.decrypt(data)).getInt();
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
            private boolean externalData;

            public SecurityDataBaseHelper(byte[] secretKeyData, byte[] referenzeTextData, byte[] salt) {
                this.secretKeyData = secretKeyData;
                this.referenzeTextData = referenzeTextData;
                this.salt = salt;
                this.externalData = true;
            }

            public SecurityDataBaseHelper() throws SQLException {
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

            private void loadDataFromDB() throws SQLException {

                Connection con = Publisher.getDBConnection();
                Statement statement = null;

                if(con != null) {
                    try {
                        statement = con.createStatement();
                        ResultSet resultSet = statement.executeQuery(SecurityDataBaseHelper.SQL_QUERY);

                        if(resultSet != null){
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

            public boolean saveDataToDB() throws SQLException {
                if(!this.externalData) {
                    return false;
                }

                Connection con = Publisher.getDBConnection();
                if(con != null) {
                    Statement statement = null;
                    ResultSet resultSet = null;

                    try {
                        statement = con.createStatement();
                        resultSet = statement.executeQuery(SecurityDataBaseHelper.SQL_QUERY);
                    }catch (SQLException e) {

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
                    }

                    if(resultSet == null){
                        PreparedStatement preparedStatement = null;
                        try {
                            preparedStatement = con.prepareStatement(SecurityDataBaseHelper.SQL_INSERT);

                            preparedStatement.setInt(1, 0);
                            preparedStatement.setBytes(2, this.secretKeyData);
                            preparedStatement.setBytes(3, this.referenzeTextData);
                            preparedStatement.setBytes(4, this.salt);

                            if (preparedStatement.execute()) {
                                this.externalData = false;
                                return true;
                            }
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

            public static SecretKey firstInit(String password) throws InvalidKeySpecException, NoSuchAlgorithmException, SQLException {
                try {
                    Cipher cipher = Cipher.getInstance(SecurityProvider.ALGORITHM);

                    byte[] salt = generateSalt();
                    SecretKey secret = prepareKey(password,salt);

                    SecretKey secretKey = generateDBEncryptionKey();

                    cipher.init(Cipher.ENCRYPT_MODE,secret);
                    byte[] dbKeyEncrypted = cipher.doFinal(secretKey.getEncoded());
                    byte[] refTextEncrypted = cipher.doFinal(SecurityProvider.REFERENCE_TEXT.getBytes(StandardCharsets.UTF_8));

                    SecurityDataBaseHelper sdb = new SecurityProvider.SecurityDataBaseHelper(dbKeyEncrypted,refTextEncrypted, salt);

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

    public static void main(String[] args) throws SQLException, IOException {

/*SecurityProvider sp = new SecurityProvider();
sp.firstInit("txla13/$Z");*/


 //    SecurityProvider sp = new SecurityProvider("txla13/$Z");
/*

        sp.unlock("txla13/$Z");
        System.out.println(sp.isLocked());


        Connection con = DatabaseConnectionFactory.createNewConnectionInstance();

        String text = "Dies ist ein Text2!";
        int zahl = 65;
        double kommazahl = 7.145;

      PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO test1 VALUES (?,?,?,?)");

        preparedStatement.setInt(1,0);
        preparedStatement.setBytes(2, sp.encrypt(text));
        preparedStatement.setBytes(3, sp.encrypt(zahl));
        preparedStatement.setBytes(4, sp.encrypt(kommazahl));

        preparedStatement.execute();

        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT text, zahl, kommazahl FROM test1 WHERE id  = 0");

        String text_d = sp.decryptAsString(resultSet.getBytes("text"));
        int zahl_d = sp.decryptAsInt(resultSet.getBytes("zahl"));
        double kommazahl_d = sp.decryptAsDouble(resultSet.getBytes("kommazahl"));

        System.out.println(text_d + "\n");
        System.out.println(zahl_d + "\n");
        System.out.println(kommazahl_d + "\n");
*/




    }
}

