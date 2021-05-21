package ru.mirea.linnik.mireaproject.ui.stories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.mirea.linnik.mireaproject.R;

public class AdapterCell extends RecyclerView.Adapter<ru.mirea.linnik.mireaproject.ui.stories.AdapterCell.StoryHolder>{
    List<ru.mirea.linnik.mireaproject.ui.stories.Cell> stories;

    public AdapterCell(List<ru.mirea.linnik.mireaproject.ui.stories.Cell> stories){
        this.stories = stories;
    }

    @Override
    public StoryHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.cell_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);
        StoryHolder storyHolder = new StoryHolder(view);
        return storyHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StoryHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    class StoryHolder extends RecyclerView.ViewHolder{
        TextView storyId;
        TextView storyText;

        public StoryHolder(View itemView) {
            super(itemView);

            storyId = itemView.findViewById(R.id.zam_id);
            storyText = itemView.findViewById(R.id.zam_text);
        }

        void bind(int listIndex){
            ru.mirea.linnik.mireaproject.ui.stories.Cell cell = stories.get(listIndex);
            storyId.setText(String.valueOf(cell.id));
            storyText.setText(cell.text);
        }
    }
}
