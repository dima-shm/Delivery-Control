package com.shm.dim.delcontrol.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.adapter.OrdersAdapter;
import com.shm.dim.delcontrol.asyncTask.RestRequestDelegate;
import com.shm.dim.delcontrol.asyncTask.RestRequestTask;
import com.shm.dim.delcontrol.model.Order;
import com.shm.dim.delcontrol.model.OrderProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class FragmentOrders extends Fragment {

    private RecyclerView mOrdersList;

    private SharedPreferences mSharedPreferences;

    private static final String AССOUNT_PREFERENCES = "ACCOUNT_INFO",
            AССOUNT_ID = "AССOUNT_ID";

    private ArrayList<Order> mOrders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        mOrdersList = view.findViewById(R.id.orders_list);
        initOrderList();
    }

    private void initOrderList() {
        mSharedPreferences = this.getActivity()
                .getSharedPreferences(AССOUNT_PREFERENCES, Context.MODE_PRIVATE);
        String courierId = mSharedPreferences.getString(AССOUNT_ID, "");
        sendRestRequest("http://192.168.43.234:46002/api/CourierOrders/" + courierId,
                "GET",
                "");
    }

    private void initOrdersAdapter() {
        if(mOrders.size() != 0) {
            OrdersAdapter adapter = new OrdersAdapter(getContext(),
                    mOrders,
                    new OrdersAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Order order, int position) {
                            Toast.makeText(getContext(),
                                    "position: " + position + " order.getCompanyId():" + order.getCompanyId(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            mOrdersList.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(),
                    "Список Ваших заказов пуст\n Нажмите кнопку обновить для проверки наличия Ваших заказов",
                    Toast.LENGTH_LONG).show();
        }
    }

    protected void sendRestRequest(String url, String method, String body) {
        RestRequestTask request =
                new RestRequestTask(new RestRequestDelegate() {
                    @Override
                    public void executionFinished(int responseCode, String responseBody) {
                        onRestRequestFinished(responseCode, responseBody);
                    }
                });
        request.execute(url, method, body);
    }

    private void onRestRequestFinished(int responseCode, String responseBody) {
        if (responseCode == HttpURLConnection.HTTP_OK) {
            getOrders(responseBody);
            Toast.makeText(getContext(), getResources().getString(R.string.complete),
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(),
                    getResources().getString(R.string.check_network_state) +
                            " (code: " + String.valueOf(responseCode) + ")",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void getOrders(String responseBody) {
        mOrders = new ArrayList<>();
        try {
            JSONArray ordersArray = new JSONArray(responseBody);
            for (int i = 0; i < ordersArray.length(); i++) {
                JSONObject order = ordersArray.getJSONObject(i);
                int companyId = order.getInt("CompanyId");
                String customerName = order.getString("CustomerName");
                String deliveryAddress = order.getString("DeliveryAddress");
                String deliveryDate = order.getString("DeliveryDate");
                String deliveryTime = order.getString("DeliveryTime");
                String comment = order.getString("Comment");
                String status = order.getString("Status");
                JSONArray orderProducts = order.getJSONArray("OrderProducts");
                ArrayList<OrderProduct> products = new ArrayList<>();
                for (int j = 0; j < orderProducts.length(); j++) {
                    JSONObject product = orderProducts.getJSONObject(j);
                    int id = product.getInt("Id");
                    int orderId = product.getInt("OrderId");
                    String productName = product.getString("ProductName");
                    String price = product.getString("Price");
                    String descriotion = product.getString("Descriotion");
                    products.add(new OrderProduct(id, orderId, productName, descriotion, price));
                }
                mOrders.add(new Order(companyId, customerName, deliveryAddress,
                        deliveryDate, deliveryTime, comment, status, products));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initOrdersAdapter();
    }

}