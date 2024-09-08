package com.usermanagement.common;

import java.util.List;

public interface BaseService<T,ID> {

    ResponseWrapper<T> save(RequestWrapper<T> requestWrapper);

    ResponseWrapper<T> findById(RequestWrapper<ID> requestWrapper);

    ResponseWrapper<List<T>> findAll();

    ResponseWrapper<T> update(RequestWrapper<T> requestWrapper);

    ResponseWrapper<T> deleteById(RequestWrapper<ID> requestWrapper);

}
