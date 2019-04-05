package com.example.starwarsboot.utils;

import com.example.starwarsboot.wires.PaginationWire;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaginationUtil {

    public static PaginationWire getPaginationResult(List resultFromDb, Pageable pageable, String pageURl) {
        PaginationWire paginationWire = new PaginationWire();
        paginationWire.setNext(pageURl + pageable.next().getPageNumber());
        paginationWire.setResult(resultFromDb);
        if (resultFromDb.size() < pageable.next().getPageSize()) {
            paginationWire.setNext(null);
            return paginationWire;
        }
        return paginationWire;
    }
}
