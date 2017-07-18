package com.ssa.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ssa.R;
import com.ssa.adapters.ChatAdapter;
import com.ssa.models.KidModel;
import com.ssa.models.Message;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private DatabaseReference mDatabase, mChildRef, mKidRef, mMessagesRef;
    private KidModel.Kids kid;
    private RecyclerView rvChat;
    private ChatAdapter chatAdapter;
    private ArrayList<Message> arrMessages = new ArrayList<>();
    private EditText etMessage;
    private TextView tvSend;
    private ScrollView svChat;
    private LinearLayout llChat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        if (getArguments().containsKey("Kid"))
            kid = (KidModel.Kids) getArguments().getSerializable("Kid");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvChat = (RecyclerView) view.findViewById(R.id.rv_chat);
        etMessage = (EditText) view.findViewById(R.id.et_message);
        tvSend = (TextView) view.findViewById(R.id.tv_send);
        llChat = (LinearLayout) view.findViewById(R.id.ll_chat);
        svChat = (ScrollView) view.findViewById(R.id.sv_chat);

        /*KidModel kidModel = new KidModel();
        kid = kidModel.new Kids();
        kid.setFirstName("laxmankid2 H");
        kid.setKidId("406054059");*/
        mDatabase = FirebaseDatabase.getInstance().getReference();

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message messageModel = new Message();
                messageModel.setSenderId(kid.getKidId());
                messageModel.setSenderName(kid.getFirstName());
                messageModel.setText(etMessage.getText().toString());
                messageModel.setStatus("sent");
                mMessagesRef.push().setValue(messageModel);
                etMessage.setText("");
            }
        });

        mChildRef = mDatabase.child("Kids");

        if (mChildRef != null) {

            mKidRef = mChildRef.child(kid.getKidId());
            if (mKidRef != null) {
                mMessagesRef = mKidRef.child("messages");
                if (mMessagesRef != null) {

                    mMessagesRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Message messageModel = dataSnapshot.getValue(Message.class);
                            if (messageModel != null) {
                                arrMessages.add(0, messageModel);
                                if (messageModel.getSenderId().equals(kid.getKidId())) {
                                    createMessageView(messageModel.getText(), 1);
                                } else {
                                    createMessageView(messageModel.getText(), 0);
                                }
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    /*mMessagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() instanceof HashMap) {
                                HashMap<Object, Object> hashMap = (HashMap<Object, Object>) dataSnapshot.getValue();
                                if (hashMap != null) {
                                    Iterator<Object> keySet = hashMap.keySet().iterator();
                                    while (keySet.hasNext()) {
                                        Object key = keySet.next();
                                        HashMap<String, String> data = (HashMap<String, String>) hashMap.get(key);
                                        if (data != null) {
                                            String text = data.get("text");
                                            String senderName = data.get("senderName");
                                            String senderId = data.get("senderId");
                                            String status = data.get("status");

                                            Message messageModel = new Message();
                                            messageModel.setText(text);
                                            messageModel.setSenderName(senderName);
                                            messageModel.setSenderId(senderId);
                                            messageModel.setStatus(status);

                                            arrMessages.add(0, messageModel);
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/

                    /*if (arrMessages != null) {
                        chatAdapter = new ChatAdapter(getActivity(), arrMessages, kid.getKidId());
                        rvChat.setAdapter(chatAdapter);
                        rvChat.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }*/
                }
            }
        }
    }

    private void createMessageView(String message, int type) {
        if (getActivity() != null) {
            TextView textView = new TextView(getActivity());
            textView.setText(message);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if (type == 1) {
                lp.gravity = Gravity.RIGHT;
                textView.setBackgroundResource(R.drawable.bg_incoming_message);
                lp.setMargins(40, 20, 10, 10);
            } else {
                lp.setMargins(10, 20, 40, 10);
                lp.gravity = Gravity.LEFT;
                textView.setBackgroundResource(R.drawable.bg_incoming_message);
            }
            textView.setPadding(20, 20, 20, 20);
            textView.setLayoutParams(lp);

            llChat.addView(textView);
            svChat.post(new Runnable() {
                @Override

                public void run() {

                    svChat.scrollTo(0, svChat.getBottom());

                }
            });
        }
    }
}
