import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { cartService } from '../services/cartService';

export const addItemToCart = createAsyncThunk(
    'cart/addItem',
    async ({ cartId, data }) => {
        const response = await cartService.addItem(cartId, data);
        return response.data;
    }
);

export const fetchCartItems = createAsyncThunk('cart/fetchItems', async (cartId) => {
    const response = await cartService.getItems(cartId);
    return response.data;
});

const cartSlice = createSlice({
    name: 'cart',
    initialState: { items: [], loading: false, error: null },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(addItemToCart.pending, (state) => { state.loading = true; state.error = null; })
            .addCase(addItemToCart.fulfilled, (state, action) => {
                state.loading = false;
                state.items.push(action.payload);
            })
            .addCase(addItemToCart.rejected, (state, action) => {
                state.loading = false;
                state.error = action.error.message;
            })
            .addCase(fetchCartItems.fulfilled, (state, action) => {
                state.items = action.payload;
            });
    },
});

export default cartSlice.reducer;
