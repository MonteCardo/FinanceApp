package br.com.gabryel.financeapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.gabryel.financeapp.investment.Investment;
import br.com.gabryel.financeapp.investment.Moneyable;
import br.com.gabryel.financeapp.investment.MonthlyInvestment;
import br.com.gabryel.financeapp.investment.Movement;
import br.com.gabryel.financeapp.investment.SimpleMovement;
import br.com.gabryel.financeapp.investment.SimpleWallet;
import br.com.gabryel.financeapp.investment.Wallet;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static String LOG_TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
