package com.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.pm.PackageInfoCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.library.AppModel;

import java.util.ArrayList;


public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.MyViewHolder> {
    private ArrayList<AppModel> data = new ArrayList<>();

    public AppListAdapter(Context context) {

    }

    public void setData(ArrayList<AppModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_icon_text, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final AppModel item = data.get(position);
        holder.icon.setImageDrawable(item.getIcon());
        holder.text.setText("Name: " + item.getLabel());
        holder.packageName.setText("Package: " + item.getApplicationPackageName());
        if (item.getAppInfo().className != null) {
            String[] bits = item.getAppInfo().className.split("\\.");
            String className = bits[bits.length - 1];
            holder.mainClassName.setText("Launch Class: " + className);
        } else {
            holder.mainClassName.setText("Launch Class: N/A");
        }
        try {
            PackageInfo pInfo = holder.itemView.getContext().getPackageManager().getPackageInfo(item.getApplicationPackageName(), 0);
            long longVersionCode = PackageInfoCompat.getLongVersionCode(pInfo);
            holder.versionCode.setText("version Code: " + longVersionCode);
            holder.versionName.setText("version Name: " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = view.getContext().getPackageManager();
                Intent launchIntent = pm.getLaunchIntentForPackage(item.getApplicationPackageName());
                view.getContext().startActivity(launchIntent);
                if (launchIntent != null) {
                    view.getContext().startActivity(launchIntent);
                } else {
                    Toast.makeText(view.getContext(), "Package not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView icon;
        public TextView text, packageName, mainClassName, versionCode, versionName;


        public MyViewHolder(View v) {
            super(v);
            icon = v.findViewById(R.id.icon);
            text = v.findViewById(R.id.text);
            packageName = v.findViewById(R.id.packageName);
            mainClassName = v.findViewById(R.id.mainClassName);
            versionCode = v.findViewById(R.id.versionCode);
            versionName = v.findViewById(R.id.versionName);
        }
    }
}
