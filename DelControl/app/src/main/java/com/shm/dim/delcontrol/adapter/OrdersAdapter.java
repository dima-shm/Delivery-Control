package com.shm.dim.delcontrol.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.model.Order;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private Context context;

    private LayoutInflater inflater;

    private ArrayList<Order> orders;

    private final OnItemClickListener listener;

    private boolean[] selectItems;

    public OrdersAdapter(Context context, ArrayList<Order> orders, OnItemClickListener listener) {
        this.context = context;
        this.orders = orders;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
        selectItems = new boolean[getItemCount()];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Order order = orders.get(position);
        holder.mOrderId.setText(Integer.toString(order.getId()));
        holder.mCustomerName.setText(order.getCustomerName());
        holder.mDeliveryAddress.setText(order.getDeliveryAddress());
        holder.mDeliveryDate.setText(order.getDeliveryDate());
        holder.mDeliveryTime.setText(order.getDeliveryTime());
        holder.mComment.setText(order.getComment());
        setColorItems(holder, position);
        holder.bind(orders.get(position), listener);
    }

    private void setColorItems(final ViewHolder holder, final int position) {
        if(selectItems[position]) {
            setSelectedItemColor(holder, R.color.colorPrimaryLight);
        } else {
            setSelectedItemColor(holder, R.color.colorWhite);
        }
    }

    private void setSelectedItemColor(final ViewHolder holder, int colorId) {
        holder.mOrderItem.setBackgroundColor(context.getResources().getColor(colorId));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Order order, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mOrderItem;

        private final TextView mOrderId, mCustomerName, mDeliveryAddress,
                mDeliveryDate, mDeliveryTime, mComment;

        private ViewHolder(View view) {
            super(view);
            mOrderItem = view.findViewById(R.id.order_item);
            mOrderId = view.findViewById(R.id.order_id);
            mCustomerName = view.findViewById(R.id.customer_name);
            mDeliveryAddress = view.findViewById(R.id.delivery_address);
            mDeliveryDate = view.findViewById(R.id.delivery_date);
            mDeliveryTime = view.findViewById(R.id.delivery_time);
            mComment = view.findViewById(R.id.comment);
        }

        private void bind(final Order order, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    for (int i = 0; i < selectItems.length; i++) {
                        selectItems[i] = false;
                    }
                    selectItems[position] = true;
                    OrdersAdapter.this.listener.onItemClick(order, position);
                    notifyDataSetChanged();
                }
            });
        }
    }
}