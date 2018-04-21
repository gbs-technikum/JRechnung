package Rechnung.model.db;

import Rechnung.Debug;
import Rechnung.Publisher;
import Rechnung.model.SecurityProvider;
import Rechnung.model.objects.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillService {

    private static final String SQL_QUERY = "SELECT id, customer_id, title, bill_number, creation_date, to_pay2date, paid_on_date, paid, comment, billfile, taxfree, tax_included " +
                                            "FROM bill WHERE  strftime(\"%Y\",datetime(creation_date/1000,'unixepoch','localtime')) = ? ";
    private static final String SQL_QUERY_WITH_ID = "SELECT id, customer_id, title, bill_number, creation_date, to_pay2date, paid_on_date, paid, comment, billfile, taxfree, tax_included FROM bill WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO bill (id, customer_id, title, bill_number, creation_date, to_pay2date, paid_on_date, paid, comment, billfile, taxfree, tax_included) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
    private static final String SQL_UPDATE = "UPDATE bill set title=?, creation_date=?, to_pay2date=?, paid_on_date=?, paid=?, comment=?, billfile=?, taxfree=?, tax_included=? WHERE ID=?";
    private static final String SQL_DELETE = "DELETE FROM bill WHERE id = ?";

    public static List<Bill> readAllBillsInYear(int year) throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = null;
        List<Bill> bills = new ArrayList<>();
        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setString(1,String.valueOf(year));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                String id = resultSet.getString("id");
                String customerId = resultSet.getString("customer_id");

                Customer debtor = CustomerService.readCustomer(customerId);

                String titel = securityProvider.decryptAsString(resultSet.getBytes("title"));
                String billNumber = resultSet.getString("bill_number");
                Date creationDate = new Date(resultSet.getLong("creation_date"));
                Date toPayToDate = new Date(securityProvider.decryptAsLong(resultSet.getBytes("to_pay2date")));
                Date paidOnDate = new Date(securityProvider.decryptAsLong(resultSet.getBytes("paid_on_date")));
                boolean paid = (resultSet.getInt("paid") != 0);
                String comment = securityProvider.decryptAsString(resultSet.getBytes("comment"));
                File billFile = new File(securityProvider.decryptAsString(resultSet.getBytes("billfile")));

                boolean taxFree = (resultSet.getInt("taxfree") != 0);
                boolean taxIncluded = (resultSet.getInt("tax_included") != 0);

                Bill bill = new Bill(id,billNumber,debtor,titel,creationDate,toPayToDate,paidOnDate,paid,comment,billFile,taxFree,taxIncluded);
                bills.add(bill);
            }
            for (Bill bill : bills) {
                List<BillEntry> list = BillEntryService.loadBillEntriesByBill(bill.getId());

                for (BillEntry billEntry : list) {
                    bill.addEntry(billEntry);
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
            preparedStatement = null;
        }

        return bills;
    }

    public static Bill read(String id) throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = null;
        Bill bill = null;
        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement = connection.prepareStatement(SQL_QUERY_WITH_ID);
            preparedStatement.setString(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                String customerId = resultSet.getString("customer_id");


                Customer debtor = null;

                if(customerId != null){
                    debtor = CustomerService.readCustomer(customerId);
                }

                String titel = securityProvider.decryptAsString(resultSet.getBytes("title"));
                String billNumber = resultSet.getString("bill_number");
                Date creationDate = new Date(resultSet.getLong("creation_date"));
                Date toPayToDate = new Date(securityProvider.decryptAsLong(resultSet.getBytes("to_pay2date")));
                Date paidOnDate = new Date(securityProvider.decryptAsLong(resultSet.getBytes("paid_on_date")));
                boolean paid = (resultSet.getInt("paid") != 0);
                String comment = securityProvider.decryptAsString(resultSet.getBytes("comment"));
                File billFile = new File(securityProvider.decryptAsString(resultSet.getBytes("billfile")));

                boolean taxFree = (resultSet.getInt("taxfree") != 0);
                boolean taxIncluded = (resultSet.getInt("tax_included") != 0);

                bill = new Bill(id,billNumber,debtor,titel,creationDate,toPayToDate,paidOnDate,paid,comment,billFile,taxFree,taxIncluded);
                List<BillEntry> list = BillEntryService.loadBillEntriesByBill(bill.getId());

                for (BillEntry billEntry : list) {
                    bill.addEntry(billEntry);
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
            preparedStatement = null;
        }

        return bill;
    }

    public static boolean modify(Bill bill) throws SQLException, UnsupportedEncodingException {

        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);
        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement.setBytes(1,securityProvider.encrypt(bill.getTitel()));
            preparedStatement.setLong(2,bill.getCreationDate().getTime());
            preparedStatement.setBytes(3,securityProvider.encrypt(bill.getToPayToDate().getTime()));
            preparedStatement.setBytes(4,securityProvider.encrypt(bill.getPaidOnDate().getTime()));
            preparedStatement.setBytes(5,securityProvider.encrypt(bill.isPaid() ? 1 : 0));
            preparedStatement.setBytes(6,securityProvider.encrypt(bill.getComment()));
            preparedStatement.setBytes(7,securityProvider.encrypt(bill.getBillFile().getAbsolutePath()));
            preparedStatement.setBytes(8,securityProvider.encrypt(bill.isBusinessTaxFree() ? 1 : 0));
            preparedStatement.setBytes(9,securityProvider.encrypt(bill.mustBeIncludedTaxes() ? 1 : 0));
            preparedStatement.setString(10,bill.getId());

            preparedStatement.execute();

            BillEntryService.removeBillEntriesFromBill(bill.getId());

            BillEntryService.saveListOfBillEntries(bill.getBillEntries(),bill.getId());

            return true;
        }finally {
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

    public static boolean save(Bill bill) throws SQLException, UnsupportedEncodingException {

        if(bill != null && BillService.read(bill.getId()) != null){
            return BillService.modify(bill);
        }

        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement.setString(1, bill.getId());
            preparedStatement.setString(2,bill.getDebtor().getId());
            preparedStatement.setBytes(3,securityProvider.encrypt(bill.getTitel()));
            preparedStatement.setString(4,bill.getNumber());
            preparedStatement.setLong(5,bill.getCreationDate().getTime());
            preparedStatement.setBytes(6,securityProvider.encrypt(bill.getToPayToDate().getTime()));

            Date paidOnDate = bill.getPaidOnDate();
            if(paidOnDate != null){
                preparedStatement.setBytes(7,securityProvider.encrypt(bill.getPaidOnDate().getTime()));
            }else {
                preparedStatement.setNull(7,Types.BLOB);
            }

            preparedStatement.setBytes(8,securityProvider.encrypt(bill.isPaid() ? 1 : 0));
            preparedStatement.setBytes(9,securityProvider.encrypt(bill.getComment()));
            preparedStatement.setBytes(10,securityProvider.encrypt(bill.getBillFile().getAbsolutePath()));
            preparedStatement.setBytes(11,securityProvider.encrypt(bill.isBusinessTaxFree() ? 1 : 0));
            preparedStatement.setBytes(12,securityProvider.encrypt(bill.mustBeIncludedTaxes() ? 1 : 0));

            preparedStatement.execute();
            BillEntryService.saveListOfBillEntries(bill.getBillEntries(),bill.getId());

            return true;
        }finally {
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

    public static boolean remove(Bill bill) throws SQLException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);
        try {
            preparedStatement.setString(1,bill.getId());

            preparedStatement.execute();

            BillEntryService.removeBillEntriesFromBill(bill.getId());

            return true;
        }finally {
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
