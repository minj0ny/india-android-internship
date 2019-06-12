package com.example.user.myphoneapps;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<CustomDTO> customDTOS;
    private Context mCtx;
    String pkgName = "";


    public CustomAdapter(List<CustomDTO> customDTOS, Context mCtx) {
        this.customDTOS = customDTOS;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_custom, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * listView getView 를 대체
     * 넘겨 받은 데이터를 화면에 출력하는 역할
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        CustomDTO item = customDTOS.get(position);
        viewHolder.resId.setImageDrawable(item.getResId());
        viewHolder.name.setText(item.getName());
        viewHolder.pkg.setText(item.getPkg());
        viewHolder.size.setText(item.getSize());
        viewHolder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mCtx, viewHolder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.m1:
                                PackageManager pm = mCtx.getPackageManager();
                                Intent appStartIntent = pm.getLaunchIntentForPackage(customDTOS.get(position).getPkg());
                                if (null != appStartIntent) {
                                    mCtx.startActivity(appStartIntent);
                                }
                                return true;
                            case R.id.m2:
                                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                StrictMode.setVmPolicy(builder.build());
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + customDTOS.get(position).getPkg())), "application/vnd.android.package-archive");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mCtx.startActivity(intent);
                                return true;
                            case R.id.m3:
                                Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT,
                                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + customDTOS.get(position).getPkg());
                                sendIntent.setType("text/plain");
                                mCtx.startActivity(sendIntent);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return customDTOS.size();
    }

    /**
     * 뷰 재활용을 위한 viewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView resId;
        public TextView name;
        public TextView pkg;
        public TextView size;
        public ImageButton buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            resId = (ImageView) itemView.findViewById(R.id.imageView);
            name = (TextView) itemView.findViewById(R.id.name);
            pkg = (TextView) itemView.findViewById(R.id.pkg);
            size = (TextView) itemView.findViewById(R.id.size);
            buttonViewOption = (ImageButton) itemView.findViewById(R.id.buttonViewOption);

        }
    }
}