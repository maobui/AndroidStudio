package com.example.maobuidinh.realm.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maobuidinh.realm.R;
import com.example.maobuidinh.realm.helper.PrefManager;
import com.example.maobuidinh.realm.model.Book;
import com.example.maobuidinh.realm.realm.RealmController;

/**
 * Created by mao.buidinh on 7/17/2017.
 */

public class BookAdapter extends RealmRecyclerViewAdapter<Book> {

    final Context context;
    private RealmController mRealmController;
    private LayoutInflater inflater;

    public BookAdapter(Context context) {

        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        mRealmController = RealmController.getInstance();

        // get the article
        final Book book = getItem(position);
        // cast the generic view holder to our specific one
        final CardViewHolder viewHolder = (CardViewHolder) holder;

        // set the title and the snippet
        viewHolder.textTitle.setText(book.getTitle());
        viewHolder.textAuthor.setText(book.getAuthor());
        viewHolder.textDescription.setText(book.getDescription());

        // load the background image
        if (book.getImageUrl() != null) {
            Glide.with(context)
                    .load(book.getImageUrl().replace("https", "http"))
                    .asBitmap()
                    .fitCenter()
                    .into(viewHolder.imageBackground);
        }

        //remove single match from realm
        viewHolder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Get the book title to show it in toast message
                Book b = mRealmController.getBookLocation(position);
                String title = b.getTitle();

               // remove single match
                mRealmController.removeBookLocation(position);

                if (!mRealmController.hasBooks()) {
                    PrefManager.with(context).setPreLoad(false);
                }

                notifyDataSetChanged();

                Toast.makeText(context, title + " is removed from Realm", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //update single match from realm
        viewHolder.card.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.edit_item, null);
                final EditText editTitle = (EditText) content.findViewById(R.id.title);
                final EditText editAuthor = (EditText) content.findViewById(R.id.author);
                final EditText editThumbnail = (EditText) content.findViewById(R.id.thumbnail);

                editTitle.setText(book.getTitle());
                editAuthor.setText(book.getAuthor());
                editThumbnail.setText(book.getImageUrl());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content)
                        .setTitle("Edit Book")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mRealmController.updateBookLocation(position,editAuthor.getText().toString(),editTitle.getText().toString(), editThumbnail.getText().toString() );

                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }



    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView textTitle;
        public TextView textAuthor;
        public TextView textDescription;
        public ImageView imageBackground;

        public CardViewHolder( View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_book);
            textTitle = (TextView) itemView.findViewById(R.id.text_books_title);
            textAuthor = (TextView) itemView.findViewById(R.id.text_books_author);
            textDescription = (TextView) itemView.findViewById(R.id.text_books_description);
            imageBackground = (ImageView) itemView.findViewById(R.id.image_background);
        }
    }
}
