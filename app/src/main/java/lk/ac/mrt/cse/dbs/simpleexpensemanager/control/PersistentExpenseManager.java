package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;
/**
 * Created by Tharaka_kamal on 11/20/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;

public class PersistentExpenseManager extends ExpenseManager {
    public Context cntx;

    public PersistentExpenseManager(Context cnt) throws ExpenseManagerException {
        this.cntx = cnt;
        setup();
    }

    @Override
    public void setup() throws ExpenseManagerException {
        SQLiteDatabase database = cntx.openOrCreateDatabase("140506U", cntx.MODE_PRIVATE, null);

        database.execSQL("CREATE TABLE IF NOT EXISTS Account (Account_no VARCHAR PRIMARY KEY, Bank VARCHAR, Holder VARCHAR, Initial_amt REAL);");

        PersistentAccountDAO accDAO = new PersistentAccountDAO(database);

        setAccountsDAO(accDAO);

        setTransactionsDAO(new PersistentTransactionDAO(database));
    }
}
