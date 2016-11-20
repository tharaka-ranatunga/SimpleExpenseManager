package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;

        import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
        import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
        import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
/**
 * Created by Tharaka_kamal on 11/20/2016.
 */
public class PersistentExpenseManager extends ExpenseManager {
    public Context cxt;

    public PersistentExpenseManager(Context c) throws ExpenseManagerException {
        this.cxt = c;
        setup();
    }

    @Override
    public void setup() throws ExpenseManagerException {
        SQLiteDatabase db = cxt.openOrCreateDatabase("140457", cxt.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS Account (Account_no VARCHAR PRIMARY KEY, Bank VARCHAR, Holder VARCHAR, Initial_amt REAL);");

        PersistentAccountDAO acntDAO = new PersistentAccountDAO(db);

        setAccountsDAO(acntDAO);

        setTransactionsDAO(new PersistentTransactionDAO(db));
    }
}
