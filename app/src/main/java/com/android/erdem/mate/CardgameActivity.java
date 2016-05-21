package com.android.erdem.mate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.erdem.mate.adapter.RecyclerViewAdapter;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardgameActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageView arrow;
    private TextView question;
    // private ImageView avatar;

    private static final int TIME_TO_AUTOMATICALLY_DISMISS_ITEM = 7000;

    private String getEntry(JSONArray arr, int index) {
        try {
            return arr.getString(ProfileInfo.questionnr).replaceAll("(\\r|\\n)", "");
        } catch (JSONException e) {
            e.printStackTrace();
            return "Error while extracting JSON.";
        }
    }

    private int getAnswerID(int questionNr, int answerPosition) {
        JSONArray selected;
        switch (answerPosition) {
            case 0:
                selected = ProfileInfo.answers1ID;
                break;
            case 1:
                selected = ProfileInfo.answers2ID;
                break;
            case 2:
                selected = ProfileInfo.answers3ID;
                break;
            case 3:
                selected = ProfileInfo.answers4ID;
                break;
            default:
                return 0;
        }
        try {
            return selected.getInt(questionNr);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardgame);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(3));
        init(mRecyclerView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(null);
        actionBar.setSubtitle(null);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.mate_logo_white);


        //  avatar = (ImageView)findViewById(R.id.avatar);
        arrow = (ImageView) findViewById(R.id.cardgame_arrow);

        question = (TextView) findViewById(R.id.question);

        question.setText(this.getEntry(ProfileInfo.questions, ProfileInfo.questionnr));

/*
        AlertDialog.Builder builder = new AlertDialog.Builder(CardgameActivity.this);
        String message;
        if (ProfileInfo.isQuestioner)
            message = "Congratulations! You're questioner";
        else
            message = "You're answerer";

        builder.setMessage(message)
                .setNegativeButton("Continue", null)
                .create()
                .show();
*/
    }


    //required for list spacing
    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mVerticalSpaceHeight;

        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }


    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{android.R.attr.listDivider};

        private Drawable mDivider;

        /**
         * Default divider will be used
         */
        public DividerItemDecoration(Context context) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
            mDivider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();
        }

        /**
         * Custom divider will be used
         */
        public DividerItemDecoration(Context context, int resId) {
            mDivider = ContextCompat.getDrawable(context, resId);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }


    private void init(final RecyclerView recyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        final MyBaseAdapter adapter = new MyBaseAdapter();

        ProfileInfo.selectedAnswersID = new JSONArray();

        //question.setText(ProfileInfo.questions.getString(ProfileInfo.questionnr));
        adapter.mDataSet.add(0, this.getEntry(ProfileInfo.answers1, ProfileInfo.questionnr));
        adapter.mDataSet.add(1, this.getEntry(ProfileInfo.answers2, ProfileInfo.questionnr));
        adapter.mDataSet.add(2, this.getEntry(ProfileInfo.answers3, ProfileInfo.questionnr));
        adapter.mDataSet.add(3, this.getEntry(ProfileInfo.answers4, ProfileInfo.questionnr));
        recyclerView.setAdapter(adapter);

        recyclerView.setAdapter(adapter);
        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new RecyclerViewAdapter(recyclerView),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onPendingDismiss(RecyclerViewAdapter recyclerView, int position) {

                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, int position) {
                                //adapter.remove(position);
                                //Add new question/answer information here !!!
                                AlertDialog.Builder builder = new AlertDialog.Builder(CardgameActivity.this);

                                ProfileInfo.selectedAnswersID.put(getAnswerID(ProfileInfo.questionnr, position));
                                /*try {
                                    builder.setMessage("SELECTED ANSWER: "+ ProfileInfo.selectedAnswersID.getInt(ProfileInfo.questionnr))
                                            .setNegativeButton("Continue", null)
                                            .create()
                                            .show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/

                                ProfileInfo.questionnr++;
                                if (ProfileInfo.questionnr >= 5) {
                                    // Response received from the server
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");

                                                if (success) {

                                                    Intent intent = new Intent(CardgameActivity.this, WaitActivity.class);
                                                    CardgameActivity.this.startActivity(intent);
                                                } else {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(CardgameActivity.this);
                                                    builder.setMessage("Fatal error. Please contact developer.")
                                                            .setNegativeButton("Okay", null)
                                                            .create()
                                                            .show();
                                                }

                                            } catch (JSONException e) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(CardgameActivity.this);
                                                builder.setMessage("Server Connection Failed. Have another try or contact developer.")
                                                        .setNegativeButton("Okay", null)
                                                        .create()
                                                        .show();
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                                    SubmitAnswersRequest submitAnswersRequest = new SubmitAnswersRequest(ProfileInfo.quizID, ProfileInfo.id, ProfileInfo.questionsID, ProfileInfo.selectedAnswersID, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(CardgameActivity.this);
                                    queue.add(submitAnswersRequest);


                                }
                                adapter.mDataSet.clear();

                                //included - change
                                String q =(String) getEntry(ProfileInfo.questions, ProfileInfo.questionnr);
                                q =q.replace(" - ", " _____ ");
                                question.setText(q);
                                recyclerView.invalidate();
                                Log.i("sdf",q);  //used for test
                                //question.setText(getEntry(ProfileInfo.questions, ProfileInfo.questionnr));
                                adapter.mDataSet.add(0, getEntry(ProfileInfo.answers1, ProfileInfo.questionnr));
                                adapter.mDataSet.add(1, getEntry(ProfileInfo.answers2, ProfileInfo.questionnr));
                                adapter.mDataSet.add(2, getEntry(ProfileInfo.answers3, ProfileInfo.questionnr));
                                adapter.mDataSet.add(3, getEntry(ProfileInfo.answers4, ProfileInfo.questionnr));
                                recyclerView.setAdapter(adapter);
                            }
                        });
        touchListener.setDismissDelay(TIME_TO_AUTOMATICALLY_DISMISS_ITEM);
        recyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        recyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(this,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.txt_selected) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        } else { // R.id.txt_data
                            //    Toast.makeText(CardgameActivity.this, "Example " + position, LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    static class MyBaseAdapter extends RecyclerView.Adapter<MyBaseAdapter.MyViewHolder> {

        private static final int SIZE = 4;

        private final List<String> mDataSet = new ArrayList<>();

        MyBaseAdapter() {
            /*for (int i = 0; i < SIZE; i++)
                mDataSet.add(i, "Answer " + i);*/
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_select, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.dataTextView.setText(mDataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        public void remove(int position) {
            mDataSet.remove(position);
            notifyItemRemoved(position);
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView dataTextView;

            MyViewHolder(View view) {
                super(view);
                dataTextView = (TextView) view.findViewById(R.id.txt_data);
            }
        }
    }
}
