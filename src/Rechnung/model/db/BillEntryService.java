package Rechnung.model.db;

import Rechnung.Debug;
import Rechnung.Publisher;
import Rechnung.model.SecurityProvider;
import Rechnung.model.objects.*;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillEntryService {
    private static final String SQL_QUERY = "SELECT id, bill_id, entrytext, tax_rate_in_percent, unitprice, amount FROM billentry";
    private static final String SQL_QUERY_WITH_ID = "SELECT id, bill_id, entrytext, tax_rate_in_percent, unitprice, amount FROM billentry WHERE id = ?";
    private static final String SQL_QUERY_WITH_BILL_ID = "SELECT id, bill_id, entrytext, tax_rate_in_percent, unitprice, amount FROM billentry WHERE bill_id = ?";
    private static final String SQL_INSERT = "INSERT INTO billentry (id, bill_id, entrytext, tax_rate_in_percent, unitprice, amount) VALUES (?,?,?,?,?,?);";
    private static final String SQL_UPDATE = "UPDATE billentry set entrytext=?, tax_rate_in_percent=?, unitprice=?, amount=? WHERE ID=?";
    private static final String SQL_DELETE = "DELETE FROM billentry WHERE id = ?";
    public static final String SQL_DELETE_BY_BILL = "DELETE FROM billentry WHERE bill_id = ?";

    public static void saveListOfBillEntries(List<BillEntry> list, String billId) throws SQLException, UnsupportedEncodingException {
        for (BillEntry billEntry : list) {
            BillEntryService.write(billEntry,billId);
        }
    }

    public static void write(BillEntry billEntry, String billId) throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();

        PreparedStatement preparedStatement = null;

        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement = connection.prepareStatement(SQL_INSERT);


            preparedStatement.setString(1,billEntry.getId());
            preparedStatement.setString(2,billId);
            preparedStatement.setBytes(3,securityProvider.encrypt(billEntry.getEntryText()));
            preparedStatement.setBytes(4,securityProvider.encrypt(billEntry.getTaxRateInPercent()));
            preparedStatement.setBytes(5,securityProvider.encrypt(billEntry.getUnitPrice()));
            preparedStatement.setBytes(6,securityProvider.encrypt(billEntry.getAmount()));

            preparedStatement.execute();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    if (Debug.ON) {
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }
        }
    }

    public static List<BillEntry> loadBillEntriesByBill(String billId) throws SQLException, UnsupportedEncodingException {
        List<BillEntry> billEntries = new ArrayList<>();
        Connection connection = Publisher.getDBConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_WITH_BILL_ID);

        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement.setString(1,billId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null && !resultSet.isClosed()){

                while(resultSet.next()) {
                    String id = resultSet.getString("id");
                    String entryText = securityProvider.decryptAsString(resultSet.getBytes("entrytext"));
                    int taxRateInPercent = securityProvider.decryptAsInt(resultSet.getBytes("tax_rate_in_percent"));
                    double unitPrice = securityProvider.decryptAsDouble(resultSet.getBytes("unitprice"));
                    int amount = securityProvider.decryptAsInt(resultSet.getBytes("amount"));

                    BillEntry billEntry = new BillEntry(id,taxRateInPercent,unitPrice,amount,entryText);
                    billEntries.add(billEntry);
                }
            }
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    if (Debug.ON) {
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }
        }

        return billEntries;
    }

    public static void remove(BillEntry billEntry) throws SQLException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_DELETE);


            preparedStatement.setString(1,billEntry.getId());

            preparedStatement.execute();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    if (Debug.ON) {
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }
        }
    }

    public static void removeBillEntriesFromBill(String billId) throws SQLException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(SQL_DELETE_BY_BILL);


            preparedStatement.setString(1,billId);

            preparedStatement.execute();

        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    if (Debug.ON) {
                        System.err.println("Fehler: " + String.format("%n") + "-------------------------------------" + String.format("%n") + e.getStackTrace().toString());
                    }
                }

            }
        }
    }



}
