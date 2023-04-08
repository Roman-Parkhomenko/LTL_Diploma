package info.Parkhomenko.personaldiary.view.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.Random;

import info.Parkhomenko.personaldiary.R;
import info.Parkhomenko.personaldiary.data.model.Diary;
import info.Parkhomenko.personaldiary.data.model.Section;
import info.Parkhomenko.personaldiary.view.callbacks.ItemClickListener;
import info.Parkhomenko.personaldiary.view.callbacks.SectionStateChangeListener;

public class SectionedExpandableGridAdapter
            extends RecyclerView.Adapter<SectionedExpandableGridAdapter.ViewHolder> {

    //data array
    private final ArrayList<Object> mDataArrayList;

    //context
    private final Context mContext;
    private final int[] mMaterialColors;

    //listeners
    private final ItemClickListener mItemClickListener;
    private final SectionStateChangeListener mSectionStateChangeListener;

    //view type
    private static final int VIEW_TYPE_SECTION = R.layout.layout_section;
    private static final int VIEW_TYPE_ITEM = R.layout.row_model;

    public SectionedExpandableGridAdapter(Context context, ArrayList<Object> dataArrayList,
                                          final GridLayoutManager gridLayoutManager,
                                           ItemClickListener itemClickListener,
                                          SectionStateChangeListener sectionStateChangeListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
        mSectionStateChangeListener = sectionStateChangeListener;
        mDataArrayList = dataArrayList;

        TypedValue mTypedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mMaterialColors = context.getResources().getIntArray(R.array.colors);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return isSection(position)?gridLayoutManager.getSpanCount():1;
            }
        });
    }

    private boolean isSection(int position) {
        return mDataArrayList.get(position) instanceof Section;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false),
         viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.viewType) {
            case VIEW_TYPE_ITEM :
                final Diary item = (Diary) mDataArrayList.get(position);
                holder.mTitleTxt.setText(item.getTitle());
                holder.mDescriptionTxt.setText(item.getDescription());
                holder.materialLetterIcon.setInitials(false);
                holder.materialLetterIcon.setLettersNumber(item.getCategory().length());
                holder.materialLetterIcon.setLetterSize(10);
                holder.materialLetterIcon.setShapeType(MaterialLetterIcon.Shape.RECT);
                holder.materialLetterIcon.setShapeColor(mMaterialColors[1]);
                holder.materialLetterIcon.setLetter(item.getCategory());

                holder.view.setBackgroundColor(mMaterialColors[0]);

                holder.view.setOnClickListener(v -> mItemClickListener.itemClicked(item));
                break;
            case VIEW_TYPE_SECTION :
                final Section section = (Section) mDataArrayList.get(position);
                holder.sectionTextView.setText(section.getName());
                holder.sectionTextView.setOnClickListener(v -> mItemClickListener.itemClicked(
                    section));
                holder.sectionToggleButton.setChecked(section.isExpanded);
                holder.sectionToggleButton.setOnCheckedChangeListener((buttonView,
                 isChecked) -> mSectionStateChangeListener.onSectionStateChanged(section,
                  isChecked));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isSection(position))
            return VIEW_TYPE_SECTION;
        else return VIEW_TYPE_ITEM;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        //common
        final View view;
        final int viewType;

        //for section
        TextView sectionTextView;
        ToggleButton sectionToggleButton;

        //for item
        TextView mTitleTxt,mDescriptionTxt;
        MaterialLetterIcon materialLetterIcon;

        public ViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;
            this.view = view;
            if (viewType == VIEW_TYPE_ITEM) {
                mTitleTxt = view.findViewById(R.id.mTitleTxt);
                mDescriptionTxt = view.findViewById(R.id.mDescriptionTxt);
                materialLetterIcon = view.findViewById(R.id.mMaterialLetterIcon);
            } else {
                sectionTextView = view.findViewById(R.id.text_section);
                sectionToggleButton = view.findViewById(R.id.toggle_button_section);
            }
        }
    }
    //end viewholder
}
//end