package edu.aku.hassannaqvi.uen_smk_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.uen_smk_hh.R;
import edu.aku.hassannaqvi.uen_smk_hh.contracts.KishMWRAContract;
import edu.aku.hassannaqvi.uen_smk_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.uen_smk_hh.core.MainApp;
import edu.aku.hassannaqvi.uen_smk_hh.databinding.ActivitySectionGBinding;
import edu.aku.hassannaqvi.uen_smk_hh.utils.Util;

public class SectionGActivity extends AppCompatActivity {

    ActivitySectionGBinding bi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_g);
        bi.setCallback(this);

        setupSkips();
        setCoronaFields();

    }

    private void setCoronaFields() {

        if (!MainApp.selectedKishMWRA.isCoronaCase()) {
            //  bi.g102f.setVisibility(View.GONE);

            bi.g103j.setVisibility(View.GONE);
            bi.g103k.setVisibility(View.GONE);
            bi.g103l.setVisibility(View.GONE);
            bi.g103m.setVisibility(View.GONE);
            bi.g103n.setVisibility(View.GONE);
            bi.g103o.setVisibility(View.GONE);

            bi.fldGrpCVg1251.setVisibility(View.GONE);

        }
    }

    private void setupSkips() {

        //g102
        bi.g102.setOnCheckedChangeListener((group, checkedId) -> {
            Clear.clearAllFields(bi.fldGrpCVg103);
            Clear.clearAllFields(bi.fldGrpCVg105);
            Clear.clearAllFields(bi.fldGrpCVg106);
            Clear.clearAllFields(bi.fldGrpCVg107);
            bi.fldGrpCVg103.setVisibility(View.GONE);
            bi.fldGrpCVg105.setVisibility(View.GONE);
            bi.fldGrpCVg106.setVisibility(View.GONE);
            bi.fldGrpCVg107.setVisibility(View.GONE);

            if (checkedId == bi.g102a.getId()) {
                bi.fldGrpCVg103.setVisibility(View.VISIBLE);
            } else {
                bi.fldGrpCVg105.setVisibility(View.VISIBLE);
                bi.fldGrpCVg106.setVisibility(View.VISIBLE);
                bi.fldGrpCVg107.setVisibility(View.VISIBLE);
            }
        });


        //g110
        bi.g110.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == bi.g110a.getId()) {
                bi.fldGrpCVg111.setVisibility(View.VISIBLE);
                bi.fldGrpCVg112.setVisibility(View.VISIBLE);
            } else {
                Clear.clearAllFields(bi.fldGrpCVg111);
                Clear.clearAllFields(bi.fldGrpCVg112);
                bi.fldGrpCVg111.setVisibility(View.GONE);
                bi.fldGrpCVg112.setVisibility(View.GONE);
            }
        });


        //g111
        bi.g111.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == bi.g111a.getId()) {
                bi.fldGrpCVg112.setVisibility(View.VISIBLE);
            } else {
                Clear.clearAllFields(bi.fldGrpCVg112);
                bi.fldGrpCVg112.setVisibility(View.GONE);
            }
        });


        //g113
        bi.g113.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == bi.g113a.getId()) {
                bi.fldGrpCVg114.setVisibility(View.VISIBLE);
                bi.fldGrpCVg115.setVisibility(View.VISIBLE);
                bi.fldGrpCVg116.setVisibility(View.VISIBLE);
            } else {
                Clear.clearAllFields(bi.fldGrpCVg114);
                Clear.clearAllFields(bi.fldGrpCVg115);
                Clear.clearAllFields(bi.fldGrpCVg116);
                bi.fldGrpCVg114.setVisibility(View.GONE);
                bi.fldGrpCVg115.setVisibility(View.GONE);
                bi.fldGrpCVg116.setVisibility(View.GONE);
            }
        });


        //g115
        bi.g115.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == bi.g115a.getId()) {
                bi.fldGrpCVg116.setVisibility(View.VISIBLE);
            } else {
                Clear.clearAllFields(bi.fldGrpCVg116);
                bi.fldGrpCVg116.setVisibility(View.GONE);
            }
        });


        //g119
        bi.g119.setOnCheckedChangeListener((group, checkedId) -> {

          /*
          Clear.clearAllFields(bi.fldGrpCVg120);
            Clear.clearAllFields(bi.fldGrpCVg121);
            bi.fldGrpCVg120.setVisibility(View.GONE);
            bi.fldGrpCVg121.setVisibility(View.GONE);

          if (checkedId == bi.g119a.getId()) {
                bi.fldGrpCVg120.setVisibility(View.VISIBLE);
            } else {
                bi.fldGrpCVg121.setVisibility(View.VISIBLE);
            }
            */

            if (checkedId == bi.g119a.getId() || checkedId == bi.g119b.getId()) {
                Clear.clearAllFields(bi.fldGrpCVg121);
            } else if (checkedId == bi.g119c.getId()) {
                Clear.clearAllFields(bi.fldGrpCVg120);
            }
        });


        //g122
        bi.g122.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == bi.g122a.getId()) {
                bi.fldGrpCVg123.setVisibility(View.VISIBLE);
                bi.fldGrpCVg124.setVisibility(View.VISIBLE);
            } else {
                Clear.clearAllFields(bi.fldGrpCVg123);
                Clear.clearAllFields(bi.fldGrpCVg124);
                bi.fldGrpCVg123.setVisibility(View.GONE);
                bi.fldGrpCVg124.setVisibility(View.GONE);
            }
        });

        bi.g12401n.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Clear.Companion.clearAllFields(bi.g12401check, !isChecked);
        });

        //g1251
        bi.g1251c.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {

                Clear.clearAllFields(bi.g1251d);
                Clear.clearAllFields(bi.g1251e);
                Clear.clearAllFields(bi.g1251f);
                Clear.clearAllFields(bi.g1251g);

                bi.g1251d.setVisibility(View.GONE);
                bi.g1251e.setVisibility(View.GONE);
                bi.g1251f.setVisibility(View.GONE);
                bi.g1251g.setVisibility(View.GONE);

            } else {

                bi.g1251d.setVisibility(View.VISIBLE);
                bi.g1251e.setVisibility(View.VISIBLE);
                bi.g1251f.setVisibility(View.VISIBLE);
                bi.g1251g.setVisibility(View.VISIBLE);
            }
        });


        bi.g1251h.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Clear.Companion.clearAllFields(bi.g1251check, !isChecked);
        });


        //g125
        bi.g125.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == bi.g125b.getId()) {
                if (MainApp.selectedKishMWRA.isCoronaCase())
                    bi.fldGrpCVg1251.setVisibility(View.VISIBLE);
            } else {
                Clear.clearAllFields(bi.fldGrpCVg1251);
                bi.fldGrpCVg1251.setVisibility(View.GONE);
            }
        });


        //g126
        bi.g126.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == bi.g126a.getId()) {
                bi.fldGrpCVg127.setVisibility(View.VISIBLE);
            } else {
                Clear.clearAllFields(bi.fldGrpCVg127);
                bi.fldGrpCVg127.setVisibility(View.GONE);
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
                startActivity(new Intent(this, SectionH1Activity.class));
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
        int updcount = db.updatesKishMWRAColumn(KishMWRAContract.SingleKishMWRA.COLUMN_SG, MainApp.kish.getsG());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("g101", bi.g101a.isChecked() ? "1"
                : bi.g101b.isChecked() ? "2"
                : "-1");

        json.put("g102", bi.g102a.isChecked() ? "1"
                : bi.g102b.isChecked() ? "2"
                : bi.g102c.isChecked() ? "3"
                : bi.g102d.isChecked() ? "4"
                : bi.g102e.isChecked() ? "5"
                : bi.g102f.isChecked() ? "6"
                : bi.g102g.isChecked() ? "7"
                : bi.g102h.isChecked() ? "8"
                : bi.g102i.isChecked() ? "9"
                : bi.g102j.isChecked() ? "10"
                : bi.g102k.isChecked() ? "11"
                : bi.g102l.isChecked() ? "12"
                : "-1");

        json.put("g103a", bi.g103a.isChecked() ? "1" : "-1");
        json.put("g103b", bi.g103b.isChecked() ? "2" : "-1");
        json.put("g103c", bi.g103c.isChecked() ? "3" : "-1");
        json.put("g103d", bi.g103d.isChecked() ? "4" : "-1");
        json.put("g103e", bi.g103e.isChecked() ? "5" : "-1");
        json.put("g103f", bi.g103f.isChecked() ? "6" : "-1");
        json.put("g103g", bi.g103g.isChecked() ? "7" : "-1");
        json.put("g103h", bi.g103h.isChecked() ? "8" : "-1");
        json.put("g103i", bi.g103i.isChecked() ? "9" : "-1");
        json.put("g103j", bi.g103j.isChecked() ? "10" : "-1");
        json.put("g103k", bi.g103k.isChecked() ? "11" : "-1");
        json.put("g103l", bi.g103l.isChecked() ? "12" : "-1");
        json.put("g103m", bi.g103m.isChecked() ? "13" : "-1");
        json.put("g103n", bi.g103n.isChecked() ? "14" : "-1");
        json.put("g103o", bi.g103o.isChecked() ? "15" : "-1");

        json.put("g104", bi.g104a.isChecked() ? "1" :
                bi.g104b.isChecked() ? "2" :
                        bi.g104c.isChecked() ? "3" :
                                bi.g104d.isChecked() ? "4" :
                                        bi.g104e.isChecked() ? "5" :
                                                /*bi.g104f.isChecked() ? "6" :*/ "-1");

        json.put("g105", bi.g105.getText().toString().trim().isEmpty() ? "-1" : bi.g105.getText().toString());

        json.put("g106", bi.g106a.isChecked() ? "1" :
                bi.g106b.isChecked() ? "2" :
                        bi.g106c.isChecked() ? "3" :
                                bi.g106d.isChecked() ? "4" : "-1");

        json.put("g107", bi.g107.getText().toString().trim().isEmpty() ? "-1" : bi.g107.getText().toString());

        json.put("g108", bi.g108a.isChecked() ? "1" :
                bi.g108b.isChecked() ? "2" : "-1");

        json.put("g109", bi.g109a.isChecked() ? "1" :
                bi.g109b.isChecked() ? "2" :
                        bi.g109c.isChecked() ? "3" :
                                bi.g109d.isChecked() ? "4" : "-1");

        json.put("g110", bi.g110a.isChecked() ? "1" :
                bi.g110b.isChecked() ? "2" :
                        bi.g11098.isChecked() ? "98" : "-1");

        json.put("g111", bi.g111a.isChecked() ? "1" :
                bi.g111b.isChecked() ? "2" : "-1");

        json.put("g112", bi.g112.getText().toString().trim().isEmpty() ? "-1" : bi.g112.getText().toString());

        json.put("g113", bi.g113a.isChecked() ? "1" :
                bi.g113b.isChecked() ? "2" :
                        bi.g11398.isChecked() ? "98" : "-1");

        json.put("g114", bi.g114a.isChecked() ? "1" :
                bi.g114b.isChecked() ? "2" :
                        bi.g114c.isChecked() ? "3" :
                                bi.g114d.isChecked() ? "4" :
                                        bi.g114e.isChecked() ? "5" :
                                                bi.g114f.isChecked() ? "6" :
                                                        bi.g114g.isChecked() ? "7" :
                                                                bi.g114h.isChecked() ? "8" : "-1");

        json.put("g115", bi.g115a.isChecked() ? "1" :
                bi.g115b.isChecked() ? "2" : "-1");

        json.put("g116", bi.g116a.isChecked() ? "1" :
                bi.g116b.isChecked() ? "2" :
                        bi.g116c.isChecked() ? "3" :
                                bi.g116d.isChecked() ? "4" :
                                        bi.g116e.isChecked() ? "5" : "-1");

        json.put("g117a", bi.g117a.getText().toString().trim().isEmpty() ? "-1" : bi.g117a.getText().toString());
        json.put("g117b", bi.g117b.getText().toString().trim().isEmpty() ? "-1" : bi.g117b.getText().toString());
        json.put("g117c", bi.g117c.getText().toString().trim().isEmpty() ? "-1" : bi.g117c.getText().toString());
        /*json.put("g11798", bi.g11798.isChecked() ? "1" : "0");*/

        json.put("g11898", bi.g11898.isChecked() ? "98" : "-1");
        json.put("g118", bi.g118.getText().toString().trim().isEmpty() ? "-1" : bi.g118.getText().toString());

        json.put("g119", bi.g119a.isChecked() ? "1" :
                bi.g119b.isChecked() ? "2" :
                        bi.g119c.isChecked() ? "3" : "-1");

        json.put("g120", bi.g120a.isChecked() ? "1" :
                bi.g120b.isChecked() ? "2" :
                        bi.g120c.isChecked() ? "3" :
                                bi.g120d.isChecked() ? "4" :
                                        bi.g120e.isChecked() ? "5" :
                                                bi.g120f.isChecked() ? "6" :
                                                        bi.g120g.isChecked() ? "7" :
                                                                bi.g12096.isChecked() ? "96" : "-1");

        json.put("g12096x", bi.g12096x.getText().toString().trim().isEmpty() ? "-1" : bi.g12096x.getText().toString());

        json.put("g121", bi.g121a.isChecked() ? "1" :
                bi.g121b.isChecked() ? "2" :
                        bi.g121c.isChecked() ? "3" :
                                bi.g121d.isChecked() ? "4" :
                                        bi.g121e.isChecked() ? "5" :
                                                bi.g121f.isChecked() ? "6" :
                                                        bi.g121g.isChecked() ? "7" :
                                                                bi.g121h.isChecked() ? "8" :
                                                                        bi.g12196.isChecked() ? "96" : "-1");

        json.put("g12196x", bi.g12196x.getText().toString().trim().isEmpty() ? "-1" : bi.g12196x.getText().toString());

        json.put("g122", bi.g122a.isChecked() ? "1" :
                bi.g122b.isChecked() ? "2" : "-1");

        json.put("g123a", bi.g123a.isChecked() ? "1" : "-1");
        json.put("g123b", bi.g123b.isChecked() ? "2" : "-1");
        json.put("g123c", bi.g123c.isChecked() ? "3" : "-1");
        json.put("g123d", bi.g123d.isChecked() ? "4" : "-1");
        json.put("g123e", bi.g123e.isChecked() ? "5" : "-1");
        json.put("g123f", bi.g123f.isChecked() ? "6" : "-1");
        json.put("g123g", bi.g123g.isChecked() ? "7" : "-1");

        json.put("g124a", bi.g124a.isChecked() ? "1" : "-1");
        json.put("g124b", bi.g124b.isChecked() ? "2" : "-1");
        json.put("g124c", bi.g124c.isChecked() ? "3" : "-1");
        json.put("g124d", bi.g124d.isChecked() ? "4" : "-1");
        json.put("g124e", bi.g124e.isChecked() ? "5" : "-1");
        json.put("g124f", bi.g124f.isChecked() ? "6" : "-1");
        json.put("g124g", bi.g124g.isChecked() ? "7" : "-1");
        json.put("g124h", bi.g124h.isChecked() ? "8" : "-1");
        json.put("g124i", bi.g124i.isChecked() ? "9" : "-1");
        json.put("g124j", bi.g124j.isChecked() ? "10" : "-1");
        json.put("g124k", bi.g124k.isChecked() ? "11" : "-1");
        json.put("g124l", bi.g124l.isChecked() ? "12" : "-1");
        json.put("g124m", bi.g124m.isChecked() ? "13" : "-1");

        json.put("g12401a", bi.g12401a.isChecked() ? "1" : "-1");
        json.put("g12401b", bi.g12401b.isChecked() ? "2" : "-1");
        json.put("g12401c", bi.g12401c.isChecked() ? "3" : "-1");
        json.put("g12401d", bi.g12401d.isChecked() ? "4" : "-1");
        json.put("g12401e", bi.g12401e.isChecked() ? "5" : "-1");
        json.put("g12401f", bi.g12401f.isChecked() ? "6" : "-1");
        json.put("g12401g", bi.g12401g.isChecked() ? "7" : "-1");
        json.put("g12401h", bi.g12401h.isChecked() ? "8" : "-1");
        json.put("g12401i", bi.g12401i.isChecked() ? "9" : "-1");
        json.put("g12401j", bi.g12401j.isChecked() ? "10" : "-1");
        json.put("g12401k", bi.g12401k.isChecked() ? "11" : "-1");
        json.put("g12401l", bi.g12401l.isChecked() ? "12" : "-1");
        json.put("g12401m", bi.g12401m.isChecked() ? "13" : "-1");
        json.put("g12401n", bi.g12401n.isChecked() ? "14" : "-1");

        json.put("g125", bi.g125a.isChecked() ? "1"
                : bi.g125b.isChecked() ? "2"
                : "-1");

        json.put("g12501a", bi.g1251a.isChecked() ? "1" : "-1");
        json.put("g12501b", bi.g1251b.isChecked() ? "2" : "-1");
        json.put("g12501c", bi.g1251c.isChecked() ? "3" : "-1");
        json.put("g12501d", bi.g1251d.isChecked() ? "4" : "-1");
        json.put("g12501e", bi.g1251e.isChecked() ? "5" : "-1");
        json.put("g12501f", bi.g1251f.isChecked() ? "6" : "-1");
        json.put("g12501g", bi.g1251g.isChecked() ? "7" : "-1");
        json.put("g12501h", bi.g1251h.isChecked() ? "8" : "-1");

        json.put("g126", bi.g126a.isChecked() ? "1" :
                bi.g126b.isChecked() ? "2" :
                        bi.g12697.isChecked() ? "97" : "-1");

        /*json.put("g127", bi.g127a.isChecked() ? "1" :
                bi.g127b.isChecked() ? "2" : "-1");

        json.put("g128", bi.g128a.isChecked() ? "1" :
                bi.g128b.isChecked() ? "2" : "-1");

        json.put("g129", bi.g129a.isChecked() ? "1" :
                bi.g129b.isChecked() ? "2" : "-1");*/


        json.put("g127", bi.g127a.isChecked() ? "1" :
                bi.g127b.isChecked() ? "2" :
                        bi.g127c.isChecked() ? "3" : "-1");

        json.put("g128", bi.g128a.isChecked() ? "1" :
                bi.g128b.isChecked() ? "2" :
                        bi.g128c.isChecked() ? "3" : "-1");

        json.put("g129", bi.g129a.isChecked() ? "1" :
                bi.g129c.isChecked() ? "2" :
                        bi.g129c.isChecked() ? "3" : "-1");

        MainApp.kish.setsG(String.valueOf(json));

        MainApp.G102 = bi.g102a.isChecked() ? "1" : "-1";

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }


}
