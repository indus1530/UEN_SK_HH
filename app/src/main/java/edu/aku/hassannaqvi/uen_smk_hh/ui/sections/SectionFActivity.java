package edu.aku.hassannaqvi.uen_smk_hh.ui.sections;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.uen_smk_hh.R;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.KishMWRAContract;
import edu.aku.hassannaqvi.uen_smk_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_smk_hh.core.MainApp;
import edu.aku.hassannaqvi.uen_smk_hh.databinding.ActivitySectionFBinding;
import edu.aku.hassannaqvi.uen_smk_hh.utils.Util;

public class SectionFActivity extends AppCompatActivity {

    ActivitySectionFBinding bi;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_f);
        bi.setCallback(this);

        setUIComponent();
        setCoronaFields();

        //MainApp.kish.set_UUID(MainApp.fc.get_UID());

        //Toast.makeText(this, "Form : " + MainApp.fc.get_UID(), Toast.LENGTH_SHORT).show();

        //bi.formNo.setText(MainApp.fc.get_UID());

        db = MainApp.appInfo.getDbHelper();
        List<String> mwras = db.getMWRAS(MainApp.fc.get_UID());
        bi.mwra.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, mwras));
        bi.mwra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(!(bi.mwra.getSelectedItem().toString() == "....")) {

                    // your code here
                    String merged_value = bi.mwra.getSelectedItem().toString();
                    String[] merged_value_sep = merged_value.split(":");

                    //Toast.makeText(getApplicationContext(), "Value1: " + merged_value_sep[0], Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Value2: " + merged_value_sep[1], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        /*if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {

                Toast.makeText(this, "Serial No: " + cursor.getString(cursor.getColumnIndex("serial_no")), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Serial No: " + cursor.getString(cursor.getColumnIndex("name")), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Serial No: " + MainApp.fc.get_UID(), Toast.LENGTH_SHORT).show();

                cursor.moveToNext();
            }
        }*/
    }

    private void setCoronaFields() {

        if (!MainApp.selectedKishMWRA.isCoronaCase()) {
            bi.f101aj.setVisibility(View.GONE);
            bi.f101ak.setVisibility(View.GONE);
            bi.f101al.setVisibility(View.GONE);
            bi.f101am.setVisibility(View.GONE);
            bi.f101an.setVisibility(View.GONE);
            bi.f101ao.setVisibility(View.GONE);
            bi.f102d.setVisibility(View.GONE);
        }
    }

    void setUIComponent() {

        /*bi.f101Name.setText(new StringBuilder(MainApp.selectedKishMWRA.getName().toUpperCase()).append("\n")
                .append(getResources().getString(R.string.d101))
                .append(":")
                .append(MainApp.selectedKishMWRA.getSerialno()));*/

        bi.f101.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.f101a.getId()) {
                Clear.clearAllFields(bi.fldGrpCVf101a);
            } else {
                Clear.clearAllFields(bi.fldGrpSectionFA);
                Clear.clearAllFields(bi.fldGrpSectionFB);
            }
        }));


        //f110i
        bi.f11097.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Clear.clearAllFields(bi.f110check, false);
            } else {
                Clear.clearAllFields(bi.f110check, true);
            }
        });

        bi.f112.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i != bi.f112a.getId()) {
                Clear.clearAllFields(bi.fldGrpCVf113);
            }
        }));

        bi.f114.setOnCheckedChangeListener((radioGroup, i) -> {

            if (i != bi.f114a.getId()) {
                Clear.clearAllFields(bi.fldGrp1520);
            }
        });
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
                startActivity(new Intent(this, SectionF02Activity.class));
            }

        }
    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        long updcount = db.addKishMWRA(MainApp.kish);
        MainApp.kish.set_ID(String.valueOf(updcount));
        if (updcount > 0) {
            MainApp.kish.setUID(MainApp.kish.getDeviceId() + MainApp.kish.get_ID());
            db.updatesKishMWRAColumn(KishMWRAContract.SingleKishMWRA.COLUMN_UID, MainApp.kish.getUID());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void SaveDraft() throws JSONException {

        MainApp.kish = new KishMWRAContract();
        MainApp.kish.set_UUID(MainApp.fc.get_UID());
        MainApp.kish.setDeviceId(MainApp.appInfo.getDeviceID());
        MainApp.kish.setDevicetagID(MainApp.fc.getDevicetagID());
        MainApp.kish.setFormDate(MainApp.fc.getFormDate());
        MainApp.kish.setUser(MainApp.fc.getUser());

        JSONObject json = new JSONObject();

        json.put("fm_uid", MainApp.selectedKishMWRA.getUid());
        //json.put("fm_serial", MainApp.selectedKishMWRA.getSerialno());
        json.put("fm_serial", bi.mwra.getSelectedItem().toString().split(":")[0].trim());
        json.put("kishmwra_name", bi.mwra.getSelectedItem().toString().split(":")[1].trim());
        json.put("hhno", MainApp.fc.getHhno());
        json.put("cluster_no", MainApp.fc.getClusterCode());
        json.put("_luid", MainApp.fc.getLuid());
        json.put("appversion", MainApp.appInfo.getAppVersion());
        json.put("sysdate", new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime()));

        json.put("f001", bi.f001a.isChecked() ? "1"
                : bi.f001b.isChecked() ? "2"
                : "-1");

        json.put("f002", bi.f002a.isChecked() ? "1"
                : bi.f002b.isChecked() ? "2"
                : "-1");

        json.put("f101", bi.f101a.isChecked() ? "1" :
                bi.f101b.isChecked() ? "2" : "-1");

        json.put("f10101a", bi.f101aa.isChecked() ? "1" : "-1");
        json.put("f10101b", bi.f101ab.isChecked() ? "2" : "-1");
        json.put("f10101c", bi.f101ac.isChecked() ? "3" : "-1");
        json.put("f10101d", bi.f101ad.isChecked() ? "4" : "-1");
        json.put("f10101e", bi.f101ae.isChecked() ? "5" : "-1");
        json.put("f10101f", bi.f101af.isChecked() ? "6" : "-1");
        json.put("f10101g", bi.f101ag.isChecked() ? "7" : "-1");
        json.put("f10101h", bi.f101ah.isChecked() ? "8" : "-1");
        json.put("f10101i", bi.f101ai.isChecked() ? "9" : "-1");
        json.put("f10101j", bi.f101aj.isChecked() ? "10" : "-1");
        json.put("f10101k", bi.f101ak.isChecked() ? "11" : "-1");
        json.put("f10101l", bi.f101al.isChecked() ? "12" : "-1");
        json.put("f10101m", bi.f101am.isChecked() ? "13" : "-1");
        json.put("f10101n", bi.f101an.isChecked() ? "14" : "-1");
        json.put("f10101o", bi.f101ao.isChecked() ? "15" : "-1");

        /*json.put("f102a", bi.f102a.isChecked() ? "1" : "0");*/
        json.put("f102a", bi.f102b.isChecked() ? "1" : "-1");
        json.put("f102b", bi.f102c.isChecked() ? "2" : "-1");
        json.put("f102c", bi.f102d.isChecked() ? "3" : "-1");
        json.put("f102d", bi.f102e.isChecked() ? "4" : "-1");
        json.put("f102e", bi.f102f.isChecked() ? "5" : "-1");
        json.put("f102f", bi.f102g.isChecked() ? "6" : "-1");
        json.put("f102g", bi.f102h.isChecked() ? "7" : "-1");
        json.put("f102h", bi.f102i.isChecked() ? "8" : "-1");
        json.put("f102i", bi.f102j.isChecked() ? "9" : "-1");
        json.put("f102j", bi.f102k.isChecked() ? "10" : "-1");

        /*json.put("f102k", bi.f102l.isChecked() ? "12" : "-1");
        json.put("f102l", bi.f102m.isChecked() ? "13" : "-1");
        json.put("f10296", bi.f10296.isChecked() ? "96" : "-1");
        json.put("f10296x", bi.f10296x.getText().toString().trim().isEmpty() ? "-1" : bi.f10296x.getText().toString());*/

        json.put("f103a", bi.f103a.isChecked() ? "1" : "-1");
        json.put("f103b", bi.f103b.isChecked() ? "2" : "-1");
        json.put("f103c", bi.f103c.isChecked() ? "3" : "-1");
        json.put("f103d", bi.f103d.isChecked() ? "4" : "-1");
        /*json.put("f103e", bi.f103e.isChecked() ? "5" : "0");*/

        json.put("f104", bi.f104.getText().toString().trim().isEmpty() ? "-1" : bi.f104.getText().toString());

        json.put("f105", bi.f105a.isChecked() ? "1"
                : bi.f105b.isChecked() ? "2"
                : bi.f105c.isChecked() ? "3"
                : bi.f105d.isChecked() ? "4"
                : "-1");

        json.put("f106", bi.f106.getText().toString().trim().isEmpty() ? "-1" : bi.f106.getText().toString());

        json.put("f107", bi.f107a.isChecked() ? "1"
                : bi.f107b.isChecked() ? "2"
                : bi.f10796.isChecked() ? "96"
                : "-1");
        json.put("f10796x", bi.f10796x.getText().toString().trim().isEmpty() ? "-1" : bi.f10796x.getText().toString());

        json.put("f108", bi.f108a.isChecked() ? "1"
                : bi.f108b.isChecked() ? "2"
                /*: bi.f10898.isChecked() ? "98"*/
                : "-1");
        json.put("f108aw", bi.f108aw.getText().toString().trim().isEmpty() ? "-1" : bi.f108aw.getText().toString());
        json.put("f108bm", bi.f108bm.getText().toString().trim().isEmpty() ? "-1" : bi.f108bm.getText().toString());

       /* json.put("f109", bi.f109b.isChecked() ? "1" : "0");*/
        json.put("f109a", bi.f109a.getText().toString().trim().isEmpty() ? "-1" : bi.f109a.getText().toString());

        json.put("f110a", bi.f110a.isChecked() ? "1" : "-1");
        json.put("f110b", bi.f110b.isChecked() ? "2" : "-1");
        json.put("f110c", bi.f110c.isChecked() ? "3" : "-1");
        json.put("f110d", bi.f110d.isChecked() ? "4" : "-1");
        json.put("f110e", bi.f110e.isChecked() ? "5" : "-1");
        json.put("f110f", bi.f110f.isChecked() ? "6" : "-1");
        json.put("f110g", bi.f110g.isChecked() ? "7" : "-1");
        json.put("f110h", bi.f110h.isChecked() ? "8" : "-1");
        json.put("f11097", bi.f11097.isChecked() ? "97" : "-1");
        json.put("f11096", bi.f11096.isChecked() ? "96" : "-1");
        json.put("f11096x", bi.f11096x.getText().toString().trim().isEmpty() ? "-1" : bi.f11096x.getText().toString());

        json.put("f111", bi.f111a.isChecked() ? "1" :
                bi.f111b.isChecked() ? "2" : "-1");

        json.put("f112", bi.f112a.isChecked() ? "1" :
                bi.f112b.isChecked() ? "2" :
                       /* bi.f112c.isChecked() ? "98" :*/ "-1");

        json.put("f113", bi.f113.getText().toString().trim().isEmpty() ? "-1" : bi.f113.getText().toString());

        json.put("f114", bi.f114a.isChecked() ? "1" :
                bi.f114b.isChecked() ? "2" : "-1");

        json.put("f115a", bi.f115a.isChecked() ? "1" : "-1");
        json.put("f115b", bi.f115b.isChecked() ? "2" : "-1");
        json.put("f115c", bi.f115c.isChecked() ? "3" : "-1");
        json.put("f115d", bi.f115d.isChecked() ? "4" : "-1");
        json.put("f115e", bi.f115e.isChecked() ? "5" : "-1");
        json.put("f115f", bi.f115f.isChecked() ? "6" : "-1");

        json.put("f116a", bi.f116a.isChecked() ? "1" : "-1");
        json.put("f116b", bi.f116b.isChecked() ? "2" : "-1");
        json.put("f116c", bi.f116c.isChecked() ? "3" : "-1");
        json.put("f116d", bi.f116d.isChecked() ? "4" : "-1");
        json.put("f116e", bi.f116e.isChecked() ? "5" : "-1");
        json.put("f116f", bi.f116f.isChecked() ? "6" : "-1");
        json.put("f116g", bi.f116g.isChecked() ? "7" : "-1");
        json.put("f116h", bi.f116h.isChecked() ? "8" : "-1");
        json.put("f116i", bi.f116i.isChecked() ? "9" : "-1");

        json.put("f117", bi.f117a.isChecked() ? "1" :
                bi.f117b.isChecked() ? "2" :
                        bi.f117c.isChecked() ? "3" :
                                bi.f117d.isChecked() ? "4" :
                                        bi.f117e.isChecked() ? "5" : "-1");

        json.put("f118a", bi.f118a.getText().toString().trim().isEmpty() ? "-1" : bi.f118a.getText().toString());
        json.put("f118b", bi.f118b.getText().toString().trim().isEmpty() ? "-1" : bi.f118b.getText().toString());
       /* json.put("f118", bi.f11898.isChecked() ? "98" : "0");*/

        json.put("f119", bi.f119a.isChecked() ? "1"
                : bi.f119b.isChecked() ? "2"
                : "-1");

        json.put("f120a", bi.f120a.getText().toString().trim().isEmpty() ? "-1" : bi.f120a.getText().toString());
        json.put("f12098", bi.f12098.isChecked() ? "98" : "-1");

        MainApp.kish.setsF(String.valueOf(json));

    }

    private boolean formValidation() {

        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionF);

    }

    public void BtnEnd() {

        Util.openEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }

}
