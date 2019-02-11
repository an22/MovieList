package com.movielist.view.view_interfaces;

import com.movielist.model.entity.Configuration;

public interface CatalogView {


    void onError(String message);
    void runHome(Configuration configuration);
    void runCatalog(Configuration configuration);
    void runSearch(Configuration configuration);
    void runProfile(Configuration configuration);
    void saveLanguage();
}
