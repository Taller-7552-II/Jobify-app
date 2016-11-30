package com.fiuba.tallerii.jobify;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NotificationsFragment extends Fragment
{
    private RecyclerView mNotificationsRecycleView;
    private NotificationAdapter mNotificationsAdapter;

    private static final String ADDFRIEND_RESPONSE = "Request answered";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        mNotificationsRecycleView = (RecyclerView) v.findViewById(R.id.recycler_view_contacts);
        mNotificationsRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    private void updateUI()
    {
        InformationHolder informationHolder = InformationHolder.get();
        List<Notification> notifications = informationHolder.getNotifications();

        mNotificationsAdapter = new NotificationAdapter(notifications);
        mNotificationsRecycleView.setAdapter(mNotificationsAdapter);
    }

    private void createAddFriendDialog(final String friendUsername, final int notificationIndex)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Confirm friend");
        builder.setMessage(friendUsername + " wants to add you");

        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which)
            {
                AnswerNotificationTask answerTask = new AnswerNotificationTask(friendUsername, true);
                answerTask.execute();
                InformationHolder.get().removeNotification(notificationIndex);

                //TODO add Contact
                updateUI();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Reject", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                AnswerNotificationTask answerTask = new AnswerNotificationTask(friendUsername, false);
                answerTask.execute();
                InformationHolder.get().removeNotification(notificationIndex);
                updateUI();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /*
        ViewHolder Class
     */
    private class NotificationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView mCardView;
        public TextView mNotificationTittle;
        public TextView mNotificationDescription;

        public NotificationsViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);

            mCardView = (CardView) itemView.findViewById(R.id.card_view_notification);
            mNotificationTittle = (TextView) itemView.findViewById(R.id.list_item_notification_tittle);
            mNotificationDescription = (TextView) itemView.findViewById(R.id.list_item_notification_description);
        }

        @Override
        public void onClick(View view)
        {
            int index = getAdapterPosition();
            Log.d("Jobify", "Clicked Notification " + index);
            Notification notification = InformationHolder.get().getNotification(index);
            if (notification.getCode() == Notification.NOTIFICATION_CODE_ADDFRIEND)
            {
                Log.d("Jobify", "Clicked Notification " + index);
                createAddFriendDialog(notification.getContent(), index);
            }
        }
    }


    /*
        Adapter Class
     */
    private class NotificationAdapter extends RecyclerView.Adapter<NotificationsViewHolder>
    {
        private List<Notification> mNotifications;

        public NotificationAdapter(List<Notification> notifications)
        {
            mNotifications = notifications;
        }

        @Override
        public NotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_card_notification, parent, false);
            return new NotificationsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NotificationsViewHolder holder, int position)
        {
            Notification notification = mNotifications.get(position);
            holder.mNotificationTittle.setText(notification.getTitle());
            holder.mNotificationDescription.setText(notification.getContent());
        }

        @Override
        public int getItemCount()
        {
            return mNotifications.size();
        }
    }

    /**
     * Represents an asynchronous Answer Notification task used to authenticate
     * the user.
     */
    public class AnswerNotificationTask extends AsyncTask<Void, Void, String>
    {
        private final String mContactUsername;
        private final boolean mContactAccepted;

        AnswerNotificationTask(String contactUsername, boolean accepted)
        {
            mContactUsername = contactUsername;
            mContactAccepted = accepted;
        }

        @Override
        protected String doInBackground(Void... params)
        {

            String urlSpec = "http://" + ServerHandler.get(getActivity()).getServerIP() + "/users/" + ServerHandler.get(getActivity()).getUsername() + "/acceptFriend/" + mContactUsername;
            String loginParams = "";
            try
            {
                JSONObject jsonLoginParams= new JSONObject();
                String requestResponse = mContactAccepted? "yes": "no";
                jsonLoginParams.put("response", requestResponse);
                loginParams = jsonLoginParams.toString();
            }
            catch(JSONException e)
            {
                Log.e("Jobify", "Error creating accept friend Json File");
            }

            return ServerHandler.get(getActivity()).POST(urlSpec, loginParams);
        }

        @Override
        protected void onPostExecute(final String response)
        {
            Log.d("Jobify", "Accept Friend response: " + response);
            String toastMessage = "";

            try
            {
                JSONObject acceptFriendResponse = new JSONObject(response);
                String status = acceptFriendResponse.getString("status");
                if (status.equals(ADDFRIEND_RESPONSE))
                {
                    toastMessage = mContactUsername + " added to your contacts";
                    Log.i("Jobify", "Successfully added: " + mContactUsername);
                }

            }
            catch (JSONException e)
            {
                toastMessage = mContactUsername + " could not be added";
                Log.e("Jobify", "Error parsing accept friend response " + e.getMessage());
            }

            Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();

        }


        @Override
        protected void onCancelled()
        {
        }
    }


    // Request Contact Data Task Class
   /* public class RequestContactDataTask extends AsyncTask<Void, Void, String>
    {
        private String mFiendUsername;

        public RequestContactDataTask(String friendUsername)
        {
            mFiendUsername = friendUsername;
        }

    }*/
}
