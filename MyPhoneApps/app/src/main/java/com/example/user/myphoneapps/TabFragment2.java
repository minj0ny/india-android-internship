package com.example.user.myphoneapps;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TabFragment2 extends android.support.v4.app.Fragment {

    private List<CustomDTO> listCustom = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_View2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        CustomAdapter myAdapter = new CustomAdapter(getInstalledApps(), v.getContext());
        mRecyclerView.setAdapter(myAdapter);

        return v;

    }

    private ArrayList<CustomDTO> getInstalledApps() {
        ArrayList<CustomDTO> res = new ArrayList<CustomDTO>();
        List<PackageInfo> packs = getActivity().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((isSystemPackage(p) == true)) {
                CustomDTO newInfo = new CustomDTO();
                newInfo.setResId(p.applicationInfo.loadIcon(getActivity().getPackageManager()));
                newInfo.setName(p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString());
                newInfo.setPkg(p.packageName);
                try {
                    newInfo.setSize(String.valueOf(getApkSize(getContext(), p.packageName)));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                res.add(newInfo);
            }
        }
        return res;
    }

    public static long getApkSize(Context context, String packageName)
            throws PackageManager.NameNotFoundException {
        return new File(context.getPackageManager().getApplicationInfo(
                packageName, 0).publicSourceDir).length();
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }

}
