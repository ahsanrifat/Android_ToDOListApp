package com.example.syedrifatahsan.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {


    ListView listView;
    Button  btn;
    TextView title;

    ArrayList<String> aListItem;
    ArrayAdapter<String> aListAdapter;

    String newItemToAdd;

    String newEditedItem;
    int rePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.btn1ID);
        listView=findViewById(R.id.listViewID);
        title=findViewById(R.id.titleID);

        aListItem = new ArrayList<>();
        aListAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,aListItem);
        listView.setAdapter(aListAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        try {
            Scanner sc =new Scanner(openFileInput("todo.txt"));
            while(sc.hasNext()){
                String prevItem=sc.nextLine();
                aListItem.add(prevItem);
            }
            aListAdapter.notifyDataSetChanged();
            sc.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

    }

    public void btn1Clicked(View v){
        Intent intent=new Intent();
        intent.setClass(MainActivity.this,to_do_activity.class);
        startActivityForResult(intent,Inent_Constants.INTENT_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        request code has to match with result code
        if (requestCode==Inent_Constants.INTENT_RESULT_CODE){ //this ensures to_do activity is sending the data
            newItemToAdd=data.getStringExtra(Inent_Constants.INTENT_MESSAGE_FIELD); //rechecking with key value
            aListItem.add(newItemToAdd);
            aListAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Item Added Successfully!", Toast.LENGTH_SHORT).show();
        }

        if(requestCode==Inent_Constants.INTENT_EDIT_RESULT_CODE){

            newEditedItem=data.getStringExtra(Inent_Constants.INTENT_ITEM_EDIT_SAVE_ON_CLICK);
            rePosition=data.getIntExtra(Inent_Constants.INTENT_SAVE_POSITION_TO_PUT_BACK,-1);
            aListItem.remove(rePosition);
            aListItem.add(rePosition,newEditedItem);
            aListAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Item Updated Successfully!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        aListItem.remove(position);
        aListAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Item Deleted Successfully!", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent =new Intent();

        intent.setClass(MainActivity.this,Item_edit.class);

        intent.putExtra(Inent_Constants.INTENT_ITEM_EDIT_ON_CLICK,aListItem.get(position).toString());

        intent.putExtra(Inent_Constants.INTENT_SAVE_POSITION_TO_PUT_BACK,position);

        startActivityForResult(intent,Inent_Constants.INTENT_EDIT_REQUEST_CODE);

//        the activity to which im sending my data must pass me a result with result code

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        PrintWriter pw = null;
        try {
            pw = new PrintWriter(openFileOutput("todo.txt",Context.MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            title.setText("File Exception");
            e.printStackTrace();
        }
        for(String itemsToBeSaved : aListItem){

            pw.println(itemsToBeSaved);
        }
        pw.close();
        finish();
    }

}
