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

    private static final String SQL_QUERY = "SELECT id, customer_id, title, creation_date, to_pay2date, paid_on_date, paid, comment, billfile, taxfree, tax_included " +
                                            "FROM bill WHERE strftime('%Y', creation_date) = ? " +
                                            "OR strftime('%Y', to_pay2date) = ? " +
                                            "OR strftime('%Y', paid_on_date) = ? ";
    private static final String SQL_QUERY_WITH_ID = "SELECT id, customer_id, title, creation_date, to_pay2date, paid_on_date, paid, comment, billfile, taxfree, tax_included FROM bill WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO bill (id, customer_id, title, creation_date, to_pay2date, paid_on_date, paid, comment, billfile, taxfree, tax_included) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
    private static final String SQL_UPDATE = "UPDATE bill set title=?, creation_date=?, to_pay2date=?, paid_on_date=?, paid=?, comment=?, billfile=?, taxfree=?, tax_included=? WHERE ID=?";
    private static final String SQL_DELETE = "DELETE FROM bill WHERE id = ?";

    public static List<Bill> readAllBillsInYear(int year) throws SQLException, UnsupportedEncodingException {
        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = null;
        List<Bill> bills = new ArrayList<>();
        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement = connection.prepareStatement(SQL_QUERY);
            preparedStatement.setInt(1,year);
            preparedStatement.setInt(2,year);
            preparedStatement.setInt(3,year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                String id = resultSet.getString("id");
                String customerId = resultSet.getString("customer_id");

                Customer debtor = CustomerService.readCustomer(customerId);

                String titel = securityProvider.decryptAsString(resultSet.getBytes("title"));
                Date creationDate = new Date(securityProvider.decryptAsLong(resultSet.getBytes("creation_date")));
                Date toPayToDate = new Date(securityProvider.decryptAsLong(resultSet.getBytes("to_pay2date")));
                Date paidOnDate = new Date(securityProvider.decryptAsLong(resultSet.getBytes("paid_on_date")));
                boolean paid = (resultSet.getInt("paid") != 0);
                String comment = securityProvider.decryptAsString(resultSet.getBytes("comment"));
                File billFile = new File(securityProvider.decryptAsString(resultSet.getBytes("billfile")));

                boolean taxFree = (resultSet.getInt("taxfree") != 0);
                boolean taxIncluded = (resultSet.getInt("tax_included") != 0);

                Bill bill = new Bill(id,debtor,titel,creationDate,toPayToDate,paidOnDate,paid,comment,billFile,taxFree,taxIncluded);
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

    public static boolean save(Bill bill) throws SQLException, UnsupportedEncodingException {

/*
        if(customer != null && CustomerService.readCustomer(customer.getId()) != null){
            return CustomerService.modifyCustomer(customer);
        }
*/

        Connection connection = Publisher.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
        SecurityProvider securityProvider = Publisher.getSecurityProvider();
        try {
            preparedStatement.setString(1, bill.getId());
            preparedStatement.setBytes(2,securityProvider.encrypt(bill.getDebtor().getId()));
            preparedStatement.setBytes(3,securityProvider.encrypt(bill.getTitel()));
            preparedStatement.setBytes(4,securityProvider.encrypt(bill.getCreationDate().getTime()));
            preparedStatement.setBytes(5,securityProvider.encrypt(bill.getToPayToDate().getTime()));
            preparedStatement.setBytes(6,securityProvider.encrypt(bill.getPaidOnDate().getTime()));
            preparedStatement.setBytes(7,securityProvider.encrypt(bill.isPaid() ? 1 : 0));
            preparedStatement.setBytes(8,securityProvider.encrypt(bill.getComment()));
            preparedStatement.setBytes(9,securityProvider.encrypt(bill.getBillFile().getAbsolutePath()));
            preparedStatement.setBytes(10,securityProvider.encrypt(bill.isBusinessTaxFree() ? 1 : 0));
            preparedStatement.setBytes(11,securityProvider.encrypt(bill.mustBeIncludedTaxes() ? 1 : 0));

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

}
