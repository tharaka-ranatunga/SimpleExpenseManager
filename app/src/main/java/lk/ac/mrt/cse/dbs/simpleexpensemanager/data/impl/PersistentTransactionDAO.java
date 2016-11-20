package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;
/**
 * Created by Tharaka_kamal on 11/20/2016.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {
    public SQLiteDatabase database;

    public PersistentTransactionDAO(SQLiteDatabase db){
        this.database = db;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        String sql = "INSERT INTO TransactionLog (Account_no, Type, Amt, Log_date) VALUES (?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1,accountNo);
        statement.bindLong(2,(expenseType == ExpenseType.EXPENSE) ? 0 : 1);
        statement.bindDouble(3,amount);
        statement.bindLong(4,date.getTime());

        statement.executeInsert();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        Cursor result = database.rawQuery("SELECT * FROM TransactionLog",null);
        List<Transaction> transactions = new ArrayList<Transaction>();

        if(result.moveToFirst()) {
            do{
                Transaction trans = new Transaction(new Date(result.getLong(result.getColumnIndex("Log_date"))), result.getString(result.getColumnIndex("Account_no")), (result.getInt(result.getColumnIndex("Type")) == 0) ? ExpenseType.EXPENSE : ExpenseType.INCOME, result.getDouble(result.getColumnIndex("Amt")));
                transactions.add(trans);
            }while (result.moveToNext());
        }
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        Cursor result = database.rawQuery("SELECT * FROM TransactionLog LIMIT " + limit,null);
        List<Transaction> transactions = new ArrayList<Transaction>();

        if(result.moveToFirst()) {
            do {
                Transaction trans = new Transaction(new Date(result.getLong(result.getColumnIndex("Log_date"))), result.getString(result.getColumnIndex("Account_no")), (result.getInt(result.getColumnIndex("Type")) == 0) ? ExpenseType.EXPENSE : ExpenseType.INCOME, result.getDouble(result.getColumnIndex("Amt")));
                transactions.add(trans);
            } while (result.moveToNext());
        }

        return transactions;
    }
}
