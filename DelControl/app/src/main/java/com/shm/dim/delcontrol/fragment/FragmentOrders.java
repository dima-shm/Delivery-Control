package com.shm.dim.delcontrol.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.adapter.OrdersAdapter;
import com.shm.dim.delcontrol.asyncTask.RestRequestDelegate;
import com.shm.dim.delcontrol.asyncTask.RestRequestTask;
import com.shm.dim.delcontrol.model.Order;
import com.shm.dim.delcontrol.model.OrderList;
import com.shm.dim.delcontrol.model.OrderProducts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class FragmentOrders
        extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefresh;

    private RecyclerView mOrdersList;

    private ProgressBar mProgressBar;

    private TextView mOrderListIsEmpty;

    private Spinner mOrderStatus;

    private Button mSendButton;

    private AlertDialog mProductsDialog;

    private int mSelectedOrderId;

    private SharedPreferences mSharedPreferences;

    private static final String AССOUNT_PREFERENCES = "ACCOUNT_INFO",
            AССOUNT_ID = "AССOUNT_ID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        mProgressBar = view.findViewById(R.id.progress);
        mOrderListIsEmpty = view.findViewById(R.id.order_list_is_empty);
        initSwipeRefresh(view);
        mOrdersList = view.findViewById(R.id.orders_list);
        initOrderList();
        initOrderStatusSpinner(view);
        initSendButton(view);
    }

    private void initSwipeRefresh(View view) {
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(this);
    }

    private void initOrderStatusSpinner(View view) {
        mOrderStatus = view.findViewById(R.id.order_status);
    }

    private void initSendButton(View view) {
        mSendButton = view.findViewById(R.id.send);
        mSendButton.setOnClickListener(onClickSend());
    }

    private void initOrderList() {
        mSharedPreferences = this.getActivity()
                .getSharedPreferences(AССOUNT_PREFERENCES, Context.MODE_PRIVATE);
        String courierId = mSharedPreferences.getString(AССOUNT_ID, "");
        sendRestRequest("http://delcontrol.somee.com/api/CourierOrders/" + courierId,
                "GET",
                "");
    }

    private void initOrdersAdapter() {
        if(OrderList.getOrderCount() != 0) {
            mOrderListIsEmpty.setVisibility(View.GONE);
            OrdersAdapter adapter = new OrdersAdapter(getContext(),
                    OrderList.getOrders(),
                    new OrdersAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Order order, int position) {
                            showOrderProduct(order);
                            mOrderStatus.setEnabled(true);
                            mSendButton.setEnabled(true);
                            setOrderStatusInSpinner(order);
                            mSelectedOrderId = order.getId();
                        }
                    });
            mOrdersList.setAdapter(adapter);
            mSwipeRefresh.setRefreshing(false);
        } else {
            mOrderListIsEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void showOrderProduct(Order order) {
        ArrayList<OrderProducts> products = order.getOrderProducts();
        String message = "";
        float totalCoast = 0F;
        for (OrderProducts product : products ) {
            message += product.getProductName() + " (" + product.getPrice() + ")\n";
            totalCoast += product.getPrice();
        }
        message += "\n" + getContext().getResources().getString(R.string.total_cost) + totalCoast;
        if(!order.getOrderProducts().isEmpty()) {
            showProductsDialog(getContext(), message);
        }
    }

    private void showProductsDialog(final Context context, String message) {
        if(mProductsDialog != null)
            mProductsDialog.dismiss();
        createProductsDialog(context, message);
        mProductsDialog.show();
    }

    private void createProductsDialog(final Context context, String message) {
        mProductsDialog
                = getProductsDialog(context,
                context.getResources().getString(R.string.products_in_order),
                message);
    }

    private AlertDialog getProductsDialog(final Context context,
                                       final String title, final String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { }
                });
        return builder.create();
    }

    private void setOrderStatusInSpinner(Order order) {
        String[] orderStatus = getResources().getStringArray(R.array.order_status);
        for (int i = 0; i < orderStatus.length; i++) {
            if (order.getStatus().equals(orderStatus[i])) {
                mOrderStatus.setSelection(i);
            }
        }
    }

    public View.OnClickListener onClickSend() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                String[] orderStatus = getResources().getStringArray(R.array.order_status);
                sendRestRequest("http://delcontrol.somee.com/api/CourierOrders/" +
                                mSelectedOrderId + "/" +
                                orderStatus[mOrderStatus.getSelectedItemPosition()],
                        "PUT",
                        "");
            }
        };
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
        mProgressBar.setVisibility(View.GONE);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            getOrders(responseBody);
            Toast.makeText(getContext(), getResources().getString(R.string.order_list_updated),
                    Toast.LENGTH_LONG).show();
        } else if(responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
            initOrderList();
        } else {
            Toast.makeText(getContext(),
                    getResources().getString(R.string.check_network_state) +
                            " (code: " + String.valueOf(responseCode) + ")",
                    Toast.LENGTH_LONG).show();
        }
        mSwipeRefresh.setRefreshing(false);
        mOrderStatus.setEnabled(false);
        mSendButton.setEnabled(false);
    }

    private void getOrders(String responseBody) {
        try {
            ArrayList<Order> orders = new ArrayList<>();
            JSONArray ordersArray = new JSONArray(responseBody);
            for (int i = 0; i < ordersArray.length(); i++) {
                JSONObject order = ordersArray.getJSONObject(i);
                int id = order.getInt("Id");
                int companyId = order.getInt("CompanyId");
                String customerName = order.getString("CustomerName");
                String deliveryAddress = order.getString("DeliveryAddress");
                String deliveryDate = order.getString("DeliveryDate");
                String deliveryTime = order.getString("DeliveryTime");
                String comment = order.getString("Comment");
                String status = order.getString("Status");
                JSONArray orderProducts = order.getJSONArray("OrderProducts");
                ArrayList<OrderProducts> products = new ArrayList<>();
                for (int j = 0; j < orderProducts.length(); j++) {
                    JSONObject product = orderProducts.getJSONObject(j);
                    int productId = product.getInt("Id");
                    int orderId = product.getInt("OrderId");
                    String productName = product.getString("ProductName");
                    double price = product.getDouble("Price");
                    String description = product.getString("Description");
                    products.add(new OrderProducts(productId, orderId, productName, description, price));
                }
                orders.add(new Order(id, companyId, customerName, deliveryAddress,
                        deliveryDate, deliveryTime, comment, status, products));
            }
            OrderList.setOrders(orders);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initOrdersAdapter();
    }

    @Override
    public void onRefresh() {
        initOrderList();
    }
}