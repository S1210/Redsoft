package ru.alex.redsoft.contractors;

import ru.alex.redsoft.models.Datum;

public interface ProductContractor {

    interface View {

        void hideProgressBar();

        void showError();

        void setValue(Datum datum);
    }

    interface Presenter {

        void loadContent(int id);
    }
}
