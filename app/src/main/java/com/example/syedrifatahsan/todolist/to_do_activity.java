package com.example.syedrifatahsan.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class to_do_activity extends AppCompatActivity {

    Button btn2;
    EditText newItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layout);

        newItem=findViewById(R.id.newItemID);
        btn2=findViewById(R.id.btn2ID);
    }
    public void saveButtonClicked(View v){
        String newItemInput= newItem.getText().toString();
        if(newItemInput.equals("")){
            Toast.makeText(this, "Empty! Type Something!", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent= new Intent();
           intent.putExtra(Inent_Constants.INTENT_MESSAGE_FIELD,newItemInput); //actual item
           setResult(Inent_Constants.INTENT_RESULT_CODE,intent); //security check
           finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent();
        intent.setClass(to_do_activity.this,MainActivity.class);
        startActivity(intent);
    }
}
