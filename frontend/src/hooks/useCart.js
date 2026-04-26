import { useCallback } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addItemToCart, fetchCartItems } from '../features/cartSlice';

export const useCart = (cartId) => {
    const dispatch = useDispatch();
    const { items, loading, error } = useSelector((state) => state.cart);

    const addToCart = useCallback((productId, quantity) => {
        dispatch(addItemToCart({ cartId, data: { productId, quantity } }));
    }, [dispatch, cartId]);

    const loadItems = useCallback(() => {
        dispatch(fetchCartItems(cartId));
    }, [dispatch, cartId]);

    return { items, loading, error, addToCart, loadItems };
};
