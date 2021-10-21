package com.nguyenloi.shop_ecommerce.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nguyenloi.shop_ecommerce.Notification;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.TheCart;

public class NotificationAdapter extends ArrayAdapter<Notification> {
    Activity context;
    int resource;
    TextView tvNotificationTitle,tvNotificationDescription;
    ImageView imgNotification;
    public NotificationAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView= inflater.inflate(this.resource,null);
        imgNotification=customView.findViewById(R.id.imgNotification);
        tvNotificationTitle=customView.findViewById(R.id.tvNotificationTitle);
        tvNotificationDescription=customView.findViewById(R.id.tvNotificationDescription);
        Notification notification = getItem(position);
        tvNotificationTitle.setText(notification.getTitleNotification());
        tvNotificationDescription.setText(notification.getDescriptionNotification());
        imgNotification.setImageResource(notification.getImageNotification());
        return customView;
    }
}
