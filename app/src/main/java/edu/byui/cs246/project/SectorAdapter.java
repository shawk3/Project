package edu.byui.cs246.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import edu.byui.cs246.project.R;

/**
 * Created by Jason on 12/9/2015.
 */
public class SectorAdapter extends BaseExpandableListAdapter {

    private Context ctx;
    private HashMap<String, List<String>> Sectors;
    private List<String> Sector_List;
    String defaultSector;
    int defaultGroup;
    String defaultSubSect;

    public SectorAdapter(Context ctx, HashMap<String, List<String>> Sectors, List<String> Sector_List){
        this.ctx = ctx;
        this.Sectors = Sectors;
        this.Sector_List = Sector_List;
        defaultSector = ",";
        defaultSubSect = ",";
    }

    private void setDefaultGroup() {
        for(int i = 0; i < getGroupCount(); i++){
            String sector = Sector_List.get(i);
            if (sector.equals(defaultSector))
                defaultGroup = i;
        }
    }

    @Override
    public int getGroupCount() {
        return Sector_List.size();
    }

    @Override
    public int getChildrenCount(int arg0) {

        return Sectors.get(Sector_List.get(arg0)).size();
    }

    @Override
    public Object getGroup(int arg0) {
        return Sector_List.get(arg0);
    }

    @Override
    public Object getChild(int parent, int child) {

        return Sectors.get(Sector_List.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int arg0) {
        return arg0;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentview) {
        String group_title = (String) getGroup(parent);
        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.parent_layout, parentview, false);
        }
        TextView parent_textview = (TextView) convertView.findViewById(R.id.parent_txt);
        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);

        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertView, ViewGroup parentview) {
        String child_title = (String) getChild(parent, child);
        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.child_layout,parentview, false);
        }
        TextView child_textview = (TextView)convertView.findViewById(R.id.child_txt);
        child_textview.setText(child_title);

        if(getGroup(parent).toString().equals(defaultSector) && getChild(parent, child).toString().equals(defaultSubSect)){
            child_textview.setBackgroundResource(R.color.blueHighlight);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

    public void setDefault(String sID, String subID){
        defaultSector = sID;
        defaultSubSect = subID;

        setDefaultGroup();
    }

    public int getDefaultGroup(){return defaultGroup; };
}
