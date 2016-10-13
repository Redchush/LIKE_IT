package by.epam.like_it.model.adapter;


import by.epam.like_it.model.bean.util_interface.DeletableByBan;

public interface DeletableByBanContent extends Content {

    @Override
    DeletableByBan getRealEntity();
}
