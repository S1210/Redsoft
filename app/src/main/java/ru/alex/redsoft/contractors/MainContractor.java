package ru.alex.redsoft.contractors;

import ru.alex.redsoft.adapters.RecyclerViewMainAdapter;

public interface MainContractor {
    interface View {

        void setAdapter(RecyclerViewMainAdapter adapter);
    }
    interface Presenter {

        void loadProducts();

    }
    interface Repository {

    }
}
