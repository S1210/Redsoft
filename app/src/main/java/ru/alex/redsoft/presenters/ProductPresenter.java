package ru.alex.redsoft.presenters;

import ru.alex.redsoft.contractors.ProductContractor;

public class ProductPresenter implements ProductContractor.Presenter {

    private ProductContractor.View pView;

    public ProductPresenter(ProductContractor.View pView) {
        this.pView = pView;
    }
}
