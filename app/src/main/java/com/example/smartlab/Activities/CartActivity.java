package com.example.smartlab.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartlab.App;
import com.example.smartlab.CartAdapter;
import com.example.smartlab.CartElement;
import com.example.smartlab.CartManager;
import com.example.smartlab.R;
import com.example.smartlab.Responses.Tests;
import com.example.smartlab.TestsAdapter;

import java.util.List;
import java.util.PrimitiveIterator;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartView;
    private List cartList;

    private App app;
    private CartAdapter cartAdapter;
    private CartManager cartManager;

    private TextView total;
    private ImageView clear;
    private AppCompatButton back;
    private AppCompatButton toOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        app = (App) getApplication();
        cartManager = app.getCartManager();
        cartList = cartManager.getList();

        total = findViewById(R.id.total);
        back = findViewById(R.id.back);
        clear = findViewById(R.id.clear);
        toOrder = findViewById(R.id.order_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        cartAdapter = new CartAdapter(cartManager);

        cartView = findViewById(R.id.cart_view);
        cartView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        cartView.setAdapter(cartAdapter);

        cartAdapter.setOnPriceChangeListener(new CartAdapter.OnPriceChangeListener() {
            @Override
            public void onPriceChange(int price) {
                total.setText(price + " ₽");
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartList.clear();
                cartAdapter.notifyDataSetChanged();
                cartAdapter.calcTotal();
            }
        });

        toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOrder();
            }
        });

    }

    private void back() {
        this.finish();
    }

    private void toOrder() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
}