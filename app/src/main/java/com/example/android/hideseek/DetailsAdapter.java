package com.example.android.hideseek;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/*
Creating custom adapter
 */
public class DetailsAdapter extends ArrayAdapter<Details> {
    private Context context;
    private List<Details> detailsList;

    public DetailsAdapter(Activity context, List<Details> detailsList) {
        super(context, 0, detailsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, null, false);
        }
        final Details currentDetails = getItem(position);

        TextView titleTextView = listViewItem.findViewById(R.id.title_text_view);
        String title = currentDetails.getLostFound() + " " + currentDetails.getmObjectType();
        titleTextView.setText(title);

        TextView authorTextView = listViewItem.findViewById(R.id.author_text_view);
        String author = "By " + currentDetails.getmName() + ", " + currentDetails.getmContactNumber();
        authorTextView.setText(author);

        TextView lfTextView = listViewItem.findViewById(R.id.lf_text_view);

        if (currentDetails.getLostFound().equals("Lost"))
            lfTextView.setText("L");
        else if (currentDetails.getLostFound().equals("Found"))
            lfTextView.setText("F");

        return listViewItem;
    }
}
