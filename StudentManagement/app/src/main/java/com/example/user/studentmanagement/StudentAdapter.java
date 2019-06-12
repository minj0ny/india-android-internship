package com.example.user.studentmanagement;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context mCtx;
    private List<Student> stukList;

    public StudentAdapter(Context mCtx, List<Student> stukList) {
        this.mCtx = mCtx;
        this.stukList = stukList;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.layout_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student t = stukList.get(position);
        holder.stuName.setText(t.getStuName());
        holder.stuClass.setText(t.getStuClass());
        holder.stuMark.setText(String.valueOf(t.getStuMark()));
    }

    @Override
    public int getItemCount() {
        return stukList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView stuName, stuClass, stuMark;

        public StudentViewHolder(View itemView) {
            super(itemView);

            stuName = itemView.findViewById(R.id.textViewTitle);
            stuClass = itemView.findViewById(R.id.textViewShortDesc);
            stuMark = itemView.findViewById(R.id.textViewRating);

        }

    }
}

