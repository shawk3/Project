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
 * adapter for ExpandableListView
 *
 * this class allows users to interact with the ExpandableLIstView found
 * in the Demographics Activity
 *
 * @author Jason
 * @since 2015-12
 */
public class SectorAdapter extends BaseExpandableListAdapter {

    /** context variable */
    private Context ctx;
    /** HashMap for sectors and sub-sectors*/
    private HashMap<String, List<String>> Sectors;
    /** list of sectors */
    private List<String> Sector_List;
    /** string for sector with no sub-sectors */
    String defaultSector;
    /** no sub-sector group */
    int defaultGroup;
    /** string for no sub-sector */
    String defaultSubSect;

    /**
     * Non-default constructor for adapter
     *
     * @param ctx
     * @param Sectors
     * @param Sector_List
     */
    public SectorAdapter(Context ctx, HashMap<String, List<String>> Sectors, List<String> Sector_List){
        this.ctx = ctx;
        this.Sectors = Sectors;
        this.Sector_List = Sector_List;
        defaultSector = ",";
        defaultSubSect = ",";
    }

    /**
     * Set the default group so that it can be auto expanded.
     */
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

    /**
     * return sector view
     *
     * @param parent
     * @param isExpanded
     * @param convertView
     * @param parentview
     * @return
     */
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

    /**
     * return sub-sector view
     *
     * @param parent
     * @param child
     * @param lastChild
     * @param convertView
     * @param parentview
     * @return
     */
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

    /**
     * child is selectable
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

    /**
     * setter
     *
     * sets default group
     *
     * @param sID
     * @param subID
     */
    public void setDefault(String sID, String subID){
        defaultSector = sID;
        defaultSubSect = subID;

        setDefaultGroup();
    }

    /**
     * getter
     *
     * return default group
     *
     * @return
     */
    public int getDefaultGroup(){return defaultGroup; }
}
