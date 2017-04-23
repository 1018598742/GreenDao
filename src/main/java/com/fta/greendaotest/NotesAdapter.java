package com.fta.greendaotest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：
 * 作者： Created by fta on 2017/4/23.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, clickListener);
    }


    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.text.setText(note.getText());
        holder.comment.setText(note.getComment());
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }


    static class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView comment;

        public NoteViewHolder(View itemView, final NoteClickListener noteClickListener) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.textViewNoteText);
            comment = (TextView) itemView.findViewById(R.id.textViewNoteComment);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (noteClickListener != null) {
                        noteClickListener.onNoteClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    private NoteClickListener clickListener;
    private List<Note> notes;

    public interface NoteClickListener {
        void onNoteClick(int position);
    }

    public NotesAdapter(NoteClickListener noteClickListener) {
        this.clickListener = noteClickListener;
        this.notes = new ArrayList<>();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNote(int position) {
        return notes.get(position);
    }


}
