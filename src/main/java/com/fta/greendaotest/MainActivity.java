package com.fta.greendaotest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.greenrobot.greendao.query.Query;

public class MainActivity extends AppCompatActivity {

    private View addNoteButton;
    private NotesAdapter notesAdapter;
    private EditText editText;
    private NoteDao noteDao;
    private Query<Note> notesQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void jumpto(View view){
        switch (view.getId()){
            case R.id.simple_greendao:
                Intent intent = new Intent(this,SimpleDaoActivity.class);
                startActivity(intent);
                break;
            case R.id.rx_greendao:
                Intent intent1 = new Intent(this,SimpleDaoActivity.class);
                startActivity(intent1);
                break;
            default:
        }
    }
}
