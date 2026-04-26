import { useEffect, useMemo, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchProducts } from '../features/productSlice';

export const useProducts = (searchTerm = '', minPrice = 0) => {
    const dispatch = useDispatch();
    const { items, loading, error } = useSelector((state) => state.products);

    useEffect(() => {
        dispatch(fetchProducts());
    }, [dispatch]);

    const filtered = useMemo(() => {
        return items.filter((p) =>
            p.name.toLowerCase().includes(searchTerm.toLowerCase()) &&
            p.price >= minPrice
        );
    }, [items, searchTerm, minPrice]);

    return { products: filtered, loading, error };
};
