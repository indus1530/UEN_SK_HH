package edu.aku.hassannaqvi.uen_smk_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.uen_smk_hh.R;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.KishMWRAContract;
import edu.aku.hassannaqvi.uen_smk_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_smk_hh.core.MainApp;
import edu.aku.hassannaqvi.uen_smk_hh.databinding.ActivitySectionAh7Binding;
import edu.aku.hassannaqvi.uen_smk_hh.utils.Util;

public class SectionAH7Activity extends AppCompatActivity {

    ActivitySectionAh7Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ah7);
        bi.setCallback(this);

        setupSkips();

    }

    private void setupSkips() {

        //h102
        /*bi.h102.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId != bi.h102a.getId()) {
                bi.fldGrpCVh103.setVisibility(View.VISIBLE);
                bi.fldGrpCVh104.setVisibility(View.VISIBLE);
            } else {
                Clear.clearAllFields(bi.fldGrpCVh103);
                Clear.clearAllFields(bi.fldGrpCVh104);
                bi.fldGrpCVh103.setVisibility(View.GONE);
                bi.fldGrpCVh104.setVisibility(View.GONE);
            }
        });*/


    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(this, SectionH102Activity.class));
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {

        Util.openEndActivity(this);
    }

    private boolean UpdateDB() {

        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesKishMWRAColumn(KishMWRAContract.SingleKishMWRA.COLUMN_SH1, MainApp.kish.getsH1());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("ah49a", bi.ah49a.isChecked() ? "1" : "-1");

        json.put("ah49b", bi.ah49b.isChecked() ? "2" : "-1");

        json.put("ah49c", bi.ah49c.isChecked() ? "98" : "-1");

        json.put("ah50", bi.ah50a.isChecked() ? "1"
                : bi.ah50b.isChecked() ? "2"
                : bi.ah50c.isChecked() ? "98"
                : "-1");

        json.put("ah50aaa", bi.ah50aaa.getText().toString());

        json.put("ah50aab", bi.ah50aab.getText().toString());

        json.put("ah51", bi.ah51a.isChecked() ? "1"
                : bi.ah51b.isChecked() ? "2"
                : bi.ah51c.isChecked() ? "3"
                : bi.ah51d.isChecked() ? "4"
                : bi.ah51e.isChecked() ? "5"
                : "-1");

        json.put("ah52", bi.ah52.getText().toString());

        json.put("ah53", bi.ah53a.isChecked() ? "1"
                : bi.ah53b.isChecked() ? "2"
                : "-1");

        json.put("ah54", bi.ah54a.isChecked() ? "1"
                : bi.ah54b.isChecked() ? "98"
                : "-1");

        json.put("ah54ax", bi.ah54ax.getText().toString());
        json.put("ah55", bi.ah55a.isChecked() ? "1"
                : bi.ah55b.isChecked() ? "2"
                : bi.ah55c.isChecked() ? "3"
                : bi.ah55d.isChecked() ? "7"
                : "-1");

        json.put("ah56a", bi.ah56a.getText().toString());

        json.put("ah57", "-1");

        json.put("ah57a", bi.ah57a.getText().toString());

        json.put("ah57b", "-1");

        json.put("ah58", bi.ah58.getText().toString());

        json.put("ah 59", bi.ah59.getText().toString());

        json.put("ah60", bi.ah60.getText().toString());


        MainApp.kish.setsH1(String.valueOf(json));
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionAH7);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}