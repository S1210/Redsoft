package ru.alex.redsoft.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.alex.redsoft.R;
import ru.alex.redsoft.models.Datum;

public class RecyclerViewMainAdapter extends RecyclerView.Adapter<RecyclerViewMainAdapter.RecyclerViewHolder> {

    private List<Datum> datumList;
    private OnProductClickListener productClickListener;

    public RecyclerViewMainAdapter(List<Datum> datumList, OnProductClickListener productClickListener) {
        this.datumList = datumList;
        this.productClickListener = productClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_main, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(datumList.get(position));
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivProduct;
        private TextView tvCategory;
        private TextView tvNameProduct;
        private TextView tvManufacturer;
        private TextView tvPrice;
        private TextView tvCountProduct;
        private LinearLayout llBtnCount;
        private Button btnAddCart;
        private Button btnCountIncrease;
        private Button btnCountDecrease;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.iv_product);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvNameProduct = itemView.findViewById(R.id.tv_name_product);
            tvManufacturer = itemView.findViewById(R.id.tv_manufacturer);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvCountProduct = itemView.findViewById(R.id.tv_count_product);
            llBtnCount = itemView.findViewById(R.id.ll_btn_count);
            btnAddCart = itemView.findViewById(R.id.btn_add_cart);
            btnCountIncrease = itemView.findViewById(R.id.btn_count_increase);
            btnCountDecrease = itemView.findViewById(R.id.btn_count_decrease);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Datum datum = datumList.get(getLayoutPosition());
                    productClickListener.onProductClick(datum);
                }
            });
            btnAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datumList.get(getLayoutPosition()).setCount(1);
                    tvCountProduct.setText("1");
                    btnAddCart.setVisibility(View.GONE);
                    llBtnCount.setVisibility(View.VISIBLE);
                }
            });
            btnCountIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = datumList.get(getLayoutPosition()).getCount() + 1;
                    datumList.get(getLayoutPosition()).setCount(count);
                    tvCountProduct.setText(String.valueOf(count));
                }
            });
            btnCountDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = datumList.get(getLayoutPosition()).getCount() - 1;
                    datumList.get(getLayoutPosition()).setCount(count);
                    if (count == 0) {
                        llBtnCount.setVisibility(View.GONE);
                        btnAddCart.setVisibility(View.VISIBLE);
                    } else {
                        tvCountProduct.setText(String.valueOf(count));
                    }
                }
            });
        }

        public void bind(Datum datum) {
            Picasso.get().load(datum.getImageUrl()).into(ivProduct);
            tvCategory.setText(datum.getCategories().size() > 0 ? datum.getCategories().get(0).getTitle() : null);
            tvNameProduct.setText(datum.getTitle());
            tvManufacturer.setText(datum.getProducer());
            tvPrice.setText(String.valueOf(datum.getPrice()));
            tvCountProduct.setText(String.valueOf(datum.getCount()));
        }
    }
    public interface OnProductClickListener {
        void onProductClick(Datum datum);
    }
}
