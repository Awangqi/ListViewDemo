package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.pangu.ha.R;
import com.pangu.ha.Remindt;

import java.util.List;

import bean.Bean;

/**
 * Created by Administrator on 2016/7/11.
 */
public class MyAdapter extends BaseAdapter {
    Context mContext;
    List<Bean> mList;

    //用于存储CheckBox选中状态
    public MyAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Bean> data) {
        mList = data;
        notifyDataSetChanged();
    }

    @Override

    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.main_test, null);
            holder.mChecBox = (CheckBox) view.findViewById(R.id.item_check);
            holder.mName = (TextView) view.findViewById(R.id.text);
            holder.mAmbo = (TextView) view.findViewById(R.id.text1);
            holder.mRemind = (TextView) view.findViewById(R.id.Remindtv);
            holder.mTextView = (TextView) view.findViewById(R.id.drag_handle);
            holder.mZD = (TextView) view.findViewById(R.id.zd);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        //CheckBox错乱解决
        holder.mChecBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mList.get(position).setChecked(true);
                } else {
                    mList.get(position).setChecked(false);
                }
            }
        });

        holder.mZD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTop(position);//自顶排序
            }
        });

        holder.mRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.Remindtv) {
                    Intent integer = new Intent(mContext, Remindt.class);
                    mContext.startActivity(integer);

                }
            }
        });



        /*CheckBox监听事件必须放在setChecked之前，否则后果自负*/
        holder.mChecBox.setChecked(mList.get(position).getChecked());
        holder.mName.setText(mList.get(position).getName());
        holder.mAmbo.setText(mList.get(position).getAmbo());
        return view;
    }

    private void setTop(int position) {
        mList.add(0, mList.get(position));
        mList.remove(position + 1);
        notifyDataSetChanged();
    }


    class ViewHolder {
        CheckBox mChecBox;
        TextView mName;
        TextView mAmbo;
        TextView mTextView;
        TextView mZD;
        TextView mRemind;
    }

    public void remove(int position) {//删除指定位置的item

        mList.remove(position);
        notifyDataSetChanged();
    }


    public void insert(Bean item, int i) {//在指定位置插入item
        mList.add(i, item);
        notifyDataSetChanged();
    }


}
