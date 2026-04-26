import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { productService } from '../services/productService';

export const fetchProducts = createAsyncThunk('products/fetchAll', async () => {
    const response = await productService.getAll();
    return response.data;
});

export const createProduct = createAsyncThunk('products/create', async (data) => {
    const response = await productService.create(data);
    return response.data;
});

export const fetchPaginatedProducts = createAsyncThunk(
    'products/fetchPaginated',
    async ({ page, size, sortBy, direction }) => {
        const response = await productService.getPaginated(page, size, sortBy, direction);
        return response.data;
    }
);

const productSlice = createSlice({
    name: 'products',
    initialState: {
        items: [],
        paginated: null,
        loading: false,
        error: null,
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchProducts.pending, (state) => { state.loading = true; state.error = null; })
            .addCase(fetchProducts.fulfilled, (state, action) => {
                state.loading = false;
                state.items = action.payload;
            })
            .addCase(fetchProducts.rejected, (state, action) => {
                state.loading = false;
                state.error = action.error.message;
            })
            .addCase(createProduct.fulfilled, (state, action) => {
                state.items.push(action.payload);
            })
            .addCase(fetchPaginatedProducts.fulfilled, (state, action) => {
                state.paginated = action.payload;
                state.loading = false;
            });
    },
});

export default productSlice.reducer;
