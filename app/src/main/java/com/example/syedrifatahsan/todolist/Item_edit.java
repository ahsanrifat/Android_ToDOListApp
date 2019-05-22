package com.example.syedrifatahsan.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Item_edit extends AppCompatActivity {

    String oldItem;


    int position;

    EditText editItem;
    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        editItem=findViewById(R.id.editItemID);
        btn3=findViewById(R.id.btn3ID);

        Intent intent =getIntent(); //gotta see which intent has been passed

        oldItem=intent.getStringExtra(Inent_Constants.INTENT_ITEM_EDIT_ON_CLICK).toString();

        position=intent.getIntExtra(Inent_Constants.INTENT_SAVE_POSITION_TO_PUT_BACK, -1);

        editItem.setText(oldItem);

    }

    public void snedEditedItemOnClick(View v){

        String newItem= editItem.getText().toString();

        if (!newItem.equals("")) {

            Intent intent = new Intent();

            intent.putExtra(Inent_Constants.INTENT_ITEM_EDIT_SAVE_ON_CLICK, newItem);

            intent.putExtra(Inent_Constants.INTENT_SAVE_POSITION_TO_PUT_BACK, position);

            setResult(Inent_Constants.INTENT_EDIT_RESULT_CODE, intent);

            finish();

            // no need to sent intent as we asked for result while sending old data to be edited
        }
        else {
            Toast.makeText(this, "Blank Item Can Not Be Saved.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent();
        intent.setClass(Item_edit.this,MainActivity.class);
        startActivity(intent);
    }
}
