package com.fta.greendaotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;

import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class RxDaoActivity extends AppCompatActivity {

    private View addNoteButton;
    private NotesAdapter notesAdapter;
    private EditText editText;
    private RxDao<Note, Long> noteDao;
    private RxQuery<Note> notesQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao);

        setUpViews();

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        noteDao = daoSession.getNoteDao().rx();

        notesQuery = daoSession.getNoteDao().queryBuilder().orderAsc(NoteDao.Properties.Text).rx();

        updateNotes();
    }

    private void updateNotes() {
        notesQuery.list().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Note>>() {
                               @Override
                               public void call(List<Note> notes) {
                                   notesAdapter.setNotes(notes);
                               }
                           }

                );
    }

    public void onAddButtonClick(View view) {
        addNote();
    }

    private void setUpViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotes);
        //noinspection ConstantConditions
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter(noteClickListener);
        recyclerView.setAdapter(notesAdapter);

        addNoteButton = findViewById(R.id.buttonAdd);
        //noinspection ConstantConditions
        addNoteButton.setEnabled(false);

        editText = (EditText) findViewById(R.id.editTextNote);
        RxTextView.editorActions(editText).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer actionId) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            addNote();
                        }
                    }
                });
        RxTextView.afterTextChangeEvents(editText).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        boolean enable = textViewAfterTextChangeEvent.editable().length() > 0;
                        addNoteButton.setEnabled(enable);
                    }
                });

    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");
        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());
        Note note = new Note();
        note.setText(noteText);
        note.setComment(comment);
        note.setDate(new Date());
        note.setType(NoteType.TEXT);
       noteDao.insert(note)
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Action1<Note>() {
                   @Override
                   public void call(Note note) {
                       Log.d("DaoExample", "Inserted new note, ID: " + note.getId());
                       updateNotes();
                   }
               });
    }

    NotesAdapter.NoteClickListener noteClickListener = new NotesAdapter.NoteClickListener() {
        @Override
        public void onNoteClick(int position) {
            Note note = notesAdapter.getNote(position);
            final long noteId = note.getId();

            noteDao.deleteByKey(noteId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            Log.d("DaoExample", "Deleted note, ID: " + noteId);
                            updateNotes();
                        }
                    });

        }
    };
}
