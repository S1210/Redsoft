package ru.alex.redsoft.contractors;

import ru.alex.redsoft.adapters.RecyclerViewMainAdapter;
import ru.alex.redsoft.models.Datum;

public interface MainContractor {
    interface View {

        void setAdapter(RecyclerViewMainAdapter adapter);

        void openProduct(Datum datum, int position);

        void hideProgressBar();

        void showError();

        void showProgressBar();
    }

    interface Presenter {

        void loadProducts();

        void loadProductsWithFilter(String filter);

        void updateProduct(int position, int count);
    }
}
