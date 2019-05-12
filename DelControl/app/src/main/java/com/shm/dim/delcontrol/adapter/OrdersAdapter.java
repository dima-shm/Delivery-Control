package com.shm.dim.delcontrol.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.model.Order;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Order> orders;
    private final OnItemClickListener listener;
    boolean[] selectItems;


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
        //holder.mOrderCode.setText(order.getCompanyId());
        holder.mName.setText(order.getCustomerName());
        holder.mAddress.setText(order.getDeliveryAddress());
        holder.mDate.setText(order.getDeliveryDate());
        holder.mTime.setText(order.getDeliveryTime());

        if(selectItems[position]) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#14ffffff"));
        }

        holder.bind(orders.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Order order, int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView mOrderCode, mProductName, mQuantity, mCost,
                mName, mAddress,
                mDate, mTime;

        ViewHolder(View view) {
            super(view);
            mOrderCode = view.findViewById(R.id.order_code);
            mProductName = view.findViewById(R.id.product_name);
            mQuantity = view.findViewById(R.id.quantity);
            mCost = view.findViewById(R.id.cost);
            mName = view.findViewById(R.id.customer_name);
            mAddress = view.findViewById(R.id.customer_address);
            mDate = view.findViewById(R.id.date);
            mTime = view.findViewById(R.id.time);
        }

        public void bind(final Order order, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positin = getAdapterPosition();
                    // Выставляем false для всеч элементов массива
                    for (int i = 0; i < selectItems.length; i++) {
                        selectItems[i] = false;
                    }
                    // Выбранный элемент — true
                    selectItems[positin] = true;
                    // Передаем order в onItemClick адаптера
                    OrdersAdapter.this.listener.onItemClick(order, positin);
                    // Вызов onBindViewHolder
                    notifyDataSetChanged();
                }
            });
        }
    }
}